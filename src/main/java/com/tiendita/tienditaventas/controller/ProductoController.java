package com.tiendita.tienditaventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tiendita.tienditaventas.models.Producto;
import com.tiendita.tienditaventas.service.ProductoService;
import com.tiendita.tienditaventas.service.VentaService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    public ProductoController(ProductoService productoService, VentaService ventaService) {
        // Los autowired fields se inyectan automáticamente
    }

    @PostMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }


    @PostMapping("/venta")
    public ResponseEntity<String> realizarVenta(@RequestBody String mensaje) {
        try {
            ventaService.procesarVentaCompleta(mensaje);
            System.out.println("Venta procesada exitosamente: " + mensaje);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            System.err.println("Error procesando venta: " + e.getMessage());
            return ResponseEntity.status(500).body("Error procesando venta: " + e.getMessage());
        }
    }
    
    @GetMapping("/ventas")
    public ResponseEntity<String> obtenerVentas() {
        try {
            var ventas = ventaService.obtenerTodasLasVentas();
            return ResponseEntity.ok("Total de ventas registradas: " + ventas.size());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error obteniendo ventas: " + e.getMessage());
        }
    }
    
    /*

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoService.obtenerProductoPorId(id);
        if (productoExistente.isPresent()) {
            producto.setId(id);
            Producto productoActualizado = productoService.guardarProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<String> actualizarStock(@PathVariable Long id, @RequestParam Integer nuevoStock) {
        boolean actualizado = productoService.actualizarStock(id, nuevoStock);
        if (actualizado) {
            return ResponseEntity.ok("Stock actualizado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reducir-stock")
    public ResponseEntity<String> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        boolean reducido = productoService.reducirStock(id, cantidad);
        if (reducido) {
            return ResponseEntity.ok("Stock reducido correctamente");
        }
        return ResponseEntity.badRequest().body("Stock insuficiente o producto no encontrado");
    }
        */
}
