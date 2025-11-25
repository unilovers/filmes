package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.CreateFilmeDTO;
import com.unilopers.cinema.dto.response.FilmeDTO;
import com.unilopers.cinema.mapper.FilmeMapper;
import com.unilopers.cinema.model.Filme;
import com.unilopers.cinema.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private FilmeMapper filmeMapper;

    @GetMapping
    public List<FilmeDTO> list() {
        List<Filme> filmes = filmeRepository.findAll();
        return filmeMapper.toDTOList(filmes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> read(@PathVariable Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme
                .map(filmeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmeDTO> create(@RequestBody CreateFilmeDTO dto) {
        Optional<Filme> existing = filmeRepository.findByTitulo(dto.getTitulo());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Converte DTO para Entity
        Filme filme = filmeMapper.toEntity(dto);

        Filme saved = filmeRepository.save(filme);

        // Converte para DTO de resposta
        FilmeDTO responseDTO = filmeMapper.toDTO(saved);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeDTO> update(@PathVariable Long id, @RequestBody CreateFilmeDTO dto) {
        Optional<Filme> opt = filmeRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Filme filme = opt.get();
        filmeMapper.updateEntity(filme, dto);

        Filme saved = filmeRepository.save(filme);
        FilmeDTO responseDTO = filmeMapper.toDTO(saved);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Filme> opt = filmeRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        filmeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}