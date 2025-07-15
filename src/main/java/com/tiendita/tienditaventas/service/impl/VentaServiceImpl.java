package com.tiendita.tienditaventas.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendita.tienditaventas.dto.VentaDTO;
import com.tiendita.tienditaventas.dto.VentaDetalleDTO;
import com.tiendita.tienditaventas.dto.VentaStockDTO;
import com.tiendita.tienditaventas.models.Venta;
import com.tiendita.tienditaventas.repository.VentaRepository;
import com.tiendita.tienditaventas.service.MensajeService;
import com.tiendita.tienditaventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MensajeService mensajeService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @org.springframework.beans.factory.annotation.Value("${stock.actualizar.url}")
    private String STOCK_ACTUALIZAR_URL;

    public VentaServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Venta guardarVenta(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setProductoId(ventaDTO.getProductoId());
        venta.setCantidad(ventaDTO.getCantidad());
        venta.setUsuarioId(ventaDTO.getUsuarioId());
        venta.setCarroId(ventaDTO.getCarroId());
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public void procesarVentaCompleta(String ventasJson) {
        try {
            System.out.println("Procesando venta completa: " + ventasJson);
            
            List<VentaDTO> ventasDTO;
            try {
                ventasDTO = objectMapper.readValue(ventasJson, new TypeReference<List<VentaDTO>>() {});
            } catch (Exception e) {
                // Si falla como lista, intentar como objeto único
                VentaDTO ventaUnica = objectMapper.readValue(ventasJson, VentaDTO.class);
                ventasDTO = List.of(ventaUnica);
            }
            
            List<Venta> ventasGuardadas = ventasDTO.stream()
                    .map(this::guardarVenta)
                    .collect(Collectors.toList());
            
            System.out.println("Ventas guardadas en BD: " + ventasGuardadas.size());
            
            List<VentaStockDTO> ventasParaStock = ventasDTO.stream()
                    .map(this::convertirAVentaStock)
                    .collect(Collectors.toList());
            
            String ventasStockJson = objectMapper.writeValueAsString(ventasParaStock);
            reenviarAStockActualizar(ventasStockJson);
            
            mensajeService.enviarMensaje(ventasJson);
            System.out.println("Mensaje enviado al broker RabbitMQ: " + ventasJson);
            
        } catch (Exception e) {
            System.err.println("Error procesando venta completa: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error procesando venta: " + e.getMessage(), e);
        }
    }

    private VentaStockDTO convertirAVentaStock(VentaDTO ventaDTO) {
        VentaStockDTO ventaStock = new VentaStockDTO();
        ventaStock.setProductoId(ventaDTO.getProductoId());
        ventaStock.setCantidad(ventaDTO.getCantidad());
        ventaStock.setClienteId(ventaDTO.getUsuarioId());
        ventaStock.setCarroId(ventaDTO.getCarroId());
        return ventaStock;
    }

    private void reenviarAStockActualizar(String ventasJson) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> request = new HttpEntity<>(ventasJson, headers);
            
            System.out.println("Reenviando venta a: " + STOCK_ACTUALIZAR_URL);
            System.out.println("JSON enviado: " + ventasJson);
            
            String response = restTemplate.postForObject(STOCK_ACTUALIZAR_URL, request, String.class);
            System.out.println("Respuesta del endpoint stock: " + response);
            
        } catch (Exception e) {
            System.err.println("Error reenviando a stock/actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<VentaDetalleDTO> obtenerVentasDetalleByUsuarioId(Long usuarioId) {
        return ventaRepository.findVentasDetalleByUsuarioId(usuarioId);
    }
}
