package com.adminsures.sures.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoDTO {

    private Long id;

    private Integer numeroEmpleado;

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El apellido paterno es obligatorio")
    private String apellido;

    private String apellidoMaterno;

    @NotNull(message = "El rfc es obligatorio")
    @Size(min = 12, max = 13, message = "El RFC debe tener entre 12 y 13 caracteres.")
    private String rfc;

    @NotNull(message = "El curp es obligatorio")
    @Size(min = 18, max = 18, message = "El Curp debe tener 18 caracteres")
    private String curp;

    @NotNull(message = "La direccion es obligatori")
    private String direccion;

    @NotNull(message = "La colonia es obligatoria")
    private String colonia;

    @NotNull(message = "La ciudad es obligatoria")
    private String ciudad;

    @Email
    @NotNull(message = "El email es obligatorio")
    private String email;

    @Size(min = 10, max = 14, message = "El telefono debe tener entre 10 y maximo 14 numeros")
    private String telefono;

    @NotNull(message = "El numero de celular es obligatorio")
    @Size(min = 10, max = 14, message = "El telefono debe tener entre 10 y maximo 14 numeros")
    private String celular;

    @Pattern(regexp = "dd/MM/yyyy")
    private LocalDate fechaIniLaboral;

    @NotNull(message = "El numero de seguro social es obligatorio")
    @Size(min = 11, max = 11, message = "El numero de seguro social debe tener 11 caracteres numericos")
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
