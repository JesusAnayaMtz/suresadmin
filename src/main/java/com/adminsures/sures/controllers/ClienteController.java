package com.adminsures.sures.controllers;


import com.adminsures.sures.dto.ClienteDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> getAllClientes(){
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id){
        ClienteDTO clienteDTO = clienteService.getClienteById(id);
        if (clienteDTO !=null) {
            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarClientes(@RequestParam("q") String searchTerm){
        List<ClienteDTO> clientes = clienteService.buscarPorNombreOrRfc(searchTerm);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        ClienteDTO newCliente = clienteService.createCliente(clienteDTO);
        return ResponseEntity.ok(newCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id,@Valid @RequestBody ClienteDTO clienteDTO){
        ClienteDTO updateCliente = clienteService.updateCliente(id, clienteDTO);
        if(updateCliente != null){
            return ResponseEntity.ok(updateCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
        clienteService.deleteCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Cliente> activarCliente(@PathVariable Long id) {
        try {
            Cliente clienteActivado = clienteService.activarCliente(id);
            return ResponseEntity.ok(clienteActivado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // Cliente no encontrado o ya está activo
        }
    }
}
