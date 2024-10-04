package com.adminsures.sures.controllers;

import com.adminsures.sures.dto.CotizacionDTO;
import com.adminsures.sures.services.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    @GetMapping
    public List<CotizacionDTO> obtenerCotizaciones(){
        return cotizacionService.obtenerTodasLasCotizaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotizacionDTO> obtenerCotizacion(@PathVariable Long id) throws Exception {
        CotizacionDTO cotizacionDTO = cotizacionService.obtenerCotizacion(id);
        return ResponseEntity.ok(cotizacionDTO);
    }

    @PostMapping
    public CotizacionDTO crearCotizacion(@RequestBody CotizacionDTO cotizacionDTO) throws Exception {
        return cotizacionService.crearCotizacion(cotizacionDTO);
    }

    @PutMapping("/{id}")
    public CotizacionDTO actualizarCotizacion(@PathVariable Long id, @RequestBody CotizacionDTO cotizacionDTO) throws Exception {
        return cotizacionService.actualizarCotizacion(id, cotizacionDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarCotizacion(@PathVariable Long id){
        cotizacionService.eliminarCotizacion(id);
    }
}
