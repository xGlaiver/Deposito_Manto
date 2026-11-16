package com.example.concessionaria.cliente.controller;

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

import com.example.concessionaria.cliente.command.AggiornaClienteCommand;
import com.example.concessionaria.cliente.command.CreaClienteCommand;
import com.example.concessionaria.cliente.command.EliminaClienteCommand;
import com.example.concessionaria.cliente.command.FindAllClienteCommand;
import com.example.concessionaria.cliente.command.FindByIdClienteCommand;
import com.example.concessionaria.cliente.command.FindClientiByCasaMadreCommand;
import com.example.concessionaria.cliente.command.FindClientiByCittaCommand;
import com.example.concessionaria.cliente.command.FindClientiByConcessionarioCommand;
import com.example.concessionaria.cliente.command.FindClientiByPaeseCommand;
import com.example.concessionaria.cliente.command.SearchClientiCommand;
import com.example.concessionaria.cliente.dto.ClienteRequest;
import com.example.concessionaria.cliente.dto.ClienteResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clienti")
@RequiredArgsConstructor
public class ClienteController {

    private final BeanFactory beanFactory;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crea(@Valid @RequestBody ClienteRequest request) {
        CreaClienteCommand command = beanFactory.getBean(CreaClienteCommand.class);
        command.setRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(command.execute());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        FindAllClienteCommand command = beanFactory.getBean(FindAllClienteCommand.class);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
        FindByIdClienteCommand command = beanFactory.getBean(FindByIdClienteCommand.class);
        command.setId(id);
        return ResponseEntity.ok(command.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> aggiorna(
        @PathVariable Long id,
        @Valid @RequestBody ClienteRequest request) {
        AggiornaClienteCommand command = beanFactory.getBean(AggiornaClienteCommand.class);
        command.setId(id);
        command.setRequest(request);
        return ResponseEntity.ok(command.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        EliminaClienteCommand command = beanFactory.getBean(EliminaClienteCommand.class);
        command.setId(id);
        command.execute();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/concessionario/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findByConcessionario(@PathVariable Long id) {
        FindClientiByConcessionarioCommand command = beanFactory.getBean(FindClientiByConcessionarioCommand.class);
        command.setConcessionarioId(id);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/casa-madre/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findByCasaMadre(@PathVariable Long id) {
        FindClientiByCasaMadreCommand command = beanFactory.getBean(FindClientiByCasaMadreCommand.class);
        command.setCasaMadreId(id);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/citta/{citta}")
    public ResponseEntity<List<ClienteResponseDTO>> findByCitta(@PathVariable String citta) {
        FindClientiByCittaCommand command = beanFactory.getBean(FindClientiByCittaCommand.class);
        command.setCitta(citta);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/paese/{paese}")
    public ResponseEntity<List<ClienteResponseDTO>> findByPaese(@PathVariable String paese) {
        FindClientiByPaeseCommand command = beanFactory.getBean(FindClientiByPaeseCommand.class);
        command.setPaese(paese);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClienteResponseDTO>> search(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String cognome) {
        SearchClientiCommand command = beanFactory.getBean(SearchClientiCommand.class);
        command.setNome(nome);
        command.setCognome(cognome);
        return ResponseEntity.ok(command.execute());
    }
}
