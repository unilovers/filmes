package com.unilopers.cinema.repository;

import com.unilopers.cinema.model.Filme;
import com.unilopers.cinema.model.Homologacao;
import com.unilopers.cinema.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomologacaoRepository extends JpaRepository<Homologacao, Long> {
    boolean existsByFilmeAndSalaAndRequisitoTecnicoAndStatusValidacao(
            Filme filme, Sala sala, String requisitoTecnico, String statusValidacao
    );
}