package com.adminsures.sures.entitys;

import com.adminsures.sures.enums.Estado;
import com.adminsures.sures.enums.RegimenFiscal;
import com.adminsures.sures.enums.TipoPersona;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPersona tipoPersona;

    private String nombre;
    private String rfc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegimenFiscal regimenFiscal;

    private String email;
    private String emailAlterno;
    private String telefono;
    private String telefonoAlterno;
    private String codigoPostal;

    private String direccion;
    private String colonia;
    private String ciudad;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Boolean activo = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate fechaCreacion;

    @UpdateTimestamp
    private LocalDate fechaActualizacion;

}
