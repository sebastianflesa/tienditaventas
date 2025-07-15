package com.tiendita.tienditaventas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tiendita.tienditaventas.models.Producto;
import com.tiendita.tienditaventas.repository.ProductoRepository;
import com.tiendita.tienditaventas.service.MensajeService;

@RestController
@RequestMapping("/apitest")
public class MensajeController {
    
    @Autowired
    private ProductoRepository productoRepository;

	private final MensajeService mensajeService;
    
    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

	@GetMapping("/productos")
	public ResponseEntity<String> index() {
        List<Producto> productos = productoRepository.findAll();
        Boolean hayProductos = !productos.isEmpty();
        if (!hayProductos) {
            return ResponseEntity.ok("No hay productos disponibles.");
        }

        return ResponseEntity.ok("Microservicio de ventas API, envia mensajes a broker RabbitMQ. Conexión BD: " + hayProductos);
	}

	@PostMapping("/mensajes")
	public ResponseEntity<String> enviar(@RequestBody String mensaje) {
		mensajeService.enviarMensaje(mensaje);
		System.err.println("Mensaje enviado al broker: " + mensaje);
		return ResponseEntity.ok(mensaje);
	}

	@PostMapping("/mensajes-directo")
	public ResponseEntity<String> enviarDirecto(@RequestBody String mensaje) {
		try {
			System.out.println("Enviando mensaje directamente a la queue: " + mensaje);
			// Inyectar RabbitTemplate directamente para prueba
			org.springframework.amqp.rabbit.core.RabbitTemplate template = 
				new org.springframework.amqp.rabbit.core.RabbitTemplate(
					new org.springframework.amqp.rabbit.connection.CachingConnectionFactory("localhost", 5672)
				);
			template.setMessageConverter(new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter());
			
			template.convertAndSend("ventas", mensaje);
			System.out.println("Mensaje enviado directamente a queue 'ventas'");
			return ResponseEntity.ok("Mensaje enviado directamente a queue: " + mensaje);
		} catch (Exception e) {
			System.err.println("Error enviando mensaje directo: " + e.getMessage());
			return ResponseEntity.status(500).body("Error: " + e.getMessage());
		}
	}

	/*
	@PostMapping("/usuarios")
	public ResponseEntity<String> enviarObjetoUsuario(@RequestBody UsuarioDTO usuario) {

		mensajeService.enviarObjeto(usuario);
		return ResponseEntity.ok("Mensaje enviado: " + usuario.toString());
	}

	@PostMapping("/prodductos")
	public ResponseEntity<String> enviarObjetoProducto(@RequestBody ProductoDTO producto) {

		mensajeService.enviarObjeto(producto);
		return ResponseEntity.ok("Mensaje enviado: " + producto.toString());
	}*/
}
