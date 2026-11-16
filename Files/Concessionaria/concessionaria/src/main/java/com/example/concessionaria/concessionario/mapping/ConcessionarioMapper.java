package com.example.concessionaria.concessionario.mapping;

import org.springframework.stereotype.Component;

import com.example.concessionaria.casa_madre.entity.CasaMadre;
import com.example.concessionaria.concessionario.dto.ConcessionarioRequest;
import com.example.concessionaria.concessionario.dto.ConcessionarioResponseDTO;
import com.example.concessionaria.concessionario.entity.Concessionario;

@Component
public class ConcessionarioMapper {

    public Concessionario toEntity(ConcessionarioRequest request, CasaMadre casaMadre) {
        Concessionario concessionario = new Concessionario();
        concessionario.setNome(request.nome());
        concessionario.setCitta(request.citta());
        concessionario.setIndirizzo(request.indirizzo());
        concessionario.setTelefono(request.telefono());
        concessionario.setFatturato(request.fatturato());
        concessionario.setCasaMadre(casaMadre);
        return concessionario;
    }

    public ConcessionarioResponseDTO toResponse(Concessionario concessionario) {
        return new ConcessionarioResponseDTO(
                concessionario.getId(),
                concessionario.getNome(),
                concessionario.getCitta(),
                concessionario.getFatturato(),
                concessionario.getCasaMadre().getRagioneSociale(),
                concessionario.getClienti().size());
    }
}
