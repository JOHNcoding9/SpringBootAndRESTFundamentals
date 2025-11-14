#  Camada Repository (PT-BR)

## 🎯 Função principal

A camada **Repository** abstrai o acesso de dados. Ele atua como uma **ponte/túnel entre a aplicação e o banco de dados**, permitindo
manipular entidades sem se preocupar com os detalhes de persistência.
Ou seja, A camada oculta todo o comportamento necessário para que uma entidade seja salva, consultada, atualizada e removida, **sem que a camada de serviço precise conhecer os mecanismos internos do banco**.

#  Papel do repository na arquitetura:

⚫ isola a lógica de acesso aos dados <br>
⚫ facilita testes de manutenção <br>
⚫ promove reutilização de código <br>
⚫ permite trocar a fonte de dados sem alterar a aplicação <br>
---
# criando repository:
```java
@Repository
public interface AlunoRepository extends JpaRepository <Aluno,Long> {
 List<Aluno>findByNome(string nome);
}
```

O que acontece no código acima? <br>
🔵 JpaRepository fornece seus métodos automáticos à interface Aluno. <br>
🔵 O método findByNome() segue o padrão findBy{atributo}(). <br>
🔵 O Spring gera a implementação em tempo de execução, sem que você precise escrever o código SQL da consulta. <br>

# :bulb: Lógica de construção de métodos personalizados -- <br>
Spring Data Jpa permite criar métodos **baseados no nome**, sem precisar escrever a query. <br>
Ele **interpreta o nome dado ao método e gera uma consulta JPQL automaticamente por baixo dos panos.** <br>
(findByNome() , findByEmailAndStatus() ...)

O Spring Data cria métodos automaticamente, desde que você siga o padrão de nome:  <br>
⚙️ Save() <br>
⚙️ findBy{atributo}() <br>
⚙️ findAll() <br>
⚙️ delete() <br>
⚙️ count() <br>
⚙️ existsBy{atributo}() <br>

Qualquer combinação válida com find, exists, count, delete, remove, get + By + campos da entidade será gerada, com suporte a:
| Operador lógico |
|-----------|
|And|
|Or|
|Between|
|Like|
|Containing|
|StartsWith|
|EndsWith|
|LessThan|
|GreaterThan|
|IsNull|
|IsNotNull|
|True|
|False|
|OrderBy|


## Consultas personalizadas

Além das consultas por convenção, é possível definir consultas personalizadas com a anotação **@Query**.
Exemplo em JPQL (usa entidades e atributos, não tabelas; independente do banco; JPA traduz para SQL interno.):
```jpql
@Repository
public interface AlunoRepository extends JpaRepository<Aluno,Long> {
 @Query("SELECT a FROM Aluno a WHERE a.nome =: nome AND a.idade >=: idade")
 List<Aluno>buscaAlunoPorNomeIdade(@Param("nome")String nome, @Param("idade")String idade)
}
```

Também é possível usar **SQL nativo** (nativeQuery = True). (usa tabela real, coluna real; Dependente do banco; Não passa por tradução.)
```jpql
@Query(value = "SELECT * FROM aluno WHERE nome = :nome", nativeQuery = true)
List<Aluno> buscarPorNome(@Param("nome") String nome);
```

# :open_book: Mapeamento de Repository

### 🔗 Anotações
| Anotação | Descrição |
|----------|-----------|
| `@Repository` | Marca o componente de persistência. |
| `@Query` | Define uma consulta JPQL ou SQL nativo diretamente no método do repository.|
| `@Param` | Especifica os parâmetros usados na @Query. |
| `@Modifying` | Obrigatória para métodos que fazem UPDATE, DELETE ou INSERT na base de dados real via @Query (Sempre precisa de @Transactional)|
| `@Transactional` | Garante que operações sejam executadas dentro de uma transação segura. **(Realiza execução atômica de operações)** |
```java
@Modifying
@Transactional
@Query("UPDATE Usuario u SET u.status = :status WHERE u.id = :id")
void atualizarStatus(@Param("id") Long id, @Param("status") StatusEnum status);

```
| Anotação | Descrição |
|----------|-----------|
| `@Lock` | Controle de concorrência em nível SQL (pessimista/otimista).|
| `@EntityGraph` | Define carregamento antecipado (JOIN FETCH) sem alterar a entidade.|
| `@Procedure` | Permite chamar Stored Procedures do banco de dados diretamente pelo Repository.|
| `@QueryHints` | Passar dicas específicas para o provedor JPA (como Hibernate) alterar comportamento da query.|
| `@RestResource` |Customização de endpoints REST gerados automaticamente(Utilizada apenas quando usa Spring Data REST.)|






