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
@RequestMapping("/ingressos")
public class IngressoController {

    @Autowired
    private IngressoRepository ingressoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;
    @Autowired
    private SessaoAssentoRepository sessaoAssentoRepository;

    @GetMapping
    public List<Ingresso> list() { return ingressoRepository.findAll(); }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        try {
            Long idUsuario = Long.valueOf(request.get("idUsuario").toString());
            Long idSessao = Long.valueOf(request.get("idSessao").toString());
            Long idTipoIngresso = Long.valueOf(request.get("idTipoIngresso").toString());
            Integer numeroAssento = Integer.valueOf(request.get("numeroAssento").toString());

            Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
            Optional<Sessao> sessao = sessaoRepository.findById(idSessao);
            Optional<TipoIngresso> tipoIngresso = tipoIngressoRepository.findById(idTipoIngresso);

            if (usuario.isEmpty() || sessao.isEmpty() || tipoIngresso.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Dados inválidos"));
            }

            // === VALIDAÇÃO DE VENDA (Compatibilidade) ===
            String categoriaSessao = sessao.get().getTipoExibicao(); // ex: 3D
            String categoriaIngresso = tipoIngresso.get().getCategoriaTecnica(); // ex: 2D

            if (!categoriaSessao.equalsIgnoreCase(categoriaIngresso)) {
                return ResponseEntity.badRequest().body(Map.of(
                        "erro", "Tipo de ingresso (" + categoriaIngresso + ") incompatível com a sessão (" + categoriaSessao + ")"
                ));
            }

            // Verificar disponibilidade do assento
            Optional<SessaoAssento> assento = sessaoAssentoRepository.findBySessaoAndIdAssento(sessao.get(), numeroAssento);
            if (assento.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Assento não existe"));
            }
            if (assento.get().getReservado()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Assento já ocupado"));
            }

            // Reserva e Salva
            assento.get().setReservado(true);
            sessaoAssentoRepository.save(assento.get());

            BigDecimal valorFinal = sessao.get().getPrecoBase().multiply(tipoIngresso.get().getFatorPreco());

            Ingresso ingresso = new Ingresso(usuario.get(), sessao.get(), tipoIngresso.get(), numeroAssento, valorFinal);
            Ingresso saved = ingressoRepository.save(ingresso);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}