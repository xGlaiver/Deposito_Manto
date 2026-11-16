package com.example.concessionaria.casa_madre.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.concessionaria.casa_madre.dto.CasaMadreRequest;
import com.example.concessionaria.casa_madre.dto.CasaMadreResponseDTO;
import com.example.concessionaria.casa_madre.entity.CasaMadre;
import com.example.concessionaria.casa_madre.mapping.CasaMadreMapper;
import com.example.concessionaria.casa_madre.projection.CasaMadreFatturatoConcessionari;
import com.example.concessionaria.casa_madre.projection.CasaMadreNumeroClienti;
import com.example.concessionaria.casa_madre.projection.CasaMadreNumeroConcessionari;
import com.example.concessionaria.casa_madre.repository.CasaMadreRepository;
import com.example.concessionaria.common.exception.ConflictException;
import com.example.concessionaria.common.exception.ResourceNotFoundException;
import com.example.concessionaria.concessionario.repository.ConcessionarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CasaMadreService {

    private final CasaMadreRepository casaMadreRepository;
    private final ConcessionarioRepository concessionarioRepository;
    private final CasaMadreMapper casaMadreMapper;

    @Transactional
    public CasaMadreResponseDTO crea(CasaMadreRequest request) {
        CasaMadre casaMadre = casaMadreMapper.toEntity(request);
        return casaMadreMapper.toResponse(casaMadreRepository.save(casaMadre));
    }

    @Transactional(readOnly = true)
    public List<CasaMadreResponseDTO> findAll() {
        return casaMadreRepository.findAll().stream()
                .map(casaMadreMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CasaMadreResponseDTO findById(Long id) {
        return casaMadreMapper.toResponse(trovaOrThrow(id));
    }

    @Transactional
    public CasaMadreResponseDTO aggiorna(Long id, CasaMadreRequest request) {
        CasaMadre esistente = trovaOrThrow(id);
        CasaMadre casaMadre = casaMadreMapper.toEntity(request);
        casaMadre.setId(id);
        casaMadre.setConcessionari(esistente.getConcessionari());
        return casaMadreMapper.toResponse(casaMadreRepository.save(casaMadre));
    }

    @Transactional
    public void elimina(Long id) {
        CasaMadre casaMadre = trovaOrThrow(id);
        if (concessionarioRepository.existsByCasaMadreId(id)) {
            throw new ConflictException(
                    "Impossibile eliminare la casa madre " + id + ": ha ancora dei concessionari associati");
        }
        casaMadreRepository.delete(casaMadre);
    }

    @Transactional(readOnly = true)
    public List<CasaMadreNumeroConcessionari> countConcessionariPerCasaMadre() {
        return casaMadreRepository.countConcessionariPerCasaMadre();
    }

    @Transactional(readOnly = true)
    public List<CasaMadreNumeroClienti> countClientiPerCasaMadre() {
        return casaMadreRepository.countClientiPerCasaMadre();
    }

    @Transactional(readOnly = true)
    public List<CasaMadreFatturatoConcessionari> sumFatturatoPerCasaMadre() {
        return casaMadreRepository.sumFatturatoConcessionariPerCasaMadre();
    }

    @Transactional(readOnly = true)
    public List<CasaMadreResponseDTO> findSenzaConcessionari() {
        return toResponseList(casaMadreRepository.findCaseMadriSenzaConcessionari());
    }

    @Transactional(readOnly = true)
    public List<CasaMadreResponseDTO> findSopraMediaFatturato() {
        return toResponseList(casaMadreRepository.findCaseMadriSopraMediaFatturato());
    }

    private List<CasaMadreResponseDTO> toResponseList(List<CasaMadre> caseMadri) {
        return caseMadri.stream()
                .map(casaMadreMapper::toResponse)
                .toList();
    }

    private CasaMadre trovaOrThrow(Long id) {
        return casaMadreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Casa madre non trovata: " + id));
    }
}
