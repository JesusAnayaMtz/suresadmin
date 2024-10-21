package com.adminsures.sures.entitys;

import com.adminsures.sures.enums.Categoria;
import com.adminsures.sures.enums.TipoIva;
import com.adminsures.sures.enums.UnidadVenta;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.PrivilegedAction;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String claveInterna;

    @Column(unique = true, nullable = false)
    private String codigoBarras;

    private String descripcion;

    private Integer claveSat;

    private String rutaImagen;

    @Enumerated(EnumType.STRING)
    private TipoIva tipoIva;

    private Double precio;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

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

//    public Double calculaIva(){
//        tipoIva = this.tipoIva;
//
//        if (tipoIva == TipoIva.GRAVADO){
//            precioIva = (precio * 0.16) + precio;
//        } else if (tipoIva == TipoIva.EXENTO) {
//            precioIva = precio;
//        }
//        return precioIva;
//    }
}

