package folhapagamento.salario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import folhapagamento.salario.entity.ServidorPublico;

public interface ServidorPublicoRepository extends JpaRepository<ServidorPublico, Long> {
    boolean existsByNuit(String nuit);
}
