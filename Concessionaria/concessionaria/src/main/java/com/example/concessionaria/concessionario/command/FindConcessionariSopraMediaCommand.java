package com.example.concessionaria.concessionario.command;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.concessionario.dto.ConcessionarioResponseDTO;
import com.example.concessionaria.concessionario.service.ConcessionarioService;

import lombok.RequiredArgsConstructor;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class FindConcessionariSopraMediaCommand implements Command<List<ConcessionarioResponseDTO>> {

    private final ConcessionarioService concessionarioService;

    @Override
    public List<ConcessionarioResponseDTO> execute() {
        return concessionarioService.findSopraMediaFatturato();
    }
}
