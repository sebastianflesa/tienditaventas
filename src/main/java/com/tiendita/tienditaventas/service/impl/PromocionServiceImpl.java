package com.tiendita.tienditaventas.service.impl;

import com.tiendita.tienditaventas.models.Promocion;
import com.tiendita.tienditaventas.repository.PromocionRepository;
import com.tiendita.tienditaventas.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Override
    public Optional<Promocion> obtenerUltimaPromocion() {
        try {
            return promocionRepository.findTopByOrderByIdDesc();
        } catch (Exception e) {
            System.err.println("Error al obtener la última promoción: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
