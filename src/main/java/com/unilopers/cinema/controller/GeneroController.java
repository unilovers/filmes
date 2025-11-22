package com.unilopers.cinema.controller;

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

    @GetMapping
    public List<Genero> list() {
        return generoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> read(@PathVariable Long id) {
        Optional<Genero> genero = generoRepository.findById(id);
        return genero.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Genero> create(@RequestBody Genero genero) {
        Optional<Genero> existing = generoRepository.findByNome(genero.getNome());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Genero saved = generoRepository.save(genero);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> update(@PathVariable Long id, @RequestBody Genero details) {
        Optional<Genero> opt = generoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Genero genero = opt.get();
        if (details.getNome() != null) {
            genero.setNome(details.getNome());
        }

        Genero saved = generoRepository.save(genero);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Genero> opt = generoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        generoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}