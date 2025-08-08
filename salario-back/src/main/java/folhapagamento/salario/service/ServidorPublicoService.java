package folhapagamento.salario.service;

import folhapagamento.salario.dto.ServidorPublicoDTO;
import folhapagamento.salario.entity.AlteracoesFolha;
import folhapagamento.salario.entity.ServidorPublico;
import folhapagamento.salario.repository.AlteracoesFolhaRepository;
import folhapagamento.salario.repository.ServidorPublicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServidorPublicoService {

    @Autowired
    private ServidorPublicoRepository repository;

    @Autowired
    private AlteracoesFolhaRepository alteracoesFolhaRepository;

    public List<ServidorPublicoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ServidorPublicoDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    // Sobrecarga para Integer (para compatibilidade com controller antigo)
    public Optional<ServidorPublicoDTO> buscarPorId(Integer id) {
        return buscarPorId(id.longValue());
    }

    public ServidorPublicoDTO salvar(ServidorPublicoDTO dto) {
        ServidorPublico entidade = toEntity(dto);
        entidade.setIdServidor(null); // Garante que será criado novo
        entidade = repository.save(entidade);
        return toDTO(entidade);
    }

    public ServidorPublicoDTO atualizarComAuditoria(Long id, ServidorPublicoDTO dto, Integer idUsuario, String ipOrigem) {
        Optional<ServidorPublico> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Servidor não encontrado");
        }
        ServidorPublico entidade = opt.get();
        // Atualize os campos
        if (!Objects.equals(entidade.getNomeCompleto(), dto.getNomeCompleto())) {
            salvarAlteracao(entidade, idUsuario, "nomeCompleto", entidade.getNomeCompleto(), dto.getNomeCompleto(), ipOrigem);
            entidade.setNomeCompleto(dto.getNomeCompleto());
        }
        if (!Objects.equals(entidade.getNuit(), dto.getNuit())) {
            salvarAlteracao(entidade, idUsuario, "nuit", entidade.getNuit(), dto.getNuit(), ipOrigem);
            entidade.setNuit(dto.getNuit());
        }
        if (!Objects.equals(entidade.getNif(), dto.getNif())) {
            salvarAlteracao(entidade, idUsuario, "nif", entidade.getNif(), dto.getNif(), ipOrigem);
            entidade.setNif(dto.getNif());
        }
        if (!Objects.equals(entidade.getCargo(), dto.getCargo())) {
            salvarAlteracao(entidade, idUsuario, "cargo", entidade.getCargo(), dto.getCargo(), ipOrigem);
            entidade.setCargo(dto.getCargo());
        }
        if (!Objects.equals(entidade.getOrgao(), dto.getOrgao())) {
            salvarAlteracao(entidade, idUsuario, "orgao", entidade.getOrgao(), dto.getOrgao(), ipOrigem);
            entidade.setOrgao(dto.getOrgao());
        }
        if (!Objects.equals(entidade.getSalarioBase(), dto.getSalarioBase())) {
            salvarAlteracao(entidade, idUsuario, "salarioBase", entidade.getSalarioBase(), dto.getSalarioBase(), ipOrigem);
            entidade.setSalarioBase(dto.getSalarioBase());
        }
        if (!Objects.equals(entidade.getEstado(), dto.getEstado())) {
            salvarAlteracao(entidade, idUsuario, "estado", entidade.getEstado(), dto.getEstado(), ipOrigem);
            entidade.setEstado(dto.getEstado());
        }
        // Aqui pode chamar lógica de auditoria se necessário
        entidade = repository.save(entidade);
        return toDTO(entidade);
    }

    public void deletar(Integer id) {
        repository.deleteById(id.longValue());
    }

    // Conversão de entidade para DTO
    private ServidorPublicoDTO toDTO(ServidorPublico entidade) {
        return new ServidorPublicoDTO(
                entidade.getIdServidor(),
                entidade.getNomeCompleto(),
                entidade.getNuit(),
                entidade.getNif(),
                entidade.getCargo(),
                entidade.getOrgao(),
                entidade.getSalarioBase(),
                entidade.getEstado()
        );
    }

    // Conversão de DTO para entidade
    private ServidorPublico toEntity(ServidorPublicoDTO dto) {
        ServidorPublico entidade = new ServidorPublico();
        entidade.setIdServidor(dto.getIdServidor());
        entidade.setNomeCompleto(dto.getNomeCompleto());
        entidade.setNuit(dto.getNuit());
        entidade.setNif(dto.getNif());
        entidade.setCargo(dto.getCargo());
        entidade.setOrgao(dto.getOrgao());
        entidade.setSalarioBase(dto.getSalarioBase());
        entidade.setEstado(dto.getEstado());
        return entidade;
    }

    private void salvarAlteracao(ServidorPublico entidade, Integer idUsuario, String campo, Object valorAntigo, Object valorNovo, String ipOrigem) {
        AlteracoesFolha alteracao = new AlteracoesFolha();
        alteracao.setServidor(entidade);
        alteracao.setIdUtilizador(idUsuario);
        alteracao.setCampoModificado(campo);
        alteracao.setValorAnterior(valorAntigo != null ? valorAntigo.toString() : null);
        alteracao.setValorNovo(valorNovo != null ? valorNovo.toString() : null);
        alteracao.setMotivo("Alteração de " + campo);
        alteracao.setDataAlteracao(java.time.LocalDateTime.now());
        alteracao.setIpOrigem(ipOrigem);
        
        // Verificar se a alteração é suspeita
        boolean isSuspeita = false;
        if ("salarioBase".equals(campo)) {
            try {
                double antigo = Double.parseDouble(valorAntigo != null ? valorAntigo.toString() : "0");
                double novo = Double.parseDouble(valorNovo != null ? valorNovo.toString() : "0");
                if (novo >= 2 * antigo) {
                    isSuspeita = true;
                }
            } catch (Exception ignore) {}
        }
        
        alteracao.setAlertaActivado(isSuspeita);
        alteracoesFolhaRepository.save(alteracao);
    }
}

