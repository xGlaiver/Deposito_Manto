package com.example.concessionaria.cliente.command;

import java.util.Objects;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.cliente.dto.ClienteRequest;
import com.example.concessionaria.cliente.dto.ClienteResponseDTO;
import com.example.concessionaria.cliente.service.ClienteService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class CreaClienteCommand implements Command<ClienteResponseDTO> {

    private final ClienteService clienteService;

    @Setter
    private ClienteRequest request;

    @Override
    public ClienteResponseDTO execute() {
        Objects.requireNonNull(request, "request non impostata");
        return clienteService.crea(request);
    }
}
