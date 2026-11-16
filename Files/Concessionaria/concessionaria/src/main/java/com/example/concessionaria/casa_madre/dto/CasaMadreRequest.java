package com.example.concessionaria.casa_madre.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CasaMadreRequest(
        @NotBlank
        @Size(max = 100) 
        String ragioneSociale,

        @NotBlank 
        @Size(max = 100) 
        String sedeLegale,

        @NotBlank 
        @Size(max = 50) 
        String paese,

        Integer annoFondazione,

        @Digits(integer = 13, fraction = 2) 
        BigDecimal fatturatoAnnuo
) {}
