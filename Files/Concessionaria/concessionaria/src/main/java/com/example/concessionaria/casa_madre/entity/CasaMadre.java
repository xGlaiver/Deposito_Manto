package com.example.concessionaria.casa_madre.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.concessionaria.concessionario.entity.Concessionario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
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
@Table(name = "casa_madre")
public class CasaMadre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "ragione_sociale", nullable = false, length = 100)
    private String ragioneSociale;

    @NotBlank
    @Column(name = "sede_legale", nullable = false, length = 100)
    private String sedeLegale;

    @NotBlank
    @Column(name = "paese", nullable = false, length = 50)
    private String paese;

    @Column(name = "anno_fondazione")
    private Integer annoFondazione;

    @Column(name = "fatturato_annuo", precision = 15, scale = 2)
    private BigDecimal fatturatoAnnuo;

    @OneToMany(mappedBy = "casaMadre", fetch = FetchType.LAZY)
    private List<Concessionario> concessionari = new ArrayList<>();
}
