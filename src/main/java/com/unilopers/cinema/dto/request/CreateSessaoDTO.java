package com.unilopers.cinema.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "sessao")
public class CreateSessaoDTO {

    @JacksonXmlProperty(localName = "idFilme")
    private Long idFilme;

    @JacksonXmlProperty(localName = "idSala")
    private Long idSala;

    @JacksonXmlProperty(localName = "dataHora")
    private LocalDateTime dataHora;

    @JacksonXmlProperty(localName = "precoBase")
    private BigDecimal precoBase;

    @JacksonXmlProperty(localName = "tipoExibicao")
    private String tipoExibicao = "2D"; // Padrão

    public CreateSessaoDTO() {}

    public CreateSessaoDTO(Long idFilme, Long idSala, LocalDateTime dataHora, BigDecimal precoBase, String tipoExibicao) {
        this.idFilme = idFilme;
        this.idSala = idSala;
        this.dataHora = dataHora;
        this.precoBase = precoBase;
        this.tipoExibicao = tipoExibicao;
    }

    // Getters e Setters
    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    public String getTipoExibicao() {
        return tipoExibicao;
    }

    public void setTipoExibicao(String tipoExibicao) {
        this.tipoExibicao = tipoExibicao;
    }
}