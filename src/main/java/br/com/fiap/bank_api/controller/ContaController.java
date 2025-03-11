package br.com.fiap.bank_api.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.bank_api.model.Conta;

@RestController
public class ContaController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Conta> repository = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        log.info("Endpoint / acessado"); // Log para registrar o acesso ao endpoint
        String nomeProjeto = "Banco Digital API";
        String integrante = "Jennifer Kaori Suzuki";
        return "Nome do Projeto: " + nomeProjeto + "\nIntegrante da Equipe: " + integrante;
    }

    @PostMapping(path = "/contas")
    public ResponseEntity<Conta> create(@RequestBody Conta conta) {
        log.info("Cadastrando conta: " + conta.getNome());
        validate(conta);
        repository.add(conta);
        return ResponseEntity.status(201).body(conta);
    }

    private void validate(Conta conta) {
        if (conta.getNome() == null || conta.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do titular é obrigatório.");
        }

        if (conta.getCpf() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do titular é obrigatório.");
        }

        if (conta.getDataAbertura() == null || conta.getDataAbertura().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de abertura não pode ser no futuro.");
        }

        if (conta.getSaldo() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O saldo inicial não pode ser negativo.");
        }

        if (conta.getTipo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo da conta deve ser Corrente, Poupanca ou Salario.");
        }
    }
}
