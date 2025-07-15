package com.tiendita.tienditaventas.service;

import com.tiendita.tienditaventas.models.Promocion;

import java.util.Optional;

public interface PromocionService {
    Optional<Promocion> obtenerUltimaPromocion();
}
