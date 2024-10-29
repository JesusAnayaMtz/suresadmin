package com.adminsures.sures.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CotizacionDTO {

    private Long id;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private ClienteDTO cliente;
    private List<ProductoCotizacionDTO> productos;
    private Double descuentoAdicional;
    private Double subtotal;
    private Double subtotalDescuento;
    private Double iva;
    private Double total;
}
