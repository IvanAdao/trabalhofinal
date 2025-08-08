package folhapagamento.salario.dto;

public class InvestigacaoDTO {
    public Long id_investigacao;
    public String nomeServidor;
    public String camposModificados; // Ex: "cargo, salarioBase"
    public String funcionarioResponsavel;
    public String estado;
    public String comentarios;
    public String dataAbertura; // <-- Adicione aqui também
}