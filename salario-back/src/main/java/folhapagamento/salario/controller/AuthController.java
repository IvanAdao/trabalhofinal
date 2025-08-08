package folhapagamento.salario.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import folhapagamento.salario.config.JwtTokenProvider;
import folhapagamento.salario.dto.UserListDTO;
import folhapagamento.salario.dto.UserRegisterDTO;
import folhapagamento.salario.entity.Role;
import folhapagamento.salario.entity.User;
import folhapagamento.salario.repository.RoleRepository;
import folhapagamento.salario.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Autowired
    private RoleRepository roleRepository;

    @Autowired  
    private UserRepository utilizadorRepository;

    @PostMapping("/login")
public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
    String username = loginRequest.get("username");
    String password = loginRequest.get("password");

    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = jwtTokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(username).orElseThrow();
        String role = user.getRoles().stream().findFirst().map(Role::getName).orElse("");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login efetuado com sucesso");
        response.put("token", token);
        response.put("username", username);
        response.put("role", role);
        return response;
    } catch (AuthenticationException e) {
        throw new RuntimeException("Usuário ou senha inválidos");
    }
}


      @GetMapping
    public List<UserListDTO> listUsers() {
        return userRepository.findAll().stream()
            .filter(user -> !"admin".equalsIgnoreCase(user.getUsername())) // <-- filtra o admin
            .map(user -> {
                String roleName = user.getRoles().stream()
                    .findFirst()
                    .map(role -> role.getName())
                    .orElse("");
                return new UserListDTO(user.getId(), user.getUsername(), user.isEnabled(), roleName);
            })
            .toList();
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
        if (userRepository.existsByUsername(dto.username)) {
            return ResponseEntity.badRequest().body("Username já existe");
        }
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(passwordEncoder.encode(dto.password));
        user.setEnabled(true);

        // Buscar o perfil pelo nome
        Optional<Role> roleOpt = roleRepository.findByName(dto.role);
        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Perfil não encontrado");
        }
        user.getRoles().add(roleOpt.get());

        userRepository.save(user); // Isso já salva na sec_user_roles também
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    @PatchMapping("/toggle-enabled/{id}")
    public ResponseEntity<?> toggleEnabled(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    
}