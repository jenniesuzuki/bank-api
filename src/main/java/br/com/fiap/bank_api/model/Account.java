package br.com.fiap.bank_api.model;

import java.time.LocalDate;

import br.com.fiap.bank_api.enums.EAccountType;

public class Account {
    private Long number;
    private int agency;
    private String name;
    private Long cpf;
    private LocalDate openingDate;
    private double balance;
    private boolean active;
    private EAccountType type;
    
    public Account(Long number, int agency, String name, Long cpf, LocalDate openingDate, double balance, boolean active,
    EAccountType type) {
        this.number = number;
        this.agency = agency;
        this.name = name;
        this.cpf = cpf;
        this.openingDate = openingDate;
        this.balance = balance;
        this.active = active;
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public int getAgency() {
        return agency;
    }

    public String getName() {
        return name;
    }

    public Long getCpf() {
        return cpf;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public EAccountType getType() {
        return type;
    }

    public void setActivity(boolean active) {
        this.active = active;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
