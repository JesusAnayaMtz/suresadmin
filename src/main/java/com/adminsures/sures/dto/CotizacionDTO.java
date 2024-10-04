package com.adminsures.sures.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CotizacionDTO {
    private Long id;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private Long clienteId;
    private List<ProductoCotizadoDTO> productos;
    private double subtotal;
    private double descuento;
    private double total;
}
