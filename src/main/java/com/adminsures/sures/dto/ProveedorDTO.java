package com.adminsures.sures.dto;

import com.adminsures.sures.enums.Estado;
import com.adminsures.sures.enums.RegimenFiscal;
import com.adminsures.sures.enums.TipoPersona;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProveedorDTO {

    private Long id;

    @NotNull(message = "El tipo de persona es obligatorio.")
    private TipoPersona tipoPersona;

    @NotNull(message = "El nombre es obligatorio.")
    @Size(min = 1, max = 255, message = "El nombre debe tener entre 1 y 255 caracteres.")
    private String nombre;

    @NotNull(message = "El RFC es obligatorio.")
    @Size(min = 12, max = 13, message = "El RFC debe tener entre 12 y 13 caracteres.")
    private String rfc;

    @NotNull(message = "El régimen fiscal es obligatorio.")
    private RegimenFiscal regimenFiscal;

    @NotNull(message = "El código postal es obligatorio.")
    @Size(min = 5, max = 5, message = "El código postal debe tener 5 caracteres.")
    private String codigoPostal;

    @NotNull(message = "El email es obligatorio.")
    @Email(message = "Debe proporcionar un email válido.")
    private String email;

    @Email(message = "Debe proporcionar un email alterno válido.")
    private String emailAlterno;

    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos.")
    private String telefono;

    @Pattern(regexp = "\\d{10}")
    private String telefonoAlterno;

    @NotNull(message = "La dirección es obligatoria.")
    private String direccion;

    @NotNull(message = "La colonia es obligatoria.")
    private String colonia;

    @NotNull(message = "La ciudad es obligatoria.")
    private String ciudad;

    @NotNull(message = "El estado es obligatorio.")
    private Estado estado;

    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;

    private Boolean activo = true;
}
