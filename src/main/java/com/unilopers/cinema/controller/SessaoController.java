package com.unilopers.cinema.controller;

import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes")

public class SessaoController {

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private FilmeRepository filmeRepository;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private SessaoAssentoRepository sessaoAssentoRepository;

    // INJEÇÃO NOVA
    @Autowired
    private HomologacaoRepository homologacaoRepository;

    @GetMapping
    public List<Sessao> list() { return sessaoRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> read(@PathVariable Long id) {
        Optional<Sessao> sessao = sessaoRepository.findById(id);
        return sessao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        try {
            Long idFilme = Long.valueOf(request.get("idFilme").toString());
            Long idSala = Long.valueOf(request.get("idSala").toString());
            String dataHora = request.get("dataHora").toString();
            BigDecimal precoBase = new BigDecimal(request.get("precoBase").toString());
            // Padrão 2D se não vier nada
            String tipoExibicao = request.getOrDefault("tipoExibicao", "2D").toString();

            Optional<Filme> filme = filmeRepository.findById(idFilme);
            Optional<Sala> sala = salaRepository.findById(idSala);

            if (filme.isEmpty() || sala.isEmpty()) {
                return ResponseEntity.badRequest().body("Filme ou Sala inválidos");
            }

            // === VALIDAÇÃO DE HARDWARE (Homologação) ===
            boolean isHomologado = homologacaoRepository.existsByFilmeAndSalaAndRequisitoTecnicoAndStatusValidacao(
                    filme.get(), sala.get(), tipoExibicao, "Aprovado"
            );

            if (!isHomologado) {
                return ResponseEntity.badRequest().body(
                        "ERRO: Sala não homologada (aprovada tecnicamente) para exibir este filme em " + tipoExibicao
                );
            }

            Sessao sessao = new Sessao();
            sessao.setFilme(filme.get());
            sessao.setSala(sala.get());
            sessao.setDataHora(java.time.LocalDateTime.parse(dataHora));
            sessao.setPrecoBase(precoBase);
            sessao.setTipoExibicao(tipoExibicao); // Salva o tipo

            Sessao saved = sessaoRepository.save(sessao);

            // Gera assentos (Estoque)
            for (int i = 1; i <= sala.get().getCapacidade(); i++) {
                SessaoAssento assento = new SessaoAssento(saved, i, false);
                sessaoAssentoRepository.save(assento);
            }

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    // Métodos PUT e DELETE seguem padrão...
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(sessaoRepository.existsById(id)) {
            sessaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
