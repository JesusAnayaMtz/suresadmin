package com.adminsures.sures.services;

import com.adminsures.sures.dto.CotizacionDTO;
import com.adminsures.sures.dto.ProductoCotizacionDTO;
import com.adminsures.sures.dto.ProductoDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Cotizacion;
import com.adminsures.sures.entitys.CotizacionProducto;
import com.adminsures.sures.entitys.Producto;
import com.adminsures.sures.mapper.CotizacionMapper;
import com.adminsures.sures.repository.ClienteRepository;
import com.adminsures.sures.repository.CotizacionRepository;
import com.adminsures.sures.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CotizacionMapper cotizacionMapper;

    @Transactional(readOnly = true)
    public List<CotizacionDTO> obtenerTodas() {
        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
        return cotizaciones.stream()
                .map(cotizacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CotizacionDTO obtenerPorId(Long id) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
        return cotizacionMapper.toDto(cotizacion);
    }

    @Transactional
    public CotizacionDTO crearCotizacion(CotizacionDTO cotizacionDTO) {
        Cotizacion cotizacion = new Cotizacion();

        // Mapeo del cliente
        Cliente cliente = clienteRepository.findById(cotizacionDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cotizacion.setCliente(cliente);

        // Mapeo de los productos
        List<CotizacionProducto> productos = cotizacionMapper.mapProductosToEntity(cotizacionDTO.getProductos(), cotizacion);
        for (CotizacionProducto cotizacionProducto : productos) {
            Producto producto = productoRepository.findById(cotizacionProducto.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            cotizacionProducto.setProducto(producto);
        }
        cotizacion.setProductos(productos);

        // Cálculo del subtotal y total
        cotizacion.setDescuentoAdicional(cotizacionDTO.getDescuentoAdicional());
        cotizacion.setSubtotal(cotizacionMapper.calcularSubtotal(cotizacion));
        cotizacion.setTotal(cotizacionMapper.calcularTotal(cotizacion));

        // Guardar la cotización
        cotizacion = cotizacionRepository.save(cotizacion);

        return cotizacionMapper.toDto(cotizacion);
    }

    @Transactional
    public CotizacionDTO actualizarCotizacion(Long id, CotizacionDTO cotizacionDTO) {
        Cotizacion cotizacionExistente = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        // Actualizar cliente
        Cliente cliente = clienteRepository.findById(cotizacionDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cotizacionExistente.setCliente(cliente);

        // Obtener IDs de productos desde el DTO
        List<Long> productoIdsDesdeDTO = cotizacionDTO.getProductos().stream()
                .map(ProductoCotizacionDTO::getProductoId)
                .collect(Collectors.toList());

        // Eliminar productos que ya no están en el DTO
        cotizacionExistente.getProductos().removeIf(cotizacionProducto ->
                !productoIdsDesdeDTO.contains(cotizacionProducto.getProducto().getId()));

        // Agregar o actualizar productos desde el DTO
        for (ProductoCotizacionDTO productoDTO : cotizacionDTO.getProductos()) {
            CotizacionProducto cotizacionProductoExistente = cotizacionExistente.getProductos().stream()
                    .filter(cp -> cp.getProducto().getId().equals(productoDTO.getProductoId()))
                    .findFirst()
                    .orElse(null);

            if (cotizacionProductoExistente != null) {
                // Actualizar cantidad en lugar de sumarla
                cotizacionProductoExistente.setCantidad(productoDTO.getCantidad());
                // Actualizar el descuento si es necesario
                cotizacionProductoExistente.setDescuento(productoDTO.getDescuento());
            } else {
                // Crear un nuevo producto si no existe
                CotizacionProducto nuevoCotizacionProducto = new CotizacionProducto();
                Producto producto = productoRepository.findById(productoDTO.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                nuevoCotizacionProducto.setProducto(producto);
                nuevoCotizacionProducto.setCantidad(productoDTO.getCantidad());
                nuevoCotizacionProducto.setDescuento(productoDTO.getDescuento());
                nuevoCotizacionProducto.setCotizacion(cotizacionExistente);
                cotizacionExistente.getProductos().add(nuevoCotizacionProducto);
            }
        }

        // Actualizar descuentos, subtotal y total
        cotizacionExistente.setDescuentoAdicional(cotizacionDTO.getDescuentoAdicional());
        cotizacionExistente.setSubtotal(cotizacionMapper.calcularSubtotal(cotizacionExistente));
        cotizacionExistente.setTotal(cotizacionMapper.calcularTotal(cotizacionExistente));
        cotizacionExistente.setFechaActualizacion(LocalDate.now());

        // Guardar cambios
        cotizacionRepository.save(cotizacionExistente);

        return cotizacionMapper.toDto(cotizacionExistente);
    }


    @Transactional
    public void eliminarCotizacion(Long id) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
        cotizacionRepository.delete(cotizacion);
    }
}
