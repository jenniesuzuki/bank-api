# Bank API

## Instruções para testar os endpoints

### 1) Endpoint / (GET)
- localhost:8080/

### 2) Buscar todas as contas
- localhost:8080/accounts

### 3) Criar conta
- localhost:8080/accounts
- Exemplo de formato para o corpo da requisição: 

```json
{
  "number": 12345,
  "agency": 1,
  "name": "Maria Helena da Silva",
  "cpf": 11122233344,
  "openingDate": "2024-03-20",
  "balance": 10000.0,
  "active": true,
  "type": "POUPANCA"
}
```
 
### 4) Buscar conta pelo ID (número da conta)
- localhost:8080/accounts/{number}

### 5) Buscar conta pelo CPF
- localhost:8080/accounts/{cpf}/cpf

### 6) Encerrar uma conta
- localhost:8080/accounts/{number}/close

### 7) Fazer um depósito
- localhost:8080/accounts/{number}/deposit
- Exemplo de formato para o corpo da requisição:

```json
{
  "amount": 100.0
}
```

### 8) Fazer um saque
- localhost:8080/accounts/{number}/withdrawal
- Exemplo de formato para o corpo da requisição:

```json
{
  "amount": 100.0
}
```

### 9) Fazer um PIX
- localhost:8080/accounts/{number}/pix
- Exemplo de formato para o corpo da requisição:

```json
{
  "pixAccountId": 12345,
  "amount": 100.0
}
```
