package com.adminsures.sures.controllers;

import com.adminsures.sures.dto.ClienteDTO;
import com.adminsures.sures.dto.ProveedorDTO;
import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Proveedor;
import com.adminsures.sures.services.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<ProveedorDTO> getAllProveedores(){
        return proveedorService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedoresById(@PathVariable Long id){
        ProveedorDTO proveedorDTO = proveedorService.getProveedorById(id);
        if (proveedorDTO !=null) {
            return ResponseEntity.ok(proveedorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProveedorDTO>> buscarProveedores(@RequestParam("q") String searchTerm){
        List<ProveedorDTO> proveedores = proveedorService.buscarPorNombreOrRfc(searchTerm);
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO){
        ProveedorDTO newProveedor = proveedorService.createProveedor(proveedorDTO);
        return ResponseEntity.ok(newProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id,@Valid @RequestBody ProveedorDTO proveedorDTO){
        ProveedorDTO updateProveedor = proveedorService.updateProveedor(id, proveedorDTO);
        if(updateProveedor != null){
            return ResponseEntity.ok(updateProveedor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id){
        proveedorService.deleteProveedor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Proveedor> activarProveedor(@PathVariable Long id) {
        try {
            Proveedor proveedorActivado = proveedorService.activarProveedor(id);
            return ResponseEntity.ok(proveedorActivado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // Cliente no encontrado o ya está activo
        }
    }
}
