package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.CreateSalaDTO;
import com.unilopers.cinema.dto.response.SalaDTO;
import com.unilopers.cinema.mapper.SalaMapper;
import com.unilopers.cinema.model.Sala;
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
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaMapper salaMapper;

    @GetMapping
    public List<SalaDTO> list() {
        return salaMapper.toDTOList(salaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> read(@PathVariable Long id) {
        return salaRepository.findById(id)
                .map(salaMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalaDTO> create(@RequestBody CreateSalaDTO dto) {
        Optional<Sala> existing = salaRepository.findByNome(dto.getNome());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Sala sala = salaMapper.toEntity(dto);
        Sala saved = salaRepository.save(sala);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(salaMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> update(@PathVariable Long id, @RequestBody CreateSalaDTO dto) {
        Optional<Sala> opt = salaRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sala sala = opt.get();
        salaMapper.updateEntity(sala, dto);
        Sala saved = salaRepository.save(sala);

        return ResponseEntity.ok(salaMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}