package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.TipoIngresso;
import com.unilopers.cinema.dto.request.CreateTipoIngressoDTO;
import com.unilopers.cinema.dto.response.TipoIngressoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoIngressoMapper {

    public TipoIngressoDTO toDTO(TipoIngresso tipo) {
        if (tipo == null) return null;
        return new TipoIngressoDTO(
                tipo.getId(),
                tipo.getDescricao(),
                tipo.getFatorPreco(),
                tipo.getCategoriaTecnica()
        );
    }

    public TipoIngresso toEntity(CreateTipoIngressoDTO dto) {
        if (dto == null) return null;
        return new TipoIngresso(dto.getDescricao(), dto.getFatorPreco(), dto.getCategoriaTecnica());
    }

    public void updateEntity(TipoIngresso tipo, CreateTipoIngressoDTO dto) {
        if (dto.getDescricao() != null) tipo.setDescricao(dto.getDescricao());
        if (dto.getFatorPreco() != null) tipo.setFatorPreco(dto.getFatorPreco());
        if (dto.getCategoriaTecnica() != null) tipo.setCategoriaTecnica(dto.getCategoriaTecnica());
    }

    public List<TipoIngressoDTO> toDTOList(List<TipoIngresso> tipos) {
        return tipos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}