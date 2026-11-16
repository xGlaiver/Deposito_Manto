package com.example.concessionaria.concessionario.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.concessionaria.concessionario.command.AggiornaConcessionarioCommand;
import com.example.concessionaria.concessionario.command.CountClientiPerConcessionarioCommand;
import com.example.concessionaria.concessionario.command.CreaConcessionarioCommand;
import com.example.concessionaria.concessionario.command.EliminaConcessionarioCommand;
import com.example.concessionaria.concessionario.command.FindAllConcessionarioCommand;
import com.example.concessionaria.concessionario.command.FindByIdConcessionarioCommand;
import com.example.concessionaria.concessionario.command.FindConcessionariByCasaMadreCommand;
import com.example.concessionaria.concessionario.command.FindConcessionariByFatturatoCommand;
import com.example.concessionaria.concessionario.command.FindConcessionariConClientiCommand;
import com.example.concessionaria.concessionario.command.FindConcessionariSopraMediaClientiCommand;
import com.example.concessionaria.concessionario.command.FindConcessionariSopraMediaCommand;
import com.example.concessionaria.concessionario.dto.ConcessionarioRequest;
import com.example.concessionaria.concessionario.dto.ConcessionarioResponseDTO;
import com.example.concessionaria.concessionario.projection.ConcessionarioNumeroClienti;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/concessionari")
@RequiredArgsConstructor
public class ConcessionarioController {

    private final BeanFactory beanFactory;

    @PostMapping
    public ResponseEntity<ConcessionarioResponseDTO> crea(@Valid @RequestBody ConcessionarioRequest request) {
        CreaConcessionarioCommand command = beanFactory.getBean(CreaConcessionarioCommand.class);
        command.setRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(command.execute());
    }

    @GetMapping
    public ResponseEntity<List<ConcessionarioResponseDTO>> findAll() {
        FindAllConcessionarioCommand command = beanFactory.getBean(FindAllConcessionarioCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcessionarioResponseDTO> findById(@PathVariable Long id) {
        FindByIdConcessionarioCommand command = beanFactory.getBean(FindByIdConcessionarioCommand.class);
        command.setId(id);
        return ResponseEntity.ok(command.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConcessionarioResponseDTO> aggiorna(
        @PathVariable Long id,
        @Valid @RequestBody ConcessionarioRequest request) {
        AggiornaConcessionarioCommand command = beanFactory.getBean(AggiornaConcessionarioCommand.class);
        command.setId(id);
        command.setRequest(request);
        return ResponseEntity.ok(command.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        EliminaConcessionarioCommand command = beanFactory.getBean(EliminaConcessionarioCommand.class);
        command.setId(id);
        command.execute();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/casa-madre/{ragioneSociale}")
    public ResponseEntity<List<ConcessionarioResponseDTO>> findByCasaMadre(@PathVariable String ragioneSociale) {
        FindConcessionariByCasaMadreCommand command = beanFactory.getBean(FindConcessionariByCasaMadreCommand.class);
        command.setRagioneSociale(ragioneSociale);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/fatturato")
    public ResponseEntity<List<ConcessionarioResponseDTO>> findByFatturato(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max) {
        FindConcessionariByFatturatoCommand command = beanFactory.getBean(FindConcessionariByFatturatoCommand.class);
        command.setMin(min);
        command.setMax(max);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/sopra-media")
    public ResponseEntity<List<ConcessionarioResponseDTO>> findSopraMedia() {
        FindConcessionariSopraMediaCommand command = beanFactory.getBean(FindConcessionariSopraMediaCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/con-clienti")
    public ResponseEntity<List<ConcessionarioResponseDTO>> findConClienti() {
        FindConcessionariConClientiCommand command = beanFactory.getBean(FindConcessionariConClientiCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/statistiche/clienti")
    public ResponseEntity<List<ConcessionarioNumeroClienti>> countClienti() {
        CountClientiPerConcessionarioCommand command = beanFactory.getBean(CountClientiPerConcessionarioCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/statistiche/sopra-media-clienti")
    public ResponseEntity<List<ConcessionarioResponseDTO>> findSopraMediaClienti() {
        FindConcessionariSopraMediaClientiCommand command = beanFactory.getBean(FindConcessionariSopraMediaClientiCommand.class);
        return ResponseEntity.ok(command.execute());
    }
}
