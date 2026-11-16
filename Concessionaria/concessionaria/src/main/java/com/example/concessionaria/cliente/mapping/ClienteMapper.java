package com.example.concessionaria.cliente.mapping;

import org.springframework.stereotype.Component;

import com.example.concessionaria.cliente.dto.ClienteRequest;
import com.example.concessionaria.cliente.dto.ClienteResponseDTO;
import com.example.concessionaria.cliente.entity.Cliente;
import com.example.concessionaria.concessionario.entity.Concessionario;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request, Concessionario concessionario) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setCognome(request.cognome());
        cliente.setEmail(request.email());
        cliente.setTelefono(request.telefono());
        cliente.setConcessionario(concessionario);
        return cliente;
    }

    public ClienteResponseDTO toResponse(Cliente cliente) {
        Concessionario concessionario = cliente.getConcessionario();
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCognome(),
                cliente.getEmail(),
                concessionario.getNome(),
                concessionario.getCitta(),
                concessionario.getCasaMadre().getRagioneSociale());
    }
}
