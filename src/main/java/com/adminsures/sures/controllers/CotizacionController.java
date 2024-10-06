package com.adminsures.sures.controllers;

import com.adminsures.sures.dto.CotizacionDTO;
import com.adminsures.sures.services.CotizacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @GetMapping
    public ResponseEntity<List<CotizacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(cotizacionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotizacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CotizacionDTO> crearCotizacion(@RequestBody CotizacionDTO cotizacionDTO) {
        return ResponseEntity.ok(cotizacionService.crearCotizacion(cotizacionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CotizacionDTO> actualizarCotizacion(@PathVariable Long id, @RequestBody CotizacionDTO cotizacionDTO) {
        return ResponseEntity.ok(cotizacionService.actualizarCotizacion(id, cotizacionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCotizacion(@PathVariable Long id) {
        cotizacionService.eliminarCotizacion(id);
        return ResponseEntity.noContent().build();
    }
}