package com.example.concessionaria.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.concessionaria.cliente.dto.ClienteRequest;
import com.example.concessionaria.cliente.dto.ClienteResponseDTO;
import com.example.concessionaria.cliente.entity.Cliente;
import com.example.concessionaria.cliente.mapping.ClienteMapper;
import com.example.concessionaria.cliente.repository.ClienteRepository;
import com.example.concessionaria.common.exception.ConflictException;
import com.example.concessionaria.common.exception.ResourceNotFoundException;
import com.example.concessionaria.concessionario.entity.Concessionario;
import com.example.concessionaria.concessionario.repository.ConcessionarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ConcessionarioRepository concessionarioRepository;
    private final ClienteMapper clienteMapper;

    @Transactional
    public ClienteResponseDTO crea(ClienteRequest request) {
        if (request.email() != null && clienteRepository.existsByEmail(request.email())) {
            throw new ConflictException(
                    "Email già registrata: " + request.email());
        }
        Concessionario concessionario = trovaConcessionarioOrThrow(request.concessionarioId());
        Cliente cliente = clienteMapper.toEntity(request, concessionario);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        return clienteMapper.toResponse(trovaOrThrow(id));
    }

    @Transactional
    public ClienteResponseDTO aggiorna(Long id, ClienteRequest request) {
        Cliente esistente = trovaOrThrow(id);
        if (request.email() != null && clienteRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new ConflictException(
                    "Email già registrata: " + request.email());
        }
        Concessionario concessionario = trovaConcessionarioOrThrow(request.concessionarioId());
        Cliente cliente = clienteMapper.toEntity(request, concessionario);
        cliente.setId(id);
        cliente.setDataRegistrazione(esistente.getDataRegistrazione());
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public void elimina(Long id) {
        clienteRepository.delete(trovaOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findByConcessionario(Long concessionarioId) {
        return toResponseList(clienteRepository.findClientiByConcessionarioId(concessionarioId));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findByCasaMadre(Long casaMadreId) {
        return toResponseList(clienteRepository.findClientiByCasaMadreId(casaMadreId));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findByCittaConcessionario(String citta) {
        return toResponseList(clienteRepository.findClientiByCittaConcessionario(citta));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findByPaeseCasaMadre(String paese) {
        return toResponseList(clienteRepository.findClientiByPaeseCasaMadre(paese));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> search(String nome, String cognome) {
        return toResponseList(clienteRepository.searchClienti(nome, cognome));
    }

    private List<ClienteResponseDTO> toResponseList(List<Cliente> clienti) {
        return clienti.stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    private Cliente trovaOrThrow(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente non trovato: " + id));
    }

    private Concessionario trovaConcessionarioOrThrow(Long concessionarioId) {
        return concessionarioRepository.findById(concessionarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Concessionario non trovato: " + concessionarioId));
    }
}
