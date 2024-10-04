package com.adminsures.sures.services;

import com.adminsures.sures.dto.CotizacionDTO;
import com.adminsures.sures.dto.ProductoCotizadoDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Cotizacion;
import com.adminsures.sures.entitys.Producto;
import com.adminsures.sures.mapper.CotizacionMapper;
import com.adminsures.sures.repository.ClienteRepository;
import com.adminsures.sures.repository.CotizacionRepository;
import com.adminsures.sures.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CotizacionDTO> obtenerTodasLasCotizaciones( ) {
        return cotizacionRepository.findAll().stream().map(CotizacionMapper::toDTO).toList();
    }

    public CotizacionDTO obtenerCotizacion(Long id) throws Exception {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new Exception("Cotizacion no encontrada: " + id));
        return cotizacionMapper.toDTO(cotizacion);
    }

    public CotizacionDTO crearCotizacion(CotizacionDTO cotizacionDTO) throws Exception {
        Cliente cliente = clienteRepository.findById(cotizacionDTO.getClienteId()).orElseThrow(() -> new Exception("Cliente no encontrado"));

        List<Producto> productos = productoRepository.findAllById(cotizacionDTO.getProductos().stream().map(ProductoCotizadoDTO::getProductoId).toList());

        Cotizacion cotizacion = CotizacionMapper.toEntity(cotizacionDTO, cliente, productos);
        Cotizacion nuevaContizacion = cotizacionRepository.save(cotizacion);
        return CotizacionMapper.toDTO(nuevaContizacion);
    }

    public CotizacionDTO actualizarCotizacion(Long id, CotizacionDTO cotizacionDTO) throws Exception {
        Cotizacion cotizacionExistente = cotizacionRepository.findById(id).orElseThrow(() -> new Exception("Cotizacion no encontrada"));

        Cliente cliente = clienteRepository.findById(cotizacionDTO.getClienteId())
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        List<Producto> productos = productoRepository.findAllById(cotizacionDTO.getProductos().stream()
                .map(ProductoCotizadoDTO::getProductoId).toList());

        cotizacionExistente.setCliente(cliente);
        cotizacionExistente.getProductos().clear();
        cotizacionDTO.getProductos().forEach(productoDTO -> {
            Producto producto = productos.stream()
                    .filter(p -> p.getId().equals(productoDTO.getProductoId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Prodicto no econtrado"));

            cotizacionExistente.agregarProducto(producto, productoDTO.getCantidad());
        });

        cotizacionExistente.setDescuento(cotizacionDTO.getDescuento());
        cotizacionExistente.actualizarCotizacion();

        Cotizacion cotizacionActualizada = cotizacionRepository.save(cotizacionExistente);
        return CotizacionMapper.toDTO(cotizacionActualizada);
    }

    public void eliminarCotizacion(Long id){
        cotizacionRepository.deleteById(id);
    }
}
