package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UtenteDto;
import com.example.demo.model.Utente;
import com.example.demo.service.UtenteService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping ("/utenti")
public class UtenteController {
    
    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/by-nome/{nome}")
    public String getUtentiByNome(@PathVariable  String nome) {
        return "Utente trovato " + utenteService.getUtenteByNome(nome);
    }

    @PostMapping("/creaUtente")
    public void postMethodName(@RequestBody UtenteDto utenteDto) {

        utenteService.creaUtente(utenteDto);
    }

    @GetMapping("/mario")
    public List<Utente> getMethodName() {
        return utenteService.getUtenteMario("mario");
    }
    
    
    
}
