package com.example.concessionaria.cliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.concessionaria.cliente.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByConcessionarioId(Long concessionarioId);

    boolean existsByConcessionarioId(Long concessionarioId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<Cliente> findByNome(String nome);

    List<Cliente> findByCognome(String cognome);

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByNomeAndCognome(String nome, String cognome);

    @Query("""
        SELECT cl
        FROM Cliente cl
        JOIN cl.concessionario c
        WHERE c.id = :concessionarioId
    """)
    List<Cliente> findClientiByConcessionarioId(@Param("concessionarioId") Long concessionarioId);

    @Query("""
        SELECT cl
        FROM Cliente cl
        JOIN cl.concessionario c
        JOIN c.casaMadre cm
        WHERE cm.id = :casaMadreId
    """)
    List<Cliente> findClientiByCasaMadreId(@Param("casaMadreId") Long casaMadreId);

    @Query("""
        SELECT cl
        FROM Cliente cl
        JOIN cl.concessionario c
        WHERE c.citta = :citta
    """)
    List<Cliente> findClientiByCittaConcessionario(@Param("citta") String citta);

    @Query("""
        SELECT cl
        FROM Cliente cl
        JOIN cl.concessionario c
        JOIN c.casaMadre cm
        WHERE cm.paese = :paese
    """)
    List<Cliente> findClientiByPaeseCasaMadre(@Param("paese") String paese);

    @Query("""
        SELECT cl
        FROM Cliente cl
        WHERE (:nome IS NULL OR cl.nome = :nome)
          AND (:cognome IS NULL OR cl.cognome = :cognome)
    """)
    List<Cliente> searchClienti(@Param("nome") String nome, @Param("cognome") String cognome);
}
