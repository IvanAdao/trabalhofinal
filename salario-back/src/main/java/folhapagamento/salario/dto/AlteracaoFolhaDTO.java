// dto/AlteracaoFolhaDTO.java
package folhapagamento.salario.dto;

import java.time.LocalDateTime;

public class AlteracaoFolhaDTO {
    public Long idAlteracao;
    public String nomeServidor;
    public String campoModificado;
    public String valorAnterior;
    public String valorNovo;
    public String motivo;
    public LocalDateTime dataAlteracao;
    public String ipOrigem;
    public Boolean alertaActivado;

    public AlteracaoFolhaDTO(Long idAlteracao, String nomeServidor, String campoModificado, String valorAnterior,
                             String valorNovo, String motivo, LocalDateTime dataAlteracao,
                             String ipOrigem, Boolean alertaActivado) {
        this.idAlteracao = idAlteracao;
        this.nomeServidor = nomeServidor;
        this.campoModificado = campoModificado;
        this.valorAnterior = valorAnterior;
        this.valorNovo = valorNovo;
        this.motivo = motivo;
        this.dataAlteracao = dataAlteracao;
        this.ipOrigem = ipOrigem;
        this.alertaActivado = alertaActivado;
    }
}
