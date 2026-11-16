package com.example.concessionaria.concessionario.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.concessionaria.concessionario.entity.Concessionario;
import com.example.concessionaria.concessionario.projection.ConcessionarioNumeroClienti;

public interface ConcessionarioRepository extends JpaRepository<Concessionario, Long> {

    List<Concessionario> findByCasaMadreId(Long casaMadreId);

    boolean existsByCasaMadreId(Long casaMadreId);

    List<Concessionario> findByCitta(String citta);

    List<Concessionario> findByFatturatoGreaterThan(BigDecimal importo);

    List<Concessionario> findByFatturatoBetween(BigDecimal importoMin, BigDecimal importoMax);

    @Query("""
        SELECT c
        FROM Concessionario c
        JOIN c.casaMadre cm
        WHERE cm.ragioneSociale = :ragioneSociale
    """)
    List<Concessionario> findConcessionariByRagioneSocialeCasaMadre(@Param("ragioneSociale") String ragioneSociale);

    @Query("""
        SELECT c
        FROM Concessionario c
        WHERE c.citta = :citta
          AND c.fatturato BETWEEN :importoMin AND :importoMax
    """)
    List<Concessionario> findConcessionariByCittaAndFatturatoTra(
        @Param("citta") String citta,
        @Param("importoMin") BigDecimal importoMin,
        @Param("importoMax") BigDecimal importoMax);

    @Query("""
        SELECT c.id AS concessionarioId,
               c.nome AS nome,
               COUNT(cl) AS numeroClienti
        FROM Concessionario c
        LEFT JOIN c.clienti cl
        GROUP BY c.id, c.nome
    """)
    List<ConcessionarioNumeroClienti> countClientiPerConcessionario();

    @Query("""
        SELECT AVG(c.fatturato)
        FROM Concessionario c
    """)
    Double avgFatturatoConcessionari();

    @Query("""
        SELECT c
        FROM Concessionario c
        WHERE c.fatturato > (
            SELECT AVG(c2.fatturato)
            FROM Concessionario c2
        )
    """)
    List<Concessionario> findConcessionariSopraMediaFatturato();

    @Query("""
        SELECT c
        FROM Concessionario c
        WHERE EXISTS (
            SELECT cl
            FROM Cliente cl
            WHERE cl.concessionario = c
        )
    """)
    List<Concessionario> findConcessionariConAlmenoUnCliente();

    @Query("""
        SELECT c
        FROM Concessionario c
        LEFT JOIN c.clienti cl
        GROUP BY c
        HAVING COUNT(cl) > (
            SELECT COUNT(cl2) * 1.0 / (SELECT COUNT(c2) FROM Concessionario c2)
            FROM Cliente cl2
        )
    """)
    List<Concessionario> findConcessionariConPiuClientiDellaMedia();
}
