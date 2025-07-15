package com.tiendita.tienditaventas.repository;

import com.tiendita.tienditaventas.models.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
    Optional<Promocion> findTopByOrderByIdDesc();
    
}
