package com.unilopers.cinema.controller;

import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.TipoIngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-ingresso")
class TipoIngressoController {

    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    @GetMapping
    public List<TipoIngresso> list() {
        return tipoIngressoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoIngresso> read(@PathVariable Long id) {
        Optional<TipoIngresso> tipo = tipoIngressoRepository.findById(id);
        return tipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoIngresso> create(@RequestBody TipoIngresso tipo) {
        Optional<TipoIngresso> existing = tipoIngressoRepository.findByDescricao(tipo.getDescricao());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        TipoIngresso saved = tipoIngressoRepository.save(tipo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoIngresso> update(@PathVariable Long id, @RequestBody TipoIngresso details) {
        Optional<TipoIngresso> opt = tipoIngressoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TipoIngresso tipo = opt.get();
        if (details.getDescricao() != null) {
            tipo.setDescricao(details.getDescricao());
        }
        if (details.getFatorPreco() != null) {
            tipo.setFatorPreco(details.getFatorPreco());
        }

        TipoIngresso saved = tipoIngressoRepository.save(tipo);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<TipoIngresso> opt = tipoIngressoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tipoIngressoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}