package com.unilopers.cinema.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.math.BigDecimal;

@JacksonXmlRootElement(localName = "tipoIngresso")
public class CreateTipoIngressoDTO {

    @JacksonXmlProperty(localName = "descricao")
    private String descricao;

    @JacksonXmlProperty(localName = "fatorPreco")
    private BigDecimal fatorPreco;

    @JacksonXmlProperty(localName = "categoriaTecnica")
    private String categoriaTecnica;

    public CreateTipoIngressoDTO() {}

    public CreateTipoIngressoDTO(String descricao, BigDecimal fatorPreco, String categoriaTecnica) {
        this.descricao = descricao;
        this.fatorPreco = fatorPreco;
        this.categoriaTecnica = categoriaTecnica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getFatorPreco() {
        return fatorPreco;
    }

    public void setFatorPreco(BigDecimal fatorPreco) {
        this.fatorPreco = fatorPreco;
    }

    public String getCategoriaTecnica() {
        return categoriaTecnica;
    }

    public void setCategoriaTecnica(String categoriaTecnica) {
        this.categoriaTecnica = categoriaTecnica;
    }
}