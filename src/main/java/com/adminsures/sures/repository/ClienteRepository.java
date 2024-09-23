package com.adminsures.sures.repository;

import com.adminsures.sures.entitys.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Verifica si existe un cliente con el mismo rfc
    boolean existsByRfc(String rfc);

    //Verifica si existe un cliente con el mismo rfc y un id diferente
    boolean existsByRfcAndIdNot(String rfc, Long id);

    //Metodo para obtener los clientes activos
    List<Cliente> findByActivoTrue();

    //Buscar por nombre o rfc usando Like para conidencias en el campo nombre o rfc
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            " OR LOWER(c.rfc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND c.activo = true")
    List<Cliente> findByNombreOrRfc(@Param("searchTerm") String searchTerm);
}
