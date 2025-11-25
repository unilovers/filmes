package com.unilopers.cinema.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "filme")
public class CreateFilmeDTO {

    @JacksonXmlProperty(localName = "titulo")
    private String titulo;

    @JacksonXmlProperty(localName = "duracaoMin")
    private Integer duracaoMin;

    @JacksonXmlProperty(localName = "ano")
    private Integer ano;

    public CreateFilmeDTO() {}

    public CreateFilmeDTO(String titulo, Integer duracaoMin, Integer ano) {
        this.titulo = titulo;
        this.duracaoMin = duracaoMin;
        this.ano = ano;
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getDuracaoMin() { return duracaoMin; }
    public void setDuracaoMin(Integer duracaoMin) { this.duracaoMin = duracaoMin; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
}