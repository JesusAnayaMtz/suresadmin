package com.adminsures.sures.repository;

import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    //Verifica si existe un proveedor con el mismo rfc
    boolean existsByRfc(String rfc);

    //Verifica si existe un proveedor con el mismo rfc y un id diferente
    boolean existsByRfcAndIdNot(String rfc, Long id);

    //Metodo para obtener los proveedores activos
    List<Proveedor> findByActivoTrue();

    //Buscar por nombre o rfc usando Like para conidencias en el campo nombre o rfc
    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            " OR LOWER(p.rfc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND p.activo = true")
    List<Proveedor> findByNombreOrRfc(@Param("searchTerm") String searchTerm);

    //Metodo para activar un proveedor
    Optional<Proveedor> findByIdAndActivoFalse(Long id);
}
