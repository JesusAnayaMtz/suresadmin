package com.adminsures.sures.repository;

import com.adminsures.sures.entitys.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Verifica si existe un cliente con el mismo rfc
    boolean existsByRfc(String rfc);

    //Verifica si existe un cliente con el mismo rfc y un id diferente
    boolean existsByRfcAndIdNot(String rfc, Long id);

    //Metodo para obtener los clientes activos
    List<Cliente> findByActivoTrue();
}
