package com.unilopers.cinema.repository;

import com.unilopers.cinema.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    Optional<Filme> findByTitulo(String titulo);
}