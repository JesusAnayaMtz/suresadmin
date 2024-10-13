package com.adminsures.sures.mapper;

import com.adminsures.sures.dto.EmpleadoDTO;
import com.adminsures.sures.entitys.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public EmpleadoDTO toDTO(Empleado empleado){
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNumeroEmpleado(empleado.getNumeroEmpleado());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setApellidoMaterno(empleado.getApellidoMaterno());
        dto.setRfc(empleado.getRfc());
        dto.setCurp(empleado.getCurp());
        dto.setDireccion(empleado.getDireccion());
        dto.setColonia(empleado.getColonia());
        dto.setCiudad(empleado.getCiudad());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getTelefono());
        dto.setCelular(empleado.getCelular());
        dto.setFechaIniLaboral(empleado.getFechaIniLaboral());
        dto.setNss(empleado.getNss());
        dto.setDepartamento(empleado.getDepartamento());
        dto.setPuesto(empleado.getPuesto());
        dto.setBanco(empleado.getBanco());
        dto.setCuentaBancaria(empleado.getCuentaBancaria());
        dto.setSalarioBase(empleado.getSalarioBase());
        dto.setSalarioIntegrado(empleado.getSalarioIntegrado());
        dto.setRutaImagen(empleado.getRutaImagen());
        dto.setActivo(empleado.getActivo());
        return dto;
    }

    public Empleado toEntity(EmpleadoDTO empleadoDTO){
        Empleado empleado = new Empleado();
        empleado.setId(empleadoDTO.getId());
        empleado.setNumeroEmpleado(empleadoDTO.getNumeroEmpleado());
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setApellidoMaterno(empleadoDTO.getApellidoMaterno());
        empleado.setRfc(empleadoDTO.getRfc());
        empleado.setCurp(empleadoDTO.getCurp());
        empleado.setDireccion(empleadoDTO.getDireccion());
        empleado.setColonia(empleadoDTO.getColonia());
        empleado.setColonia(empleadoDTO.getColonia());
        empleado.setCiudad(empleadoDTO.getCiudad());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setCelular(empleadoDTO.getCelular());
        empleado.setFechaIniLaboral(empleadoDTO.getFechaIniLaboral());
        empleado.setNss(empleadoDTO.getNss());
        empleado.setDepartamento(empleadoDTO.getDepartamento());
        empleado.setPuesto(empleadoDTO.getPuesto());
        empleado.setBanco(empleadoDTO.getBanco());
        empleado.setCuentaBancaria(empleadoDTO.getCuentaBancaria());
        empleado.setSalarioBase(empleadoDTO.getSalarioBase());
        empleado.setSalarioIntegrado(empleadoDTO.getSalarioIntegrado());
        empleado.setRutaImagen(empleadoDTO.getRutaImagen());
        empleado.setActivo(empleadoDTO.getActivo());
        return empleado;
    }
}
