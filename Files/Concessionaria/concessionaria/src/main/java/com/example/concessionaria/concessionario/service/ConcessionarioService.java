package com.example.concessionaria.concessionario.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.concessionaria.casa_madre.entity.CasaMadre;
import com.example.concessionaria.casa_madre.repository.CasaMadreRepository;
import com.example.concessionaria.cliente.repository.ClienteRepository;
import com.example.concessionaria.common.exception.ConflictException;
import com.example.concessionaria.common.exception.ResourceNotFoundException;
import com.example.concessionaria.concessionario.dto.ConcessionarioRequest;
import com.example.concessionaria.concessionario.dto.ConcessionarioResponseDTO;
import com.example.concessionaria.concessionario.entity.Concessionario;
import com.example.concessionaria.concessionario.mapping.ConcessionarioMapper;
import com.example.concessionaria.concessionario.projection.ConcessionarioNumeroClienti;
import com.example.concessionaria.concessionario.repository.ConcessionarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcessionarioService {

    private final ConcessionarioRepository concessionarioRepository;
    private final CasaMadreRepository casaMadreRepository;
    private final ClienteRepository clienteRepository;
    private final ConcessionarioMapper concessionarioMapper;

    @Transactional
    public ConcessionarioResponseDTO crea(ConcessionarioRequest request) {
        CasaMadre casaMadre = trovaCasaMadreOrThrow(request.casaMadreId());
        Concessionario concessionario = concessionarioMapper.toEntity(request, casaMadre);
        return concessionarioMapper.toResponse(concessionarioRepository.save(concessionario));
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioResponseDTO> findAll() {
        return concessionarioRepository.findAll().stream()
                .map(concessionarioMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConcessionarioResponseDTO findById(Long id) {
        return concessionarioMapper.toResponse(trovaOrThrow(id));
    }

    @Transactional
    public ConcessionarioResponseDTO aggiorna(Long id, ConcessionarioRequest request) {
        Concessionario esistente = trovaOrThrow(id);
        CasaMadre casaMadre = trovaCasaMadreOrThrow(request.casaMadreId());
        Concessionario concessionario = concessionarioMapper.toEntity(request, casaMadre);
        concessionario.setId(id);
        concessionario.setClienti(esistente.getClienti());
        return concessionarioMapper.toResponse(concessionarioRepository.save(concessionario));
    }

    @Transactional
    public void elimina(Long id) {
        Concessionario concessionario = trovaOrThrow(id);
        if (clienteRepository.existsByConcessionarioId(id)) {
            throw new ConflictException(
                    "Impossibile eliminare il concessionario " + id + ": ha ancora dei clienti associati");
        }
        concessionarioRepository.delete(concessionario);
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioResponseDTO> findByRagioneSocialeCasaMadre(String ragioneSociale) {
        return toResponseList(concessionarioRepository.findConcessionariByRagioneSocialeCasaMadre(ragioneSociale));
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioResponseDTO> findByFatturatoTra(BigDecimal min, BigDecimal max) {
        return toResponseList(concessionarioRepository.findByFatturatoBetween(min, max));
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioResponseDTO> findSopraMediaFatturato() {
        return toResponseList(concessionarioRepository.findConcessionariSopraMediaFatturato());
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioResponseDTO> findConAlmenoUnCliente() {
        return toResponseList(concessionarioRepository.findConcessionariConAlmenoUnCliente());
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioNumeroClienti> countClientiPerConcessionario() {
        return concessionarioRepository.countClientiPerConcessionario();
    }

    @Transactional(readOnly = true)
    public List<ConcessionarioResponseDTO> findConPiuClientiDellaMedia() {
        return toResponseList(concessionarioRepository.findConcessionariConPiuClientiDellaMedia());
    }

    private List<ConcessionarioResponseDTO> toResponseList(List<Concessionario> concessionari) {
        return concessionari.stream()
                .map(concessionarioMapper::toResponse)
                .toList();
    }

    private Concessionario trovaOrThrow(Long id) {
        return concessionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Concessionario non trovato: " + id));
    }

    private CasaMadre trovaCasaMadreOrThrow(Long casaMadreId) {
        return casaMadreRepository.findById(casaMadreId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Casa madre non trovata: " + casaMadreId));
    }
}
