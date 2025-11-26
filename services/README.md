#  Camada SERVICE (PT-BR)

## 🎯 Função principal

* A camada de serviço é responsável por conter a **Lógica de negócio** (composta por rergras de negócio), as quais definem **como** as operações devem ocorrer na aplicação.
  
* Service serve como um intermediário entre **Controller** e **Repository** <br>
  Controller --> Recebe e responde requisições <br>
  Service    --> Aplica regras de negócio <br>
  Repository --> Interage com o banco de dados <br>

## O que são as regras de negócio?

Regras de negócio são as condições exigidas pela sua aplicação (Dependendo do seu modelo de negócio),
para que sejam aceitas as requisições do usuário antes de interagir com o banco de dados.

| Exemplo de Regras e negócio |
|-----------|
|"Não é permitido criar o cadastro de dois clientes diferentes usando o mesmo email" |
|"CPF devem ser único, não pode repetir" |
|"Nome de usuário (username) não pode estar em uso." |
|"Senha deve ter no mínimo 8 caracteres, incluir letra maiúscula e número." |
|"Data de nascimento deve indicar idade ≥ 18 anos." |
|"Chamado só pode ser criado se o cliente associado existir."|
|"Cliente só pode ser deletado se não possuir chamados abertos."|
| "Usuário só pode ser ativado após verificação de e-mail."|
| "Chamado só pode ser alterado para “Concluído” se o status atual for “Em andamento”|
|"Não permitir agendamento para datas passadas."|
|"Tokens de recuperação de senha expiram após X minutos."|
|"Desconto máximo permitido é de 20% por pedido."|
|"Somente administradores podem alterar o perfil de um usuário."|
|"Toda operação deve registrar log de auditoria."|

# Quando  usar SERVICE? <br>

⚫ Sempre que houver regras de negócio <br>
⚫ Quando é necessário manipular dados antes de salvar/retornar <br>
⚫ Para centralizar a lógica e evitar duplicação <br>

# :open_book: Mapeamento de SERVIÇOS

### 🧰 Anotação de INICIALIZAÇÃO
| Anotação | Descrição |
|----------|-----------|
| `@Service` | Indica ao Spring Boot que a classe faz parte da camada de serviço. |
| `@Autowired` | Faz automaticamente a injeção de dependencias, permitindo que o service use os métodos do repositório sem instanciá-los novamente. |


### 🏦  Anotação de TRANSAÇÃO
| Anotação | Descrição |
|----------|-----------|
| `@Transactional` | Indica que os métodos da classe (ou o método anotado) devem ser executados dentro de uma transação do banco de dados (Isso garante as propriedades ACID). |
| `@Transactional(readOnly = true)` | Variante de @Transactional usada para métodos de apenas leitura. |

⚫ ACID: Atomicidade (tudo ou nada), Consistência, Isolamento e Durabilidade. Ou seja, se alguma operação falhar durante a transação, todas as mudanças serão revertidas (“rollback”). <br>
⚫ Use-a quando você for fazer operações de escrita/alteração no banco (save, update, delete), ou quando o método envolve várias operações que precisam ser atômicas (ex: salvar várias entidades, atualizar relacionamento,      etc.). <br>




# Exemplo de Service
```java

@Service
@Transactional
 public class AlunoService {
   @Autowired
   private AlunoRepository alunoRepository;

   public Aluno salvar(Aluno aluno) {
       //regra de negócio: não permitir nome vazio
     if (aluno.getNome() == null || aluno.getNome().isEmpty {
        throw new IllegalArgumetnException("Nome é obrigatório");
      }
      return alunoRepository.save(aluno);
    }


   public Aluno buscaPorId(Long id) {
     return alunoRepository.findById(id).OrElseThrow(()-> new RunTimeException("Não encontrado"));
   }


   public Aluno deletar(Long id) {
     Aluno aluno = buscarPorId(id);
     alunoRepository.delete(aluno);
   }

```






