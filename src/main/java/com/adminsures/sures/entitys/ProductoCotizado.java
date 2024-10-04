package com.adminsures.sures.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos_cotizados")
public class ProductoCotizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private int cantidad;

    public ProductoCotizado() {
    }

    public ProductoCotizado(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularImporte(){
        double precioConIva = producto.getPrecio() * 1.16;
        return  precioConIva * cantidad;
    }
}
