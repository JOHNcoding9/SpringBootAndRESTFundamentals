#  Camada DTO (PT-BR)
Data Transfer Object

## 🎯 Função principal

A camada **DTO** é um objeto simples, geralmente composto apenas por atributos e métodos de acesso, sendo  utilizados para **transportar dados entre camadas**. O transporte é feito **sem utilizar a lógica de negócio**
e serve como **uma interface de comunicação segura e enxuta**.

São separados em: <br>
⚫ RequestDTO <br>
⚫ ResponseDTO  <br>

Em resumo, DTOs basicamente filtram as Entidades antes de transportá-las às outras camadas da aplicação.

**Por que usar DTOs?** <br>
📥 Encapsulamento de dados: Evita expôr diretamnte entidades do banco de dados. <br>
🔑 Segurança: permite controlar quais dados serão enviados e recebidos. <br>
🎭 Performance: reduz o volume de dados trafegados (Apenas o necessário).  <br>


## Características dos DTOs
❌ Não contém regras de negócio, apenas os dados de entidades. <br>
✔️ Realiza validações dos dados. <br>


# ⚙️ Etapas de validação de uma aplicação

## 1º Validação Estrutural ( Realizada na camada de DTO)
🎯 Objetivos: <br>
⚫ Garantir que dados preenchidos têm o formato esperado. <br>
⚫ Realizar a validação dos dados (Email existe? CPF existe? ...) <br>
⚫ Conferir campos obrigatórios. <br>
⚫ Tamanho máximo e mínimo dos dados. <br>
ferramentas: Bean Validation,

```java

public class UsuarioDTO {
 @NotNull (message = "Nome é obrigatório")
 @Size (min = 3, max = 50)
 private String nome;

 @Email(message = "E-mail inválido")
 private string email;
}
```
Essas validações são automáticas com **@Valid** nos Controllers.

## 2º Validação Semântica ( Realizada na camada de Serviço)
🎯 Objetivos: <br>
⚫ Validar regras de negócio que dependem do contexto da aplicação. <br>
("Email já cadastrado", "Data de nascimento não pode ser 2 anos anteriores ao atual", "Nome de usuário já está em uso" ....)

```java
if (usuarioRepository.existsByEmail(dto.getEmail()) {
 throw new BusinessException("E-mail já cadastrado") ;
}
```

## 3º Validação de Persistência ( Realizada no Banco de Dados)
🎯 Objetivos: <br>
⚫ Garantir integridade referencial e unicidade no banco real da aplicação (Postgre, MySQL ...) <br>
⚫ Uso  de constraints do banco real: UNIQUE, FOREIGN KEY, PRIMARY KEY ...

```SQL
CREATE TABLE USUARIO (
id_usuario SERIAL NOT NULL,
nome VARCHAR(100) NOT NULL,
email_hash VARCHAR(320) UNIQUE NOT NULL,
senha_hash VARCHAR(100) NOT NULL,
data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
PRIMARY KEY (id_usuario)
);
```



