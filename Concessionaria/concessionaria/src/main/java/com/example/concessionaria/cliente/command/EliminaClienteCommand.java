package com.example.concessionaria.cliente.command;

import java.util.Objects;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.concessionaria.common.command.Command;
import com.example.concessionaria.cliente.service.ClienteService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class EliminaClienteCommand implements Command<Void> {

    private final ClienteService clienteService;

    @Setter
    private Long id;

    @Override
    public Void execute() {
        Objects.requireNonNull(id, "id non impostato");
        clienteService.elimina(id);
        return null;
    }
}
