package com.example.concessionaria.casa_madre.controller;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.concessionaria.casa_madre.command.AggiornaCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.CountClientiPerCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.CountConcessionariPerCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.CreaCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.EliminaCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.FindAllCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.FindByIdCasaMadreCommand;
import com.example.concessionaria.casa_madre.command.FindCaseMadriSenzaConcessionariCommand;
import com.example.concessionaria.casa_madre.command.FindCaseMadriSopraMediaCommand;
import com.example.concessionaria.casa_madre.command.SumFatturatoPerCasaMadreCommand;
import com.example.concessionaria.casa_madre.dto.CasaMadreRequest;
import com.example.concessionaria.casa_madre.dto.CasaMadreResponseDTO;
import com.example.concessionaria.casa_madre.projection.CasaMadreFatturatoConcessionari;
import com.example.concessionaria.casa_madre.projection.CasaMadreNumeroClienti;
import com.example.concessionaria.casa_madre.projection.CasaMadreNumeroConcessionari;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/case-madri")
@RequiredArgsConstructor
public class CasaMadreController {

    private final BeanFactory beanFactory;

    @PostMapping
    public ResponseEntity<CasaMadreResponseDTO> crea(@Valid @RequestBody CasaMadreRequest request) {
        CreaCasaMadreCommand command = beanFactory.getBean(CreaCasaMadreCommand.class);
        command.setRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(command.execute());
    }

    @GetMapping
    public ResponseEntity<List<CasaMadreResponseDTO>> findAll() {
        FindAllCasaMadreCommand command = beanFactory.getBean(FindAllCasaMadreCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasaMadreResponseDTO> findById(@PathVariable Long id) {
        FindByIdCasaMadreCommand command = beanFactory.getBean(FindByIdCasaMadreCommand.class);
        command.setId(id);
        return ResponseEntity.ok(command.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CasaMadreResponseDTO> aggiorna(
        @PathVariable Long id,
        @Valid @RequestBody CasaMadreRequest request) {
        AggiornaCasaMadreCommand command = beanFactory.getBean(AggiornaCasaMadreCommand.class);
        command.setId(id);
        command.setRequest(request);
        return ResponseEntity.ok(command.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        EliminaCasaMadreCommand command = beanFactory.getBean(EliminaCasaMadreCommand.class);
        command.setId(id);
        command.execute();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistiche/concessionari")
    public ResponseEntity<List<CasaMadreNumeroConcessionari>> countConcessionari() {
        CountConcessionariPerCasaMadreCommand command = beanFactory.getBean(CountConcessionariPerCasaMadreCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/statistiche/clienti")
    public ResponseEntity<List<CasaMadreNumeroClienti>> countClienti() {
        CountClientiPerCasaMadreCommand command = beanFactory.getBean(CountClientiPerCasaMadreCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/statistiche/fatturato")
    public ResponseEntity<List<CasaMadreFatturatoConcessionari>> sumFatturato() {
        SumFatturatoPerCasaMadreCommand command = beanFactory.getBean(SumFatturatoPerCasaMadreCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/senza-concessionari")
    public ResponseEntity<List<CasaMadreResponseDTO>> findSenzaConcessionari() {
        FindCaseMadriSenzaConcessionariCommand command = beanFactory.getBean(FindCaseMadriSenzaConcessionariCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/sopra-media")
    public ResponseEntity<List<CasaMadreResponseDTO>> findSopraMedia() {
        FindCaseMadriSopraMediaCommand command = beanFactory.getBean(FindCaseMadriSopraMediaCommand.class);
        return ResponseEntity.ok(command.execute());
    }
}
