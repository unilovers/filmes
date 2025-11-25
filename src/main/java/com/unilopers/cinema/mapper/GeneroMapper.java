package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.Genero;
import com.unilopers.cinema.dto.request.CreateGeneroDTO;
import com.unilopers.cinema.dto.response.GeneroDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeneroMapper {

    public GeneroDTO toDTO(Genero genero) {
        if (genero == null) return null;
        return new GeneroDTO(genero.getId(), genero.getNome());
    }

    public Genero toEntity(CreateGeneroDTO dto) {
        if (dto == null) return null;
        return new Genero(dto.getNome());
    }

    public void updateEntity(Genero genero, CreateGeneroDTO dto) {
        if (dto.getNome() != null) genero.setNome(dto.getNome());
    }

    public List<GeneroDTO> toDTOList(List<Genero> generos) {
        return generos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}