package com.tiendita.tienditaventas.repository;
import com.tiendita.tienditaventas.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
