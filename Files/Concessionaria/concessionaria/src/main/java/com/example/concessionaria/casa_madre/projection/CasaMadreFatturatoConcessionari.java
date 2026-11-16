package com.example.concessionaria.casa_madre.projection;

import java.math.BigDecimal;

public record CasaMadreFatturatoConcessionari(
        Long casaMadreId,
        String ragioneSociale,
        BigDecimal fatturatoComplessivo
) {}
