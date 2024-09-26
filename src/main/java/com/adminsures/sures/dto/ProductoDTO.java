package com.adminsures.sures.dto;

import com.adminsures.sures.enums.UnidadVenta;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String codigoBarras;
    private String descripcion;
    private String rutaImagen;
    private Double precio;
    private Double costo;  // Campo para costo
    private Double utilidad;  // Campo para utilidad calculada automáticamente
    private UnidadVenta unidadVenta;  // Campo para la unidad de venta
    private Integer existencia;  // Nuevo campo de existencia
    private Integer existenciaMinima;  // Nuevo campo de existencia mínima
    private Boolean activo;
    private LocalDate fechaActualizacion;
}
