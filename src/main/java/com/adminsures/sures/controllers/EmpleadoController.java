package com.adminsures.sures.controllers;

import com.adminsures.sures.dto.EmpleadoDTO;
import com.adminsures.sures.dto.ProductoDTO;
import com.adminsures.sures.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<EmpleadoDTO> obtenerTodosLosEmpleados(){
        return empleadoService.obtenerTodos();
    }

    @GetMapping("/activos")
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos(){
        return empleadoService.obtenerEmpleadosActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long id) throws Exception {
        EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws Exception {
        EmpleadoDTO nuevoEmpleado = empleadoService.crearEmpleadoSinImagen(empleadoDTO);
        return ResponseEntity.ok(nuevoEmpleado);
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<EmpleadoDTO> subirImagenEmpleado(
            @PathVariable Long id,
            @RequestPart("imagen") MultipartFile imagen) throws Exception {

        EmpleadoDTO empeladoActualizado = empleadoService.subirImagenEmpleado(id, imagen);
        return ResponseEntity.ok(empeladoActualizado);
    }

    @GetMapping("/empleadoimg/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagenEmpleado(@PathVariable String nombreImagen) {
        try {
            Resource imagen = empleadoService.obtenerImagen(nombreImagen);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo según corresponda (IMAGE_PNG, etc.)
                    .body(imagen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable Long id,@RequestBody EmpleadoDTO empleadoDTO) throws Exception {
        EmpleadoDTO empleadoActualizado = empleadoService.actualizarEmpleado(id, empleadoDTO);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarEmpleado(@PathVariable Long id) throws Exception {
        empleadoService.desactivarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
