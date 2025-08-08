package folhapagamento.salario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servidor_publico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorPublico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servidor")
    private Long idServidor;

    private String nomeCompleto;
    private String nuit;
    private String nif;
    private String cargo;
    private String orgao;
    private Double salarioBase;
    private String estado;
    
    
    // Ativo, Inativo, Bloqueado etc.
}
