package folhapagamento.salario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import folhapagamento.salario.dto.ServidorPublicoDTO;
import folhapagamento.salario.service.ServidorPublicoService;

@RestController
@RequestMapping("/api/servidores")
@CrossOrigin(origins = "http://localhost:4200") 
public class ServidorPublicoController {

    @Autowired
    private ServidorPublicoService service;

    @GetMapping
    public List<ServidorPublicoDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServidorPublicoDTO> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ServidorPublicoDTO salvar(@RequestBody ServidorPublicoDTO dto) {
        return service.salvar(dto);
    }

@PutMapping("{id}")
public ResponseEntity<ServidorPublicoDTO> atualizar(
        @PathVariable Long id,
        @RequestBody ServidorPublicoDTO dto) {

    if (!service.buscarPorId(id).isPresent()) {
        return ResponseEntity.notFound().build();
    }

    dto.setIdServidor(id);
    ServidorPublicoDTO atualizado = service.atualizarComAuditoria(id, dto, null, null); // método sem auditoria
    return ResponseEntity.ok(atualizado);
}



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        if (!service.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}

