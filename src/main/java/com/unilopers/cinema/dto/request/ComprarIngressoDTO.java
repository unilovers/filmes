package com.unilopers.cinema.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "compraIngresso")
public class ComprarIngressoDTO {

    @JacksonXmlProperty(localName = "idUsuario")
    private Long idUsuario;

    @JacksonXmlProperty(localName = "idSessao")
    private Long idSessao;

    @JacksonXmlProperty(localName = "idTipoIngresso")
    private Long idTipoIngresso;

    @JacksonXmlProperty(localName = "numeroAssento")
    private Integer numeroAssento;

    public ComprarIngressoDTO() {}

    public ComprarIngressoDTO(Long idUsuario, Long idSessao, Long idTipoIngresso, Integer numeroAssento) {
        this.idUsuario = idUsuario;
        this.idSessao = idSessao;
        this.idTipoIngresso = idTipoIngresso;
        this.numeroAssento = numeroAssento;
    }

    // Getters e Setters
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdSessao() {
        return idSessao;
    }
    public void setIdSessao(Long idSessao) {
        this.idSessao = idSessao;
    }
    public Long getIdTipoIngresso() {
        return idTipoIngresso;
    }
    public void setIdTipoIngresso(Long idTipoIngresso) {
        this.idTipoIngresso = idTipoIngresso;
    }
    public Integer getNumeroAssento() {
        return numeroAssento;
    }
    public void setNumeroAssento(Integer numeroAssento) {
        this.numeroAssento = numeroAssento;
    }
}