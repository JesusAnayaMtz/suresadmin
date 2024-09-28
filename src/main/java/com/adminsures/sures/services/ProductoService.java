package com.adminsures.sures.services;

import com.adminsures.sures.dto.ProductoDTO;
import com.adminsures.sures.entitys.Producto;
import com.adminsures.sures.mapper.ProductoMapper;
import com.adminsures.sures.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private static final String UPLOAD_DIRECTORY = "C:/GitHub/suresadmin/images";

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> obtenerTodos(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(productoMapper::toDTO).toList();
    }

    public ProductoDTO obtenerProductoPorId(Long id) throws Exception {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO crearProductoSinImagen(ProductoDTO productoDTO) throws Exception {
        validarProducto(productoDTO);

        Producto producto = productoMapper.toEntity(productoDTO);

        // Calcular Utilidad
        producto.setUtilidad(calcularUtilidad(producto.getCosto(), producto.getPrecio()));

        // Guardar producto sin imagen
        producto = productoRepository.save(producto);
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO subirImagenProducto(Long id, MultipartFile imagen) throws Exception {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        if (imagen != null && !imagen.isEmpty()) {
            String rutaImagen = guardarImagen(imagen);
            producto.setRutaImagen(rutaImagen);
        }

        producto = productoRepository.save(producto);
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO, MultipartFile imagen) throws Exception {
        Producto productoExistente = productoRepository.findById(id).orElseThrow(() -> new Exception("Producto no encontrado"));

        validarProducto(productoDTO);

        productoExistente.setClaveInterna(productoDTO.getClaveInterna());
        productoExistente.setCodigoBarras(productoDTO.getCodigoBarras());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setClaveSat(productoDTO.getClaveSat());
        productoExistente.setPrecio(productoDTO.getCosto());
        productoExistente.setCosto(productoDTO.getCosto());
        productoExistente.setUnidadVenta(productoDTO.getUnidadVenta());
        productoExistente.setCategoria(productoDTO.getCategoria());
        productoExistente.setExistencia(productoDTO.getExistencia());
        productoExistente.setExistenciaMinima(productoDTO.getExistenciaMinima());
        productoExistente.setFechaActualizacion(LocalDate.now());

        //Recalcular la en caso de que cambie el precio o el costo
        productoExistente.setUtilidad(calcularUtilidad(productoExistente.getCosto(), productoExistente.getPrecio()));

        //Si hay una nueva imagen guardarla
        if (imagen != null && imagen.isEmpty()){
            String rutaImagen = guardarImagen(imagen);
            productoExistente.setRutaImagen(rutaImagen);
        }

        productoExistente = productoRepository.save(productoExistente);
        return productoMapper.toDTO(productoExistente);
    }

    public void desactvarProducto(Long id) throws Exception {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new Exception("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    // Método para obtener productos por nombre o código de barras
    public List<ProductoDTO> buscarPorNombreOCodigoBarras(String searchTerm) {
        List<Producto> productos = productoRepository.findByClaveInternaContainingIgnoreCaseOrCodigoBarrasContainingIgnoreCase(searchTerm, searchTerm);
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    public String guardarImagen(MultipartFile imagen) throws Exception {
        byte[] bytes = imagen.getBytes();
        Path path = Paths.get(UPLOAD_DIRECTORY + "/" + imagen.getOriginalFilename());
        Files.write(path, bytes);
        return path.toString();
    }

    private Double calcularUtilidad(Double costo, Double precio) {
        if (costo == null || precio == null) {
            return 0.0;
        }
        return precio - costo;  // Fórmula para la utilidad
    }

    private void validarProducto(ProductoDTO productoDTO) throws Exception {
        Optional<Producto> productoExistenteNombre = productoRepository.findByClaveInterna(productoDTO.getClaveInterna());
        if (productoExistenteNombre.isPresent()) {
            throw new Exception("Ya existe un producto con ese nombre.");
        }

        Optional<Producto> productoExistenteCodigoBarras = productoRepository.findByCodigoBarras(productoDTO.getCodigoBarras());
        if (productoExistenteCodigoBarras.isPresent()) {
            throw new Exception("Ya existe un producto con ese código de barras.");
        }
    }
}
