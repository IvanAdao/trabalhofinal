package folhapagamento.salario.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "investigacao")
public class Investigacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_investigacao;

    private Integer id_servidor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data_abertura;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String comentarios;

    private Long idAlteracao;

    public Long getId_investigacao() {
        return id_investigacao;
    }

    public void setId_investigacao(Long id_investigacao) {
        this.id_investigacao = id_investigacao;
    }

    public Integer getId_servidor() {
        return id_servidor;
    }

    public void setId_servidor(Integer id_servidor) {
        this.id_servidor = id_servidor;
    }

    public Date getData_abertura() {
        return data_abertura;
    }

    public void setData_abertura(Date data_abertura) {
        this.data_abertura = data_abertura;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Long getIdAlteracao() {
        return idAlteracao;
    }

    public void setIdAlteracao(Long idAlteracao) {
        this.idAlteracao = idAlteracao;
    }

    public enum Estado { EM_ANALISE, FECHADO, ARQUIVADO }
    

    // Getters e setters
    // ...
}
