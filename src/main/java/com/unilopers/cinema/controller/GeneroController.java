package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.CreateGeneroDTO;
import com.unilopers.cinema.dto.response.GeneroDTO;
import com.unilopers.cinema.mapper.GeneroMapper;
import com.unilopers.cinema.model.Genero;
import com.unilopers.cinema.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GeneroMapper generoMapper;

    @GetMapping
    public List<GeneroDTO> list() {
        return generoMapper.toDTOList(generoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> read(@PathVariable Long id) {
        return generoRepository.findById(id)
                .map(generoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GeneroDTO> create(@RequestBody CreateGeneroDTO dto) {
        Optional<Genero> existing = generoRepository.findByNome(dto.getNome());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Genero genero = generoMapper.toEntity(dto);
        Genero saved = generoRepository.save(genero);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(generoMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> update(@PathVariable Long id, @RequestBody CreateGeneroDTO dto) {
        Optional<Genero> opt = generoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Genero genero = opt.get();
        generoMapper.updateEntity(genero, dto);
        Genero saved = generoRepository.save(genero);

        return ResponseEntity.ok(generoMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (generoRepository.existsById(id)) {
            generoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}