package com.adminsures.sures.dto;

import lombok.Data;

@Data
public class ProductoCotizacionDTO {

    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private Double precio;
    private Double descuento;
    private Double iva;
    private Double importe;
}
