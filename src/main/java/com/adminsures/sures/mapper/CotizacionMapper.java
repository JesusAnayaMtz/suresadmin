package com.adminsures.sures.mapper;

import com.adminsures.sures.dto.CotizacionDTO;
import com.adminsures.sures.dto.ProductoCotizadoDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Cotizacion;
import com.adminsures.sures.entitys.Producto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CotizacionMapper {


    public static CotizacionDTO toDTO(Cotizacion cotizacion){
        CotizacionDTO dto = new CotizacionDTO();
        dto.setId(cotizacion.getId());
        dto.setFechaCreacion(cotizacion.getFechaCreacion());
        dto.setFechaActualizacion(cotizacion.getFechaActualizacion());
        dto.setClienteId(cotizacion.getCliente().getId());
        dto.setProductos(cotizacion.getProductos().stream()
                .map(productoCotizado -> {
                    ProductoCotizadoDTO productoCotizadoDTO = new ProductoCotizadoDTO();
                    productoCotizadoDTO.setProductoId(productoCotizado.getProducto().getId());
                    productoCotizadoDTO.setCantidad(productoCotizado.getCantidad());
                    return productoCotizadoDTO;
                }).collect(Collectors.toList()));
        dto.setSubtotal(cotizacion.getSubtotal());
        dto.setDescuento(cotizacion.getDescuento());
        dto.setTotal(cotizacion.getTotal());
        return dto;
    }

    public static Cotizacion toEntity(CotizacionDTO dto, Cliente cliente, List<Producto> productos){
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCliente(cliente);
        dto.getProductos().forEach(productoDTO -> {
            Producto producto = productos.stream()
                    .filter(p -> p.getId().equals(productoDTO.getProductoId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            cotizacion.agregarProducto(producto, productoDTO.getCantidad());
        });
        cotizacion.setDescuento(dto.getDescuento());
        cotizacion.recalcularTotales();
        return cotizacion;
    }
}
