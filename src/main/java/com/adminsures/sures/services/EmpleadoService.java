package com.adminsures.sures.services;

import com.adminsures.sures.dto.EmpleadoDTO;
import com.adminsures.sures.entitys.Empleado;
import com.adminsures.sures.mapper.EmpleadoMapper;
import com.adminsures.sures.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private static final String UPLOAD_DIRECTORY = "empleadoimg/";

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    public List<EmpleadoDTO> obtenerTodos(){
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados.stream().map(empleadoMapper::toDTO).toList();
    }

    //Metodo para obtener los empleados;
    public List<EmpleadoDTO> obtenerEmpleadosActivos(){
        List<Empleado> empleados = empleadoRepository.findByActivoTrue();
        return empleados.stream()
                .map(empleadoMapper::toDTO)
                .collect(Collectors.toList());

    }

    public EmpleadoDTO obtenerEmpleadoPorId(Long id) throws Exception {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new Exception("Empleado no encontrado"));
        return empleadoMapper.toDTO(empleado);
    }

    public EmpleadoDTO crearEmpleadoSinImagen(EmpleadoDTO empleadoDTO) throws Exception {
        validarEmpleado(empleadoDTO);

        Empleado empleado = empleadoMapper.toEntity(empleadoDTO);

        // Guardar producto sin imagen
        empleado = empleadoRepository.save(empleado);
        return empleadoMapper.toDTO(empleado);
    }

    public EmpleadoDTO subirImagenEmpleado(Long id, MultipartFile imagen) throws Exception {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new Exception("Empleado no encontrado"));

        if (imagen != null && !imagen.isEmpty()) {
            String rutaImagen = guardarImagen(imagen);
            empleado.setRutaImagen(rutaImagen);
        }

        empleado = empleadoRepository.save(empleado);
        return empleadoMapper.toDTO(empleado);
    }

    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO empleadoDTO) throws Exception {
        Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(() -> new Exception("Empleado no encontrado"));

        validarEmpleadoActualizado(empleadoDTO, empleadoExistente);

        empleadoExistente.setNumeroEmpleado(empleadoDTO.getNumeroEmpleado());
        empleadoExistente.setNombre(empleadoDTO.getNombre());
        empleadoExistente.setApellido(empleadoDTO.getApellido());
        empleadoExistente.setApellidoMaterno(empleadoDTO.getApellidoMaterno());
        empleadoExistente.setRfc(empleadoDTO.getRfc());
        empleadoExistente.setCurp(empleadoDTO.getCurp());
        empleadoExistente.setDireccion(empleadoDTO.getDireccion());
        empleadoExistente.setColonia(empleadoDTO.getColonia());
        empleadoExistente.setCiudad(empleadoDTO.getCiudad());
        empleadoExistente.setEmail(empleadoDTO.getEmail());
        empleadoExistente.setTelefono(empleadoDTO.getTelefono());
        empleadoExistente.setCelular(empleadoDTO.getCelular());
        empleadoExistente.setNss(empleadoDTO.getNss());
        empleadoExistente.setDepartamento(empleadoDTO.getDepartamento());
        empleadoExistente.setPuesto(empleadoDTO.getPuesto());
        empleadoExistente.setBanco(empleadoDTO.getBanco());
        empleadoExistente.setCuentaBancaria(empleadoDTO.getCuentaBancaria());
        empleadoExistente.setSalarioBase(empleadoDTO.getSalarioBase());
        empleadoExistente.setSalarioIntegrado(empleadoDTO.getSalarioIntegrado());


        empleadoExistente = empleadoRepository.save(empleadoExistente);
        return empleadoMapper.toDTO(empleadoExistente);
    }

    public void desactivarEmpleado(Long id) throws Exception {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new Exception("Empleado no encontrado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }

    public Resource obtenerImagen(String nombreImagen) throws Exception {
        Path imagePath = Paths.get(UPLOAD_DIRECTORY).resolve(nombreImagen);

        if (!Files.exists(imagePath)) {
            throw new FileNotFoundException("La imagen no se encuentra en el servidor");
        }

        return new UrlResource(imagePath.toUri());
    }

    private void validarEmpleado(EmpleadoDTO empleadoDTO) throws Exception {
        Optional<Empleado> empleadoExistenteRfc = empleadoRepository.findByRfc(empleadoDTO.getRfc());
        if (empleadoExistenteRfc.isPresent()) {
            throw new Exception("Ya existe un empleado con ese rfc.");
        }

        Optional<Empleado> empleadoExistenteCurp = empleadoRepository.findByCurp(empleadoDTO.getCurp());
        if (empleadoExistenteCurp.isPresent()) {
            throw new Exception("Ya existe un empleado con ese curp.");
        }
    }

    private void validarEmpleadoActualizado(EmpleadoDTO empleadoDTO, Empleado empleadoExistente) throws Exception {
        // Validar solo si la clave interna ha cambiado
        if (!empleadoExistente.getRfc().equals(empleadoDTO.getRfc())) {
            Optional<Empleado> empleadoExistenteRfc = empleadoRepository.findByRfc(empleadoDTO.getRfc());
            if (empleadoExistenteRfc.isPresent()) {
                throw new Exception("Ya existe un empleado con ese rfc.");
            }
        }

        // Validar solo si el código de barras ha cambiado
        if (!empleadoExistente.getNumeroEmpleado().equals(empleadoDTO.getNumeroEmpleado())) {
            Optional<Empleado> empleadoExistenteNumeroEmpleado = empleadoRepository.findByNumeroEmpleado(empleadoDTO.getNumeroEmpleado());
            if (empleadoExistenteNumeroEmpleado.isPresent()) {
                throw new Exception("Ya existe un Empleado con ese numero de empleado.");
            }
        }
    }

    public String guardarImagen(MultipartFile imagen) throws Exception {
        Path directoryPath = Paths.get(UPLOAD_DIRECTORY);

        // Crear el directorio si no existe
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        byte[] bytes = imagen.getBytes();
        Path imagePath = directoryPath.resolve(imagen.getOriginalFilename());
        Files.write(imagePath, bytes);

        return imagePath.toString();
    }

}
