package folhapagamento.salario.controller;

import folhapagamento.salario.entity.Investigacao;
import folhapagamento.salario.service.InvestigacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/investigacao")
@CrossOrigin(origins = "*")
public class InvestigacaoController {
    private final InvestigacaoService service;

    public InvestigacaoController(InvestigacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Investigacao> listar() { return service.listar(); }

    @Autowired
    private folhapagamento.salario.repository.AlteracoesFolhaRepository alteracoesFolhaRepository;

    @PostMapping
    public Investigacao criar(@RequestBody Investigacao inv) {
        Investigacao salvo = service.salvar(inv);

        // Se investigação foi concluída, atualiza o status da alteração correspondente
        if ((inv.getEstado() == Investigacao.Estado.FECHADO || inv.getEstado() == Investigacao.Estado.ARQUIVADO)
            && inv.getIdAlteracao() != null) {
            alteracoesFolhaRepository.findById(inv.getIdAlteracao()).ifPresent(alt -> {
                alt.setAlertaActivado(false);
                alteracoesFolhaRepository.save(alt);
            });
        }

        return salvo;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) { service.remover(id); }

    // NOVO: Estatísticas por estado
    @GetMapping("/estatisticas")
    public Map<String, Integer> estatisticas() {
        List<Investigacao> lista = service.listar();
        int emAnalise = 0, fechado = 0, arquivado = 0;
        for (Investigacao inv : lista) {
            if (inv.getEstado() == Investigacao.Estado.EM_ANALISE) emAnalise++;
            else if (inv.getEstado() == Investigacao.Estado.FECHADO) fechado++;
            else if (inv.getEstado() == Investigacao.Estado.ARQUIVADO) arquivado++;
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("emAnalise", emAnalise); // Suspeitas
        map.put("fechado", fechado);
        map.put("arquivado", arquivado);
        map.put("total", lista.size());
        return map;
    }

    // NOVO: Alterar estado da investigação
    @PutMapping("/{id}/estado")
    public Investigacao alterarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Investigacao inv = service.buscarPorId(id);
        if (inv != null && body.containsKey("estado")) {
            inv.setEstado(Investigacao.Estado.valueOf(body.get("estado")));
            Investigacao salvo = service.salvar(inv);

            // Se investigação foi concluída, remove alerta na alteração correspondente
            if ((inv.getEstado() == Investigacao.Estado.FECHADO || inv.getEstado() == Investigacao.Estado.ARQUIVADO)
                && inv.getIdAlteracao() != null) {
                alteracoesFolhaRepository.findById(inv.getIdAlteracao()).ifPresent(alt -> {
                    alt.setAlertaActivado(false);
                    alteracoesFolhaRepository.save(alt);
                });
            }

            return salvo;
        }
        return inv;
    }
}
