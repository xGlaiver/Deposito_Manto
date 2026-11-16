package com.example.concessionaria.casa_madre.command;

import java.util.Objects;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.casa_madre.dto.CasaMadreRequest;
import com.example.concessionaria.casa_madre.dto.CasaMadreResponseDTO;
import com.example.concessionaria.casa_madre.service.CasaMadreService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class AggiornaCasaMadreCommand implements Command<CasaMadreResponseDTO> {

    private final CasaMadreService casaMadreService;

    @Setter
    private Long id;

    @Setter
    private CasaMadreRequest request;

    @Override
    public CasaMadreResponseDTO execute() {
        Objects.requireNonNull(id, "id non impostato");
        Objects.requireNonNull(request, "request non impostata");
        return casaMadreService.aggiorna(id, request);
    }
}
