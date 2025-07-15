package com.tiendita.tienditaventas.controller;

import com.tiendita.tienditaventas.dto.VentaDetalleDTO;
import com.tiendita.tienditaventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VentaDetalleDTO>> obtenerVentasPorUsuario(@PathVariable Long usuarioId) {
        List<VentaDetalleDTO> ventas = ventaService.obtenerVentasDetalleByUsuarioId(usuarioId);
        return ResponseEntity.ok(ventas);
    }
}
