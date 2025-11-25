package com.unilopers.cinema.mapper;

import com.unilopers.cinema.model.Filme;
import com.unilopers.cinema.model.Genero;
import com.unilopers.cinema.dto.request.CreateFilmeDTO;
import com.unilopers.cinema.dto.response.FilmeDTO;
import com.unilopers.cinema.dto.response.FilmeSimplesDTO;
import com.unilopers.cinema.dto.response.GeneroDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmeMapper {

    public FilmeDTO toDTO(Filme filme) {
        if (filme == null) return null;

        List<GeneroDTO> generosDTO = filme.getGeneros().stream()
                .map(this::toGeneroDTO)
                .collect(Collectors.toList());

        return new FilmeDTO(
                filme.getId(),
                filme.getTitulo(),
                filme.getDuracaoMin(),
                filme.getAno(),
                filme.getCreatedAt(),
                filme.getUpdatedAt(),
                generosDTO
        );
    }

    public FilmeSimplesDTO toSimpleDTO(Filme filme) {
        if (filme == null) return null;
        return new FilmeSimplesDTO (
                filme.getId(),
                filme.getTitulo(),
                filme.getDuracaoMin(),
                filme.getAno()
        );
    }

    private GeneroDTO toGeneroDTO(Genero genero) {
        if (genero == null) return null;
        return new GeneroDTO(genero.getId(), genero.getNome());
    }

    public Filme toEntity(CreateFilmeDTO dto) {
        if (dto == null) return null;
        Filme filme = new Filme();
        filme.setTitulo(dto.getTitulo());
        filme.setDuracaoMin(dto.getDuracaoMin());
        filme.setAno(dto.getAno());
        return filme;
    }

    public void updateEntity(Filme filme, CreateFilmeDTO dto) {
        if (dto.getTitulo() != null) filme.setTitulo(dto.getTitulo());
        if (dto.getDuracaoMin() != null) filme.setDuracaoMin(dto.getDuracaoMin());
        if (dto.getAno() != null) filme.setAno(dto.getAno());
    }

    public List<FilmeDTO> toDTOList(List<Filme> filmes) {
        return filmes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}