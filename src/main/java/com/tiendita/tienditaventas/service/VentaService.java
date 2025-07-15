package com.tiendita.tienditaventas.service;

import com.tiendita.tienditaventas.dto.VentaDTO;
import com.tiendita.tienditaventas.dto.VentaDetalleDTO;
import com.tiendita.tienditaventas.models.Venta;
import java.util.List;

public interface VentaService {
    Venta guardarVenta(VentaDTO ventaDTO);
    List<Venta> obtenerTodasLasVentas();
    void procesarVentaCompleta(String ventasJson);
    List<VentaDetalleDTO> obtenerVentasDetalleByUsuarioId(Long usuarioId);
}
