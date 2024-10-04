package com.adminsures.sures.entitys;

import com.adminsures.sures.enums.Estado;
import com.adminsures.sures.enums.RegimenFiscal;
import com.adminsures.sures.enums.TipoPersona;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

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

    private Boolean manejoCredito = false;

    @DecimalMin("0.00")
    private BigDecimal limiteCredito;

    private Integer diasCredito;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cotizacion> cotizaciones;

    private Boolean activo = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate fechaCreacion;

    @UpdateTimestamp
    private LocalDate fechaActualizacion;
}
