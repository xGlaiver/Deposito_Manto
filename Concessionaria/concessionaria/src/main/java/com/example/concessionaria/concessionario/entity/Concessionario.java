package com.example.concessionaria.concessionario.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.concessionaria.casa_madre.entity.CasaMadre;
import com.example.concessionaria.cliente.entity.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
@Table(name = "concessionario") 
public class Concessionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Column(name = "citta", nullable = false, length = 100)
    private String citta;

    @Column(name = "indirizzo", length = 150)
    private String indirizzo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fatturato", precision = 15, scale=2)
    private BigDecimal fatturato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casa_madre_id", nullable = false)
    private CasaMadre casaMadre;

    @OneToMany(mappedBy = "concessionario", fetch = FetchType.LAZY)
    private List<Cliente> clienti = new ArrayList<>();
}
