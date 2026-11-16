package com.example.concessionaria.casa_madre.mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.concessionaria.casa_madre.dto.CasaMadreRequest;
import com.example.concessionaria.casa_madre.dto.CasaMadreResponseDTO;
import com.example.concessionaria.casa_madre.entity.CasaMadre;
import com.example.concessionaria.concessionario.entity.Concessionario;

@Component
public class CasaMadreMapper {

    public CasaMadre toEntity(CasaMadreRequest request) {
        CasaMadre casaMadre = new CasaMadre();
        casaMadre.setRagioneSociale(request.ragioneSociale());
        casaMadre.setSedeLegale(request.sedeLegale());
        casaMadre.setPaese(request.paese());
        casaMadre.setAnnoFondazione(request.annoFondazione());
        casaMadre.setFatturatoAnnuo(request.fatturatoAnnuo());
        return casaMadre;
    }

    public CasaMadreResponseDTO toResponse(CasaMadre casaMadre) {
        List<Concessionario> concessionari = casaMadre.getConcessionari();
        BigDecimal fatturatoTotaleConcessionari = concessionari.stream()
                .map(Concessionario::getFatturato)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CasaMadreResponseDTO(
                casaMadre.getId(),
                casaMadre.getRagioneSociale(),
                casaMadre.getPaese(),
                casaMadre.getFatturatoAnnuo(),
                concessionari.size(),
                fatturatoTotaleConcessionari);
    }
}
