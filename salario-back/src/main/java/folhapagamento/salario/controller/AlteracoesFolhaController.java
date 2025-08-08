// controller/AlteracoesFolhaController.java
package folhapagamento.salario.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import folhapagamento.salario.dto.AlteracaoFolhaDTO;
import folhapagamento.salario.entity.AlteracoesFolha;
import folhapagamento.salario.repository.AlteracoesFolhaRepository;

@RestController
@RequestMapping("/api/alteracoes")
@CrossOrigin(origins = "http://localhost:4200")
public class AlteracoesFolhaController {

    @Autowired
    private AlteracoesFolhaRepository alteracoesFolhaRepository;

    @GetMapping
    public List<AlteracaoFolhaDTO> listarTodas() {
        return alteracoesFolhaRepository.findAllWithServidor()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/alertas")
    public List<AlteracaoFolhaDTO> listarAlertas() {
        return alteracoesFolhaRepository.findByAlertaActivadoTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/dashboard-estatisticas")
    public Map<String, Integer> getEstatisticasDashboard() {
        List<AlteracoesFolha> todas = alteracoesFolhaRepository.findAll();

        int fraude = 0, suspeita = 0, semRisco = 0;

        for (AlteracoesFolha a : todas) {
            if (Boolean.TRUE.equals(a.getAlertaActivado())) {
                // FRAUDE: mudou cargo E salário duplicou
                if ("cargo".equals(a.getCampoModificado())) {
                    AlteracoesFolha alteracaoSalario = todas.stream()
                            .filter(b -> b.getServidor().getIdServidor().equals(a.getServidor().getIdServidor())
                                    && b.getDataAlteracao().equals(a.getDataAlteracao())
                                    && "salarioBase".equals(b.getCampoModificado()))
                            .findFirst().orElse(null);
                    if (alteracaoSalario != null) {
                        try {
                            double antigo = Double.parseDouble(alteracaoSalario.getValorAnterior());
                            double novo = Double.parseDouble(alteracaoSalario.getValorNovo());
                            if (novo >= 2 * antigo) {
                                fraude++;
                                continue;
                            }
                        } catch (Exception ignore) {}
                    }
                }
                // SUSPEITA: mudou salarioBase e duplicou
                if ("salarioBase".equals(a.getCampoModificado())) {
                    try {
                        double antigo = Double.parseDouble(a.getValorAnterior());
                        double novo = Double.parseDouble(a.getValorNovo());
                        if (novo >= 2 * antigo) {
                            suspeita++;
                            continue;
                        }
                    } catch (Exception ignore) {}
                }
            } else {
                // SEM RISCO: qualquer alteração sem alerta
                semRisco++;
            }
        }

        Map<String, Integer> estat = new java.util.HashMap<>();
        estat.put("fraude", fraude);
        estat.put("suspeita", suspeita);
        estat.put("semRisco", semRisco);
        estat.put("total", todas.size());
        return estat;
    }

    @GetMapping("/{idAlteracao}")
    public AlteracaoFolhaDTO buscarPorId(@PathVariable Long idAlteracao) {
        AlteracoesFolha entity = alteracoesFolhaRepository.findById(idAlteracao).orElse(null);
        if (entity == null) return null;
        return toDTO(entity);
    }

    @PutMapping("/{idAlteracao}/resolver")
    public void resolverAlteracao(@PathVariable Long idAlteracao) {
        AlteracoesFolha alt = alteracoesFolhaRepository.findById(idAlteracao).orElse(null);
        if (alt != null) {
            alt.setAlertaActivado(false); // Marca como não suspeita
            alteracoesFolhaRepository.save(alt);
        }
    }

        private AlteracaoFolhaDTO toDTO(AlteracoesFolha entity) {
        String nomeServidor = entity.getServidor() != null ? entity.getServidor().getNomeCompleto() : null;
        return new AlteracaoFolhaDTO(
                entity.getIdAlteracao(),
                nomeServidor,
                entity.getCampoModificado(),
                entity.getValorAnterior(),
                entity.getValorNovo(),
                entity.getMotivo(),
                entity.getDataAlteracao(),
                entity.getIpOrigem(),
                entity.getAlertaActivado()
        );
    }
}
