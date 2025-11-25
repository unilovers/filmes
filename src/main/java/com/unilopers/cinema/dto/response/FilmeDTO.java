package com.unilopers.cinema.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.time.LocalDateTime;
import java.util.List;

@JacksonXmlRootElement(localName = "filme")
public class FilmeDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "titulo")
    private String titulo;

    @JacksonXmlProperty(localName = "duracaoMin")
    private Integer duracaoMin;

    @JacksonXmlProperty(localName = "ano")
    private Integer ano;

    @JacksonXmlProperty(localName = "createdAt")
    private LocalDateTime createdAt;

    @JacksonXmlProperty(localName = "updatedAt")
    private LocalDateTime updatedAt;

    @JacksonXmlElementWrapper(localName = "generos")
    @JacksonXmlProperty(localName = "genero")
    private List<GeneroDTO> generos;

    public FilmeDTO() {}

    public FilmeDTO(Long id, String titulo, Integer duracaoMin, Integer ano,
                    LocalDateTime createdAt, LocalDateTime updatedAt, List<GeneroDTO> generos) {
        this.id = id;
        this.titulo = titulo;
        this.duracaoMin = duracaoMin;
        this.ano = ano;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.generos = generos;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracaoMin() {
        return duracaoMin;
    }

    public void setDuracaoMin(Integer duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<GeneroDTO> getGeneros() {
        return generos;
    }

    public void setGeneros(List<GeneroDTO> generos) {
        this.generos = generos;
    }
}