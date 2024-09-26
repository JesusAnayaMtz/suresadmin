package com.adminsures.sures.mapper;

import com.adminsures.sures.dto.ProductoDTO;
import com.adminsures.sures.entitys.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCosto(producto.getCosto());
        dto.setUtilidad(producto.getUtilidad());
        dto.setUnidadVenta(producto.getUnidadVenta());
        dto.setExistencia(producto.getExistencia());  // Mapeo del campo existencia
        dto.setRutaImagen(producto.getRutaImagen());
        dto.setExistenciaMinima(producto.getExistenciaMinima());  // Mapeo del campo existencia mínima
        dto.setActivo(producto.getActivo());
        return dto;
    }

    public Producto toEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setCodigoBarras(dto.getCodigoBarras());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCosto(dto.getCosto());
        producto.setUnidadVenta(dto.getUnidadVenta());
        producto.setRutaImagen(dto.getRutaImagen());
        producto.setExistencia(dto.getExistencia());  // Mapeo del campo existencia
        producto.setExistenciaMinima(dto.getExistenciaMinima());  // Mapeo del campo existencia mínima
        producto.setActivo(dto.getActivo());
        return producto;
    }

}
