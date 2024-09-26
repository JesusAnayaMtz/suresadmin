package com.adminsures.sures.controllers;

import com.adminsures.sures.dto.ProductoDTO;
import com.adminsures.sures.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> obtenerTodosProductos(){
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) throws Exception {
        ProductoDTO producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProducto(@RequestParam String searchTerm) {
        List<ProductoDTO> productos = productoService.buscarPorNombreOCodigoBarras(searchTerm);
        return ResponseEntity.ok(productos);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestPart("producto") ProductoDTO productoDTO, @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws Exception {
        ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO,imagen);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestPart("producto") ProductoDTO productoDTO, @RequestPart("imagen") MultipartFile imagen) throws Exception {
        ProductoDTO productoActualizado = productoService.actualizarProducto(id, productoDTO, imagen);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) throws Exception {
        productoService.desactvarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
