package com.adminsures.sures.entitys;

import jakarta.persistence.*;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cotizacion_id")
    private List<ProductoCotizado> productos = new ArrayList<>();
    private double subtotal;
    private double descuento;
    private double total;

    public Cotizacion() {
        this.fechaCreacion = LocalDate.now();
    }

    public void actualizarCotizacion() {
        this.fechaActualizacion = LocalDate.now();
    }

    // Métodos para agregar productos, calcular subtotal, IVA, importe y total
    public void agregarProducto(Producto producto, int cantidad) {
        ProductoCotizado productoCotizado = new ProductoCotizado(producto, cantidad);
        productos.add(productoCotizado);
        recalcularTotales();
    }

    public void recalcularTotales() {
        subtotal = productos.stream()
                .mapToDouble(ProductoCotizado::calcularImporte)
                .sum();
        double descuentoAplicado = subtotal * (descuento / 100);
        total = subtotal - descuentoAplicado;
    }
}
