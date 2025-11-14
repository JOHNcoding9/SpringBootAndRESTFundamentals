#  Camada Repository (PT-BR)

## 🎯 Função principal

A camada **Repository** abstrai o acesso de dados. Ele atua como uma **ponte/túnel entre a aplicação e o banco de dados**, permitindo
manipular entidades sem se preocupar com os detalhes de persistência.
Ou seja, A camada oculta todo o comportamento necessário para que uma entidade seja salva, consultada, atualizada e removida, **sem que a camada de serviço precise conhecer os mecanismos internos do banco**.

##  Papel do repository na arquitetura:
⚫ isola a lógica de acesso aos dados
⚫ facilita testes de manutenção
⚫ promove reutilização de código
⚫ permite trocar a fonte de dados sem alterar a aplicação
---
# criando repository:
```java
@Repository
public interface AlunoRepository extends JpaRepository <Aluno,Long> {
 List<Aluno>findByNome(string nome);
}
```

O que acontece no código acima?
🔵 JpaRepository fornece seus métodos automáticos à interface Aluno.
🔵 O método findByNome() segue o padrão findBy{atributo}().
🔵 O Spring gera a implementação em tempo de execução, sem que você precise escrever o código SQL da consulta.



O Spring Data cria métodos automaticamente, desde que você siga o padrão de nome: 
⚙️ Save()
⚙️ findBy{atributo}()
⚙️ findAll()
⚙️ delete()
⚙️ count()
⚙️ existsBy{atributo}()

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
Exemplo em JPQL:
```jpql
@Repository
public interface AlunoRepository extends JpaRepository<Aluno,Long> {
 @Query("SELECT a FROM Aluno a WHERE a.nome =: nome AND a.idade >=: idade")
 List<Aluno>buscaAlunoPorNomeIdade(@Param("nome")String nome, @Param("idade")String idade)
}
```



# :open_book: Mapeamento de Repository

### 🔗 Anotações de CLASSE 
| Anotação | Descrição |
|----------|-----------|
| `@Entity` | Marca a classe como entidade gerenciada pelo **JPA (Java Persistence API)**. |
| `@Table(name = "nome_tabela")` | Define explicitamente o nome da tabela associada. Se omitida, o nome da classe será usado. |

### :key: Anotações de IDENTIFICAÇÃO 
| Anotação | Descrição |
|----------|-----------|
| `@Id` | Identifica o campo que representa a **chave primária** da entidade. |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | Define a estratégia de geração automática do ID (ex.: `IDENTITY`, `AUTO`, `SEQUENCE`, `TABLE`). |

### 📊 Anotações de COLUNAS
| Anotação | Descrição |
|----------|-----------|
| `@Column(nullable = false)` | Personaliza uma coluna: nome, obrigatoriedade (`nullable`), unicidade (`unique`), tamanho (`length`), etc. |
| `@Lob` | Indica que o campo será persistido como objeto de grande tamanho (LOB); geralmente usado para BLOBs ou CLOBs. |
| `@Transient` | Indica que o campo **não será persistido** no banco de dados — apenas na memória da aplicação. |

### :family_man_woman_boy: Anotações de RELACIONAMENTOS
| Anotação | Tipo | Descrição |
|----------|------|-----------|
| `@OneToOne` | 1 : 1 | Um registro está ligado exatamente a outro. |
| `@OneToMany` | 1 : N | Um registro da entidade está ligado a vários de outra entidade. |
| `@ManyToOne` | N : 1 | Vários registros da entidade fazem referência a um registro de outra entidade. |
| `@ManyToMany` | N : N | Vários registros de ambas as entidades estão associados entre si. |
| `@JoinColumn` | — | Define a coluna da chave estrangeira (ex.: `referencedColumnName`, `nullable`, `name`) para mapear o relacionamento. |

### :pencil: Anotações de DATA E AUDITORIA
| Anotação | Descrição |
|----------|-----------|
| `@Temporal` | Controla o tipo de dado para atributos de data/hora (`TemporalType.DATE`, `TIME`, `TIMESTAMP`). |
| `@CreationTimestamp` | Preenche automaticamente com a data/hora da **criação** do registro. |
| `@UpdateTimestamp` | Preenche automaticamente com a data/hora da **última atualização** do registro. |

### :crown: Anotações de HERANÇA  
| Anotação | Descrição |
|----------|-----------|
| `@MappedSuperclass` | Marca uma classe como superclasse de entidades. Não vira uma tabela, mas suas subclasses herdam seus campos. |
| `@Inheritance(strategy = InheritanceType.*)` | Define a estratégia de mapeamento de herança para entidades (ex.: `JOINED`, `SINGLE_TABLE`, `TABLE_PER_CLASS`). |





