package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.CreateTipoIngressoDTO;
import com.unilopers.cinema.dto.response.TipoIngressoDTO;
import com.unilopers.cinema.mapper.TipoIngressoMapper;
import com.unilopers.cinema.model.TipoIngresso;
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
public class TipoIngressoController {

    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    @Autowired
    private TipoIngressoMapper tipoIngressoMapper;

    @GetMapping
    public List<TipoIngressoDTO> list() {
        return tipoIngressoMapper.toDTOList(tipoIngressoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoIngressoDTO> read(@PathVariable Long id) {
        return tipoIngressoRepository.findById(id)
                .map(tipoIngressoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoIngressoDTO> create(@RequestBody CreateTipoIngressoDTO dto) {
        Optional<TipoIngresso> existing = tipoIngressoRepository.findByDescricao(dto.getDescricao());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        TipoIngresso tipo = tipoIngressoMapper.toEntity(dto);
        TipoIngresso saved = tipoIngressoRepository.save(tipo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(tipoIngressoMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoIngressoDTO> update(@PathVariable Long id, @RequestBody CreateTipoIngressoDTO dto) {
        Optional<TipoIngresso> opt = tipoIngressoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TipoIngresso tipo = opt.get();
        tipoIngressoMapper.updateEntity(tipo, dto);
        TipoIngresso saved = tipoIngressoRepository.save(tipo);

        return ResponseEntity.ok(tipoIngressoMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (tipoIngressoRepository.existsById(id)) {
            tipoIngressoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}