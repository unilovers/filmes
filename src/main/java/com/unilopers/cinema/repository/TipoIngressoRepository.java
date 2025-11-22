package com.unilopers.cinema.repository;

import com.unilopers.cinema.model.TipoIngresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoIngressoRepository extends JpaRepository<TipoIngresso, Long> {
    Optional<TipoIngresso> findByDescricao(String descricao);
}