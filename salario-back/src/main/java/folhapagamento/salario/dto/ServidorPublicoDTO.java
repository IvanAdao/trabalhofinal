package folhapagamento.salario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorPublicoDTO {
    private Long idServidor; // <-- troque para Long
    private String nomeCompleto;
    private String nuit;
    private String nif;
    private String cargo;
    private String orgao;
    private Double salarioBase;
    private String estado;
}
