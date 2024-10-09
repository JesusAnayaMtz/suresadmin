package com.adminsures.sures.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroEmpleado;

    private String nombre;
    private String apellido;
    private String apellidoMaterno;
    private String rfc;
    private String curp;
    private String direccion;
    private String colonia;
    private String ciudad;
    private String email;
    private String telefono;
    private String celular;
    private LocalDate fechaIniLaboral;
    private Long nss;
    private String departamento;
    private String puesto;
    private String banco;
    private Long cuentaBancaria;
    private Double salarioBase;
    private Double salarioIntegrado;
    private String rutaImagen;
    private Boolean activo = true;
}
