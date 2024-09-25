package com.adminsures.sures.services;

import com.adminsures.sures.dto.ClienteDTO;
import com.adminsures.sures.dto.ProveedorDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Proveedor;
import com.adminsures.sures.exception.EntidadYaExisteException;
import com.adminsures.sures.mapper.ProveedorMapper;
import com.adminsures.sures.repository.ProveedorRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapper proveedorMapper;

    //Metodo para obtener los proveedores;
    public List<ProveedorDTO> getAllProveedores(){
        List<Proveedor> proveedores = proveedorRepository.findByActivoTrue();
        return proveedores.stream()
                .map(proveedorMapper::toDTO)
                .collect(Collectors.toList());

    }

    //Metodo para obtener un proveedor por id
    public ProveedorDTO getProveedorById(Long id){
        Optional<Proveedor> provedorOpt = proveedorRepository.findById(id);
        return provedorOpt.map(proveedorMapper::toDTO).orElse(null);
    }

    //Metodo para crear un nuevo proveedor
    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO){

        //Validar si el rfc ya existe en la base de datos
        if(proveedorRepository.existsByRfc(proveedorDTO.getRfc())){
            throw new EntidadYaExisteException("El proveedor con RFC " + proveedorDTO.getRfc() + " ya existe");
        }

        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        Proveedor newProveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDTO(newProveedor);
    }

    //Metodo para actualizar un cliente
    public ProveedorDTO updateProveedor(Long id, ProveedorDTO proveedorDTO){
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(id);
        if (proveedorOpt.isPresent()){

            Proveedor proveedor = proveedorOpt.get();

            // Verificar si el RFC ya existe y si pertenece a otro cliente
            if (proveedorRepository.existsByRfcAndIdNot(proveedorDTO.getRfc(), id)) {
                throw new EntidadYaExisteException("El proveedor con RFC " + proveedorDTO.getRfc() + " ya existe.");
            }

            proveedor.setTipoPersona(proveedorDTO.getTipoPersona());
            proveedor.setNombre(proveedorDTO.getNombre());
            proveedor.setRfc(proveedorDTO.getRfc());
            proveedor.setRegimenFiscal(proveedorDTO.getRegimenFiscal());
            proveedor.setCodigoPostal(proveedorDTO.getCodigoPostal());
            proveedor.setEmail(proveedorDTO.getEmail());
            proveedor.setEmailAlterno(proveedorDTO.getEmailAlterno());
            proveedor.setTelefono(proveedorDTO.getTelefono());
            proveedor.setTelefonoAlterno(proveedorDTO.getTelefonoAlterno());
            proveedor.setDireccion(proveedorDTO.getDireccion());
            proveedor.setColonia(proveedorDTO.getColonia());
            proveedor.setCiudad(proveedorDTO.getCiudad());
            proveedor.setEstado(proveedorDTO.getEstado());

            Proveedor updateProveedor = proveedorRepository.save(proveedor);
            return proveedorMapper.toDTO(updateProveedor);
        }
        return null;
    }

    //Metodo para eliminar un proveedor
    public void deleteProveedor(Long id){
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(id);
        if(proveedorOpt.isPresent()){
            Proveedor proveedor = new Proveedor();
            proveedor = proveedorOpt.get();
            proveedor.setActivo(false);
            proveedorRepository.save(proveedor);
        } else {
            throw new ValidationException("El proveedor con ID " + id + " no fue encontrado");
        }
    }

    //Metodo para activar un cliente eliminado/desactivado
    @Transactional
    public Proveedor activarProveedor(Long id) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findByIdAndActivoFalse(id);

        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setActivo(true);  // Activar cliente
            return proveedorRepository.save(proveedor);  // Guardar cliente activado
        } else {
            throw new RuntimeException("Cliente no encontrado o ya está activo");
        }
    }

    //Metodo para buscar por nombre o rfc de un cliente
    public List<ProveedorDTO> buscarPorNombreOrRfc(String searchTerm){
        List<Proveedor> proveedores = proveedorRepository.findByNombreOrRfc(searchTerm);
        return proveedores.stream()
                .map(proveedorMapper::toDTO)
                .collect(Collectors.toList());
    }
}
