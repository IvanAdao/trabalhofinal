package folhapagamento.salario.service;

import folhapagamento.salario.entity.Investigacao;
import folhapagamento.salario.repository.InvestigacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestigacaoService {
    private final InvestigacaoRepository repo;

    public InvestigacaoService(InvestigacaoRepository repo) {
        this.repo = repo;
    }

    public List<Investigacao> listar() {
        return repo.findAll();
    }

    public Investigacao salvar(Investigacao inv) {
        return repo.save(inv);
    }
    public Investigacao buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
}
