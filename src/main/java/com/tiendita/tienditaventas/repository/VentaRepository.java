package com.tiendita.tienditaventas.repository;

import com.tiendita.tienditaventas.dto.VentaDetalleDTO;
import com.tiendita.tienditaventas.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    @Query("SELECT new com.tiendita.tienditaventas.dto.VentaDetalleDTO(" +
           "v.id, p.id, p.valor, p.nombre, v.cantidad, v.carroId, v.usuarioId) " +
           "FROM Venta v JOIN Producto p ON v.productoId = p.id " +
           "WHERE v.usuarioId = :usuarioId")
    List<VentaDetalleDTO> findVentasDetalleByUsuarioId(@Param("usuarioId") Long usuarioId);
}
