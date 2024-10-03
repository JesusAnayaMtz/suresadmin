package com.adminsures.sures.repository;

import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Metodo para obtener los productos activos
    List<Producto> findByActivoTrue();

    //Metodos para buscar un producto por el nombre
    Optional<Producto> findByClaveInterna(String claveInterna);

    //Metodo para buscar un producto por su codigo de barras
    Optional<Producto> findByCodigoBarras(String codigoBarras);

//    //Metodo para verificar si existe un producto basado en noombre y codigo de barras
//    Boolean existByNombre(String nombre);
//    boolean existByCodigoBarras(String codigoBarras);

    // Método para buscar productos que coincidan parcialmente con el nombre o el código de barras (case-insensitive)
    List<Producto> findByClaveInternaContainingIgnoreCaseOrCodigoBarrasContainingIgnoreCase(String claveInterna, String codigoBarras);
}
