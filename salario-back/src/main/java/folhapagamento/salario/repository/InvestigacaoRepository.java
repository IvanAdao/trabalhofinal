package folhapagamento.salario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import folhapagamento.salario.entity.Investigacao;

public interface InvestigacaoRepository extends JpaRepository<Investigacao, Long> {
}
