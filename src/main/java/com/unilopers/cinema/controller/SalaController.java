package com.unilopers.cinema.controller;

import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salas")
class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping
    public List<Sala> list() {
        return salaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> read(@PathVariable Long id) {
        Optional<Sala> sala = salaRepository.findById(id);
        return sala.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sala> create(@RequestBody Sala sala) {
        Optional<Sala> existing = salaRepository.findByNome(sala.getNome());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Sala saved = salaRepository.save(sala);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> update(@PathVariable Long id, @RequestBody Sala details) {
        Optional<Sala> opt = salaRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sala sala = opt.get();
        if (details.getNome() != null) {
            sala.setNome(details.getNome());
        }
        if (details.getCapacidade() != null) {
            sala.setCapacidade(details.getCapacidade());
        }

        Sala saved = salaRepository.save(sala);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Sala> opt = salaRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        salaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}