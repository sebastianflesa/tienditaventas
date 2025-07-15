package com.tiendita.tienditaventas.controller;

import com.tiendita.tienditaventas.models.Promocion;
import com.tiendita.tienditaventas.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping("/promos/listar")
    public ResponseEntity<?> obtenerUltimaPromocion() {
        try {
            Optional<Promocion> ultimaPromocion = promocionService.obtenerUltimaPromocion();
            
            if (ultimaPromocion.isPresent()) {
                return ResponseEntity.ok(ultimaPromocion.get());
            } else {
                return ResponseEntity.ok("No hay promociones disponibles");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error al obtener la última promoción: " + e.getMessage());
        }
    }
}
