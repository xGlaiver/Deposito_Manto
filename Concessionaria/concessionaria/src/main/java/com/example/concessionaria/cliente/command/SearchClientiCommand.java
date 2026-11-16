package com.example.concessionaria.cliente.command;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.cliente.dto.ClienteResponseDTO;
import com.example.concessionaria.cliente.service.ClienteService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class SearchClientiCommand implements Command<List<ClienteResponseDTO>> {

    private final ClienteService clienteService;

    @Setter
    private String nome;

    @Setter
    private String cognome;

    @Override
    public List<ClienteResponseDTO> execute() {
        return clienteService.search(nome, cognome);
    }
}
