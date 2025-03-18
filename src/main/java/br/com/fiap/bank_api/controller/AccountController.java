package br.com.fiap.bank_api.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.bank_api.dto.PixDTO;
import br.com.fiap.bank_api.dto.WithdrawalDepositDTO;
import br.com.fiap.bank_api.model.Account;

@RestController
public class AccountController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Account> repository = new ArrayList<>();

    @PostMapping(path = "/accounts")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        log.info("Cadastrando conta: " + account.getName());
        validate(account);
        repository.add(account);
        return ResponseEntity.status(201).body(account);
    }

    @GetMapping(path = "/accounts")
    public List<Account> getAll() {
        log.info("Buscando todas as contas");
        return repository;
    }

    @GetMapping("/accounts/{number}")
    public ResponseEntity<Account> getByNumber(@PathVariable Long number) {
        log.info("Buscando conta pelo número: " + number);
        return ResponseEntity.ok(getAccount(number));
    }

    @GetMapping("/accounts/{cpf}/cpf")
    public ResponseEntity<Account> getByCpf (@PathVariable Long cpf) {
        log.info("Buscando conta pelo CPF: " + cpf);
        Account account = repository.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada."));
        return ResponseEntity.ok(account);
    }

    @PutMapping("/accounts/{number}/close")
    public ResponseEntity<Account> close(@PathVariable Long number) {
        log.info("Encerrando a conta: " + number);
        Account account = getAccount(number);
        account.setActivity(false);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/accounts/{number}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long number, @RequestBody WithdrawalDepositDTO depositData) {
        log.info("Efetuando depósito");
        Account account = getAccount(number);
        account.setBalance(account.getBalance() + depositData.amount());
        return ResponseEntity.ok(account);
    }

    @PostMapping("/accounts/{number}/withdrawal")
    public ResponseEntity<Account> withdrawal(@PathVariable Long number, @RequestBody WithdrawalDepositDTO withdrawalData) {
        log.info("Efetuando saque");
        Account account = getAccount(number);
        if (account.getBalance() >= withdrawalData.amount()) {
            account.setBalance(account.getBalance() - withdrawalData.amount());
            return ResponseEntity.ok(account);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
    }

    @PostMapping("/accounts/{number}/pix")
    public ResponseEntity<Account> pix(@PathVariable Long number, @RequestBody PixDTO pixData) {
        log.info("Realizando transferência: " + pixData.amount() + "transferidos da conta " + number + " para a conta " + pixData.pixAccountId());
        Account account = getAccount(number);
        if (account.getBalance() >= pixData.amount()) {
            Account pixAccount = getAccount(pixData.pixAccountId());
            account.setBalance(account.getBalance() - pixData.amount());
            pixAccount.setBalance(pixAccount.getBalance() + pixData.amount());
            return ResponseEntity.ok(account);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
    }

    private void validate(Account account) {
        if (account.getName() == null || account.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do titular é obrigatório.");
        }

        if (account.getCpf() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do titular é obrigatório.");
        }

        if (account.getOpeningDate() == null || account.getOpeningDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de abertura não pode ser no futuro.");
        }

        if (account.getBalance() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O saldo inicial não pode ser negativo.");
        }

        if (account.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo da conta deve ser Corrente, Poupanca ou Salario.");
        }
    }

    private Account getAccount(Long number) {
        return repository.stream().filter(c -> c.getNumber().equals(number)).findFirst().orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }
}
