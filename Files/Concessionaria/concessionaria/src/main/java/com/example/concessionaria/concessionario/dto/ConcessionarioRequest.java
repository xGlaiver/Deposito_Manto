package com.example.concessionaria.concessionario.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConcessionarioRequest(
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(max = 100)
        String citta,

        @Size(max = 150)
        String indirizzo,

        @Size(max = 20)
        String telefono,

        @Digits(integer = 13, fraction = 2)
        BigDecimal fatturato,

        @NotNull
        Long casaMadreId
) {}
