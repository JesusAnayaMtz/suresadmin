package com.adminsures.sures.mapper;

import com.adminsures.sures.dto.ClienteDTO;
import com.adminsures.sures.dto.ProveedorDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorDTO toDTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setTipoPersona(proveedor.getTipoPersona());
        dto.setNombre(proveedor.getNombre());
        dto.setRfc(proveedor.getRfc());
        dto.setRegimenFiscal(proveedor.getRegimenFiscal());
        dto.setCodigoPostal(proveedor.getCodigoPostal());
        dto.setEmail(proveedor.getEmail());
        dto.setEmailAlterno(proveedor.getEmailAlterno());
        dto.setTelefono(proveedor.getTelefono());
        dto.setTelefonoAlterno(proveedor.getTelefonoAlterno());
        dto.setDireccion(proveedor.getDireccion());
        dto.setColonia(proveedor.getColonia());
        dto.setCiudad(proveedor.getCiudad());
        dto.setEstado(proveedor.getEstado());
        dto.setFechaCreacion(proveedor.getFechaCreacion());
        dto.setFechaActualizacion(proveedor.getFechaActualizacion());

        return dto;
    }

    public Proveedor toEntity(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(dto.getId());
        proveedor.setTipoPersona(dto.getTipoPersona());
        proveedor.setNombre(dto.getNombre());
        proveedor.setRfc(dto.getRfc());
        proveedor.setRegimenFiscal(dto.getRegimenFiscal());
        proveedor.setCodigoPostal(dto.getCodigoPostal());
        proveedor.setEmail(dto.getEmail());
        proveedor.setEmailAlterno(dto.getEmailAlterno());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setTelefonoAlterno(dto.getTelefonoAlterno());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setColonia(dto.getColonia());
        proveedor.setCiudad(dto.getCiudad());
        proveedor.setEstado(dto.getEstado());

        // No se necesita establecer las fechas, Hibernate se encarga

        return proveedor;
    }
}
