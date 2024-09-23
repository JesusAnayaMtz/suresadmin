package com.adminsures.sures.mapper;

import com.adminsures.sures.dto.ClienteDTO;
import com.adminsures.sures.entitys.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setTipoPersona(cliente.getTipoPersona());
        dto.setNombre(cliente.getNombre());
        dto.setRfc(cliente.getRfc());
        dto.setRegimenFiscal(cliente.getRegimenFiscal());
        dto.setCodigoPostal(cliente.getCodigoPostal());
        dto.setEmail(cliente.getEmail());
        dto.setEmailAlterno(cliente.getEmailAlterno());
        dto.setTelefono(cliente.getTelefono());
        dto.setTelefonoAlterno(cliente.getTelefonoAlterno());
        dto.setDireccion(cliente.getDireccion());
        dto.setColonia(cliente.getColonia());
        dto.setCiudad(cliente.getCiudad());
        dto.setEstado(cliente.getEstado());
        dto.setManejoCredito(cliente.getManejoCredito());
        dto.setLimiteCredito(cliente.getLimiteCredito());
        dto.setDiasCredito(cliente.getDiasCredito());
        dto.setFechaCreacion(cliente.getFechaCreacion());
        dto.setFechaActualizacion(cliente.getFechaActualizacion());

        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setTipoPersona(dto.getTipoPersona());
        cliente.setNombre(dto.getNombre());
        cliente.setRfc(dto.getRfc());
        cliente.setRegimenFiscal(dto.getRegimenFiscal());
        cliente.setCodigoPostal(dto.getCodigoPostal());
        cliente.setEmail(dto.getEmail());
        cliente.setEmailAlterno(dto.getEmailAlterno());
        cliente.setTelefono(dto.getTelefono());
        cliente.setTelefonoAlterno(dto.getTelefonoAlterno());
        cliente.setDireccion(dto.getDireccion());
        cliente.setColonia(dto.getColonia());
        cliente.setCiudad(dto.getCiudad());
        cliente.setEstado(dto.getEstado());
        cliente.setManejoCredito(dto.getManejoCredito());
        cliente.setLimiteCredito(dto.getLimiteCredito());
        cliente.setDiasCredito(dto.getDiasCredito());

        // No se necesita establecer las fechas, Hibernate se encarga

        return cliente;
    }
}
