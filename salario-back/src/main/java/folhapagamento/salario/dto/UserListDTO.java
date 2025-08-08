package folhapagamento.salario.dto;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

public class UserListDTO {
    public Long id;
    public String username;
    public boolean enabled;
    public String role;

    public UserListDTO(Long id, String username, boolean enabled, String role) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.role = role;
    }
}
