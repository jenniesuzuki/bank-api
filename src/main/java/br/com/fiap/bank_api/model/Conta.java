package br.com.fiap.bank_api.model;

import java.time.LocalDate;

public class Conta {
    private Long numero;
    private int agencia;
    private String nome;
    private Long cpf;
    private LocalDate dataAbertura;
    private double saldo;
    private boolean ativa;
    private Tipo tipo;
    
    public Conta(Long numero, int agencia, String nome, Long cpf, LocalDate dataAbertura, double saldo, boolean ativa,
            Tipo tipo) {
        this.numero = numero;
        this.agencia = agencia;
        this.nome = nome;
        this.cpf = cpf;
        this.dataAbertura = dataAbertura;
        this.saldo = saldo;
        this.ativa = ativa;
        this.tipo = tipo;
    }

    public Long getNumero() {
        return numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public String getNome() {
        return nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setAtividade(boolean ativa) {
        this.ativa = ativa;
    }
}
