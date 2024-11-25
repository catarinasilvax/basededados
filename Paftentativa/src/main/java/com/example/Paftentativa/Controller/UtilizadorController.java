package com.example.Paftentativa.Controller;

import com.example.Paftentativa.dtos.RegistoDto;
import com.example.Paftentativa.dtos.LoginDto;
import com.example.Paftentativa.models.UtilizadorModel;
import com.example.Paftentativa.repositorios.UtilizadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UtilizadorController {

    @Autowired
    private UtilizadorRepositorio utilizadorRepositorio;

    @PostMapping("/registo")
    public ResponseEntity<UtilizadorModel> registo(@RequestBody RegistoDto utilizadorRegistroDto) {
        if (utilizadorRegistroDto.getNome() == null || utilizadorRegistroDto.getEmail() == null || utilizadorRegistroDto.getSenha() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<UtilizadorModel> utilizadorExistente = utilizadorRepositorio.findByEmail(utilizadorRegistroDto.getEmail());

        if (utilizadorExistente.isEmpty()) {
            UtilizadorModel novoUtilizador = new UtilizadorModel();
            novoUtilizador.setNome(utilizadorRegistroDto.getNome());
            novoUtilizador.setEmail(utilizadorRegistroDto.getEmail());
            novoUtilizador.setSenha(utilizadorRegistroDto.getSenha());

            UtilizadorModel utilizadorRegistado = utilizadorRepositorio.save(novoUtilizador);
            return ResponseEntity.ok(utilizadorRegistado);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

        if (loginDto.getEmail() == null || loginDto.getSenha() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email e senha são obrigatórios.");
        }

        Optional<UtilizadorModel> utilizador = utilizadorRepositorio.findByEmail(loginDto.getEmail());

        if (utilizador.isPresent() && utilizador.get().getSenha().equals(loginDto.getSenha())) {
            return ResponseEntity.ok("Login bem-sucedido.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }
}
