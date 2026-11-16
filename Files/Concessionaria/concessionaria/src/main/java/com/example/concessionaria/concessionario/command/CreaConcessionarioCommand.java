package com.example.concessionaria.concessionario.command;

import java.util.Objects;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.concessionario.dto.ConcessionarioRequest;
import com.example.concessionaria.concessionario.dto.ConcessionarioResponseDTO;
import com.example.concessionaria.concessionario.service.ConcessionarioService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class CreaConcessionarioCommand implements Command<ConcessionarioResponseDTO> {

    private final ConcessionarioService concessionarioService;

    @Setter
    private ConcessionarioRequest request;

    @Override
    public ConcessionarioResponseDTO execute() {
        Objects.requireNonNull(request, "request non impostata");
        return concessionarioService.crea(request);
    }
}
