package com.adminsures.sures.mapper;

import com.adminsures.sures.dto.CotizacionDTO;
import com.adminsures.sures.dto.ProductoCotizacionDTO;
import com.adminsures.sures.entitys.Cotizacion;
import com.adminsures.sures.entitys.CotizacionProducto;
import com.adminsures.sures.entitys.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ProductoMapper.class})
public interface CotizacionMapper {


    CotizacionMapper INSTANCE = Mappers.getMapper(CotizacionMapper.class);

    @Mapping(target = "subtotal", expression = "java(calcularSubtotal(cotizacion))")
    @Mapping(target = "subtotalDescuento", expression = "java(calcularSubtotalDescuento(cotizacion))")
    @Mapping(target = "productos", source = "productos")
    CotizacionDTO toDto(Cotizacion cotizacion);

    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "productos", source = "productos")
    Cotizacion toEntity(CotizacionDTO cotizacionDTO);

    default List<ProductoCotizacionDTO> mapProductosToDTO(List<CotizacionProducto> productos) {
        return productos.stream()
                .map(producto -> {
                    ProductoCotizacionDTO dto = new ProductoCotizacionDTO();
                    dto.setProductoId(producto.getProducto().getId());
                    dto.setNombre(producto.getProducto().getDescripcion());
                    dto.setUnidadVenta(producto.getProducto().getUnidadVenta());
                    dto.setCantidad(producto.getCantidad());
                    dto.setPrecio(producto.getProducto().getPrecio());
                    dto.setDescuento(producto.getDescuento());
                    dto.setIva(producto.calcularImporte() * 0.16);
                    dto.setImporte(producto.calcularImporte());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    default List<CotizacionProducto> mapProductosToEntity(List<ProductoCotizacionDTO> productosDTO, Cotizacion cotizacion) {
        return productosDTO.stream()
                .map(dto -> {
                    CotizacionProducto producto = new CotizacionProducto();
                    Producto productoEntity = new Producto();
                    productoEntity.setId(dto.getProductoId());
                    producto.setProducto(productoEntity);
                    producto.setCotizacion(cotizacion);
                    producto.setCantidad(dto.getCantidad());
                    producto.setDescuento(dto.getDescuento());
                    return producto;
                })
                .collect(Collectors.toList());
    }

    default Double calcularSubtotal(Cotizacion cotizacion) {
        return cotizacion.getProductos().stream()
                .mapToDouble(CotizacionProducto::calcularImporte)
                .sum();
    }

    default Double calcularSubtotalDescuento(Cotizacion cotizacion) {
        Double subtotal = calcularSubtotal(cotizacion);
        return subtotal - (subtotal * (cotizacion.getDescuentoAdicional() / 100));
    }

    default Double calcularIva(Cotizacion cotizacion) {
        Double iva = calcularSubtotalDescuento(cotizacion);
        return iva * 0.16;
    }

    default Double calcularTotal(Cotizacion cotizacion){
        Double total = calcularSubtotalDescuento(cotizacion);
        return total + calcularIva(cotizacion);
    }
}