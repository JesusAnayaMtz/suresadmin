package com.adminsures.sures.controllers;

import com.adminsures.sures.dto.ProductoDTO;
import com.adminsures.sures.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/activos")
    public List<ProductoDTO> obtenerTodosProductosActivos(){
        return productoService.obtenerProductosActivos();
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

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO nuevoProducto = productoService.crearProductoSinImagen(productoDTO);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<ProductoDTO> subirImagenProducto(
            @PathVariable Long id,
            @RequestPart("imagen") MultipartFile imagen) throws Exception {

        ProductoDTO productoActualizado = productoService.subirImagenProducto(id, imagen);
        return ResponseEntity.ok(productoActualizado);
    }

    @GetMapping("/images/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagenProducto(@PathVariable String nombreImagen) {
        try {
            Resource imagen = productoService.obtenerImagen(nombreImagen);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo según corresponda (IMAGE_PNG, etc.)
                    .body(imagen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id,@RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) throws Exception {
        productoService.desactivarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
