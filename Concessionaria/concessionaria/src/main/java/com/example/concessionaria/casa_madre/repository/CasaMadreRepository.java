package com.example.concessionaria.casa_madre.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.concessionaria.casa_madre.entity.CasaMadre;
import com.example.concessionaria.casa_madre.projection.CasaMadreFatturatoConcessionari;
import com.example.concessionaria.casa_madre.projection.CasaMadreNumeroClienti;
import com.example.concessionaria.casa_madre.projection.CasaMadreNumeroConcessionari;

public interface CasaMadreRepository extends JpaRepository<CasaMadre, Long>{

    List<CasaMadre> findByRagioneSociale(String ragioneSociale);

    List<CasaMadre> findByPaese(String paese);

    List<CasaMadre> findByAnnoFondazioneLessThan(Integer anno);

    @Query("""
        SELECT cm
        FROM CasaMadre cm
        WHERE cm.paese = :paese
          AND cm.fatturatoAnnuo > :importo
    """)
    List<CasaMadre> findCaseMadriByPaeseAndFatturatoAnnuoMaggioreDi(
        @Param("paese") String paese,
        @Param("importo") BigDecimal importo);

    @Query("""
        SELECT new com.example.concessionaria.casa_madre.projection.CasaMadreNumeroConcessionari(
            cm.id, cm.ragioneSociale, COUNT(c))
        FROM CasaMadre cm
        LEFT JOIN cm.concessionari c
        GROUP BY cm.id, cm.ragioneSociale
    """)
    List<CasaMadreNumeroConcessionari> countConcessionariPerCasaMadre();

    @Query("""
        SELECT new com.example.concessionaria.casa_madre.projection.CasaMadreFatturatoConcessionari(
            cm.id, cm.ragioneSociale, COALESCE(SUM(c.fatturato), 0))
        FROM CasaMadre cm
        LEFT JOIN cm.concessionari c
        GROUP BY cm.id, cm.ragioneSociale
    """)
    List<CasaMadreFatturatoConcessionari> sumFatturatoConcessionariPerCasaMadre();

    @Query("""
        SELECT cm.id AS casaMadreId,
               cm.ragioneSociale AS ragioneSociale,
               COUNT(cl) AS numeroClienti
        FROM CasaMadre cm
        LEFT JOIN cm.concessionari c
        LEFT JOIN c.clienti cl
        GROUP BY cm.id, cm.ragioneSociale
    """)
    List<CasaMadreNumeroClienti> countClientiPerCasaMadre();

    @Query("""
        SELECT cm
        FROM CasaMadre cm
        WHERE cm.fatturatoAnnuo > (
            SELECT AVG(cm2.fatturatoAnnuo)
            FROM CasaMadre cm2
        )
    """)
    List<CasaMadre> findCaseMadriSopraMediaFatturato();

    @Query("""
        SELECT cm
        FROM CasaMadre cm
        WHERE NOT EXISTS (
            SELECT c
            FROM Concessionario c
            WHERE c.casaMadre = cm
        )
    """)
    List<CasaMadre> findCaseMadriSenzaConcessionari();
}
