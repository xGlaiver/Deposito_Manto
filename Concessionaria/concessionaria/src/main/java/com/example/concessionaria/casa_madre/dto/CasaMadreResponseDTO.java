package com.example.concessionaria.casa_madre.dto;

import java.math.BigDecimal;

public record CasaMadreResponseDTO(
        Long id,
        String ragioneSociale,
        String paese,
        BigDecimal fatturatoAnnuo,
        Integer numeroConcessionari,
        BigDecimal fatturatoTotaleConcessionari
) {}
