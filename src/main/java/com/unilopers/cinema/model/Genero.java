package com.unilopers.cinema.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "generos")
    private Set<Filme> filmes = new HashSet<>();

    public Genero() {}

    public Genero(String nome) {

        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public Set<Filme> getFilmes() {

        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {

        this.filmes = filmes;
    }
}