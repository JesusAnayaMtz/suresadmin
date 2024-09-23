package com.adminsures.sures.services;

import com.adminsures.sures.dto.ClienteDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.exception.EntidadYaExisteException;
import com.adminsures.sures.mapper.ClienteMapper;
import com.adminsures.sures.repository.ClienteRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    //Metodo para obtener los clientes;
    public List<ClienteDTO> getAllClientes(){
        List<Cliente> clientes = clienteRepository.findByActivoTrue();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());

    }

    //Metodo para obtener un cliente por id
    public ClienteDTO getClienteById(Long id){
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        return clienteOpt.map(clienteMapper::toDTO).orElse(null);
    }

    //Metodo para crear un nuevo cliente
    public ClienteDTO createCliente(ClienteDTO clienteDTO){

        validarClienteDTO(clienteDTO);

        //Validar si el rfc ya existe en la base de datos
        if(clienteRepository.existsByRfc(clienteDTO.getRfc())){
            throw new EntidadYaExisteException("El cliente con RFC " + clienteDTO.getRfc() + " ya existe");
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente newCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(newCliente);
    }

    //Metodo para actualizar un cliente
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO){
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()){
            validarClienteDTO(clienteDTO);

            Cliente cliente = clienteOpt.get();

            //Verificar si el rfc ya existe y si pertenece a otro cliente
            // Verificar si el RFC ya existe y si pertenece a otro cliente
            if (clienteRepository.existsByRfcAndIdNot(clienteDTO.getRfc(), id)) {
                throw new EntidadYaExisteException("El cliente con RFC " + clienteDTO.getRfc() + " ya existe.");
            }

            cliente.setTipoPersona(clienteDTO.getTipoPersona());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setRfc(clienteDTO.getRfc());
            cliente.setRegimenFiscal(clienteDTO.getRegimenFiscal());
            cliente.setCodigoPostal(clienteDTO.getCodigoPostal());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setEmailAlterno(clienteDTO.getEmailAlterno());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setTelefonoAlterno(clienteDTO.getTelefonoAlterno());
            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setColonia(clienteDTO.getColonia());
            cliente.setCiudad(clienteDTO.getCiudad());
            cliente.setEstado(clienteDTO.getEstado());
            cliente.setManejoCredito(clienteDTO.getManejoCredito());

            if(!clienteDTO.getManejoCredito()){
                cliente.setLimiteCredito(BigDecimal.ZERO);
                cliente.setDiasCredito(0);
            } else {
                cliente.setLimiteCredito(clienteDTO.getLimiteCredito());
                cliente.setDiasCredito(clienteDTO.getDiasCredito());
            }

            Cliente updateCliente = clienteRepository.save(cliente);
            return clienteMapper.toDTO(updateCliente);
        }
        return null;
    }

    //Metodo para eliminar un cliente
    public void deleteCliente(Long id){
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if(clienteOpt.isPresent()){
            Cliente cliente = clienteOpt.get();
            cliente.setActivo(false);
            clienteRepository.save(cliente);
        } else {
            throw new ValidationException("El cliente con ID " + id + " no fue encontrado");
        }

        clienteRepository.deleteById(id);
    }

    //Metodo para buscar por nombre o rfc a un cliente
    public List<ClienteDTO> buscarPorNombreOrRfc(String searchTerm){
        List<Cliente> clientes = clienteRepository.findByNombreOrRfc(searchTerm);
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Validar la lógica condicional de manejo de crédito
    private void validarClienteDTO(ClienteDTO clienteDTO) {
        if (!clienteDTO.getManejoCredito()) {
            clienteDTO.setLimiteCredito(BigDecimal.ZERO);
            clienteDTO.setDiasCredito(0);
        } else {
            if (clienteDTO.getLimiteCredito() == null || clienteDTO.getLimiteCredito().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("El límite de crédito debe ser mayor que 0 si se maneja crédito.");
            }
            if (clienteDTO.getDiasCredito() == null || clienteDTO.getDiasCredito() <= 0) {
                throw new ValidationException("Los días de crédito deben ser mayores que 0 si se maneja crédito.");
            }
        }
    }
}
