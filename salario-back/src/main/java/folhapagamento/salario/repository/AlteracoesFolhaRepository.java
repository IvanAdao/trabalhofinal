// repository/AlteracoesFolhaRepository.java
package folhapagamento.salario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import folhapagamento.salario.entity.AlteracoesFolha;

public interface AlteracoesFolhaRepository extends JpaRepository<AlteracoesFolha, Long> {

    // Buscar todas as alterações de um servidor específico
    List<AlteracoesFolha> findByServidor_IdServidor(Long idServidor);

    // Exemplo de consulta customizada para trazer alterações com dados do servidor
    @Query("SELECT a FROM AlteracoesFolha a JOIN FETCH a.servidor s")
    List<AlteracoesFolha> findAllWithServidor();
    List<AlteracoesFolha> findByAlertaActivadoTrue();

    // Adicione outros métodos customizados conforme necessário
}