package com.example.concessionaria.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank
        @Size(max = 50)
        String nome,

        @NotBlank
        @Size(max = 50)
        String cognome,

        @Email
        @Size(max = 100)
        String email,

        @Size(max = 20)
        String telefono,

        @NotNull
        Long concessionarioId
) {}
