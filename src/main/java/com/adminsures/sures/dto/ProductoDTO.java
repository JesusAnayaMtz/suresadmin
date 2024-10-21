package com.adminsures.sures.dto;

import com.adminsures.sures.enums.Categoria;
import com.adminsures.sures.enums.TipoIva;
import com.adminsures.sures.enums.UnidadVenta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductoDTO {
    private Long id;

    @NotNull(message = "La clave interna es obligatoria.")
    private String claveInterna;


    private String codigoBarras;

    @NotNull(message = "La descripccion es obligatoria.")
    private String descripcion;

    @NotNull
    @Min(7)
    private Integer claveSat;


    private String rutaImagen;

    @NotNull(message = "El tipo de iva es obligatorio.")
    private TipoIva tipoIva;

    @NotNull
    @Min(1)
    private Double precio;

    private UnidadVenta unidadVenta;  // Campo para la unidad de venta


    private Categoria categoria;

    private Integer existencia;  // Nuevo campo de existencia

    private Integer existenciaMinima;  // Nuevo campo de existencia mínima
    private Boolean activo = true;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
}
