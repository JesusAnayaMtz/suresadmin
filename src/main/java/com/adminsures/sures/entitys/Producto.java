package com.adminsures.sures.entitys;

import com.adminsures.sures.enums.UnidadVenta;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String codigoBarras;

    private String descripcion;

    private String rutaImagen;

    private Double precio;
    private Double costo;
    private Double utilidad;

    @Enumerated(EnumType.STRING)
    private UnidadVenta unidadVenta;

    private Integer existencia;

    private Integer existenciaMinima;

    @Column(nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate fechaCreacion;

    @UpdateTimestamp
    private LocalDate fechaActualizacion;
}

