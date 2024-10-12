package com.adminsures.sures.repository;

import com.adminsures.sures.entitys.Cliente;
import com.adminsures.sures.entitys.Empleado;
import com.adminsures.sures.entitys.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    //Metodos para buscar un producto por el nombre
    Optional<Empleado> findByRfc(String rfc);

    //Metodo para buscar un producto por su codigo de barras
    Optional<Empleado> findByCurp(String curp);

    //Metodo para buscar un producto por su codigo de barras
    Optional<Empleado> findByNumeroEmpleado(Integer numeroEmpleado);

    //Metodo para obtener los clientes activos
    List<Empleado> findByActivoTrue();

    //Buscar por apellido o rfc usando Like para conidencias en el campo nombre o rfc
    @Query("SELECT e FROM Empleado e WHERE LOWER(e.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            " OR LOWER(e.rfc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND e.activo = true")
    List<Empleado> findByApellidoOrRfc(@Param("searchTerm") String searchTerm);

    //Metodo para activar un empleado
    Optional<Empleado> findByIdAndActivoFalse(Long id);
}
