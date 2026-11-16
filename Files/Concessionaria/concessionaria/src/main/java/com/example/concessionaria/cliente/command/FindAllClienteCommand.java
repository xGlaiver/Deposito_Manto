package com.example.concessionaria.cliente.command;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.cliente.dto.ClienteResponseDTO;
import com.example.concessionaria.cliente.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class FindAllClienteCommand implements Command<List<ClienteResponseDTO>> {

    private final ClienteService clienteService;

    @Override
    public List<ClienteResponseDTO> execute() {
        return clienteService.findAll();
    }
}
