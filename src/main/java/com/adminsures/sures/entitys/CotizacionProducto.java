package com.adminsures.sures.entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cotizacion_productos")
@Data
@NoArgsConstructor
public class CotizacionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cotizacion_id", nullable = false)
    private Cotizacion cotizacion;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "descuento", nullable = false)
    private Double descuento;

    @Transient
    private Double iva;

    @Transient
    private Double importe;

    public Double calcularImporte() {
        Double precio = producto.getPrecio();
        Double descuentoAplicado = precio * (descuento / 100);
        Double precioConDescuento = precio - descuentoAplicado;
        iva = precioConDescuento * 0.16; // IVA del 16%
        importe = (precioConDescuento + iva) * cantidad;
        return importe;
    }
}