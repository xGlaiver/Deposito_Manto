package com.example.concessionaria.casa_madre.command;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.casa_madre.dto.CasaMadreResponseDTO;
import com.example.concessionaria.casa_madre.service.CasaMadreService;

import lombok.RequiredArgsConstructor;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class FindCaseMadriSenzaConcessionariCommand implements Command<List<CasaMadreResponseDTO>> {

    private final CasaMadreService casaMadreService;

    @Override
    public List<CasaMadreResponseDTO> execute() {
        return casaMadreService.findSenzaConcessionari();
    }
}
