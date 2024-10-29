package com.adminsures.sures.entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cotizaciones")
@Data
@NoArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion = LocalDate.now();

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CotizacionProducto> productos;

    @Column(name = "descuento_adicional")
    private Double descuentoAdicional;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "subtotal_descuento")
    private Double subtotalDescuento;

    @Column(name = "iva")
    private Double iva;

    @Column(name = "total")
    private Double total;

    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = LocalDate.now();
    }



}