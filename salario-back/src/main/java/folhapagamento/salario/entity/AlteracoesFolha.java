package folhapagamento.salario.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alteracoes_folha")
public class AlteracoesFolha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alteracao")
    private Long idAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servidor", referencedColumnName = "id_servidor")
    private ServidorPublico servidor;

    @Column(name = "id_utilizador")
    private Integer idUtilizador;

    @Column(name = "campo_modificado")
    private String campoModificado;

    @Column(name = "valor_anterior")
    private String valorAnterior;

    @Column(name = "valor_novo")
    private String valorNovo;

    private String motivo;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @Column(name = "ip_origem")
    private String ipOrigem;

    @Column(name = "alerta_activado")
    private Boolean alertaActivado;

    // Getters e setters...

    public Long getIdAlteracao() { return idAlteracao; }
    public void setIdAlteracao(Long idAlteracao) { this.idAlteracao = idAlteracao; }

    public ServidorPublico getServidor() { return servidor; }
    public void setServidor(ServidorPublico servidor) { this.servidor = servidor; }

    public Integer getIdUtilizador() { return idUtilizador; }
    public void setIdUtilizador(Integer idUtilizador) { this.idUtilizador = idUtilizador; }

    public String getCampoModificado() { return campoModificado; }
    public void setCampoModificado(String campoModificado) { this.campoModificado = campoModificado; }

    public String getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(String valorAnterior) { this.valorAnterior = valorAnterior; }

    public String getValorNovo() { return valorNovo; }
    public void setValorNovo(String valorNovo) { this.valorNovo = valorNovo; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(LocalDateTime dataAlteracao) { this.dataAlteracao = dataAlteracao; }

    public String getIpOrigem() { return ipOrigem; }
    public void setIpOrigem(String ipOrigem) { this.ipOrigem = ipOrigem; }

    public Boolean getAlertaActivado() { return alertaActivado; }
    public void setAlertaActivado(Boolean alertaActivado) { this.alertaActivado = alertaActivado; }

}
