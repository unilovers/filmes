package com.unilopers.cinema.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "genero")
public class CreateGeneroDTO {

    @JacksonXmlProperty(localName = "nome")
    private String nome;

    public CreateGeneroDTO() {}

    public CreateGeneroDTO(String nome) {

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}