# Modelagem de entidade

Exercicio do modulo **Modelagem de entidade** da EBAC.

## O que foi pedido

O exercicio pede uma modelagem JPA com duas entidades principais:

- `Curso`
- `Matricula`

A modelagem deve criar as tabelas no banco de dados e representar o relacionamento entre elas:

- um `Curso` pode possuir varias `Matricula`;
- uma `Matricula` pertence a um unico `Curso`.

## O que foi feito

- Projeto Maven em Java 17.
- Entidade `Curso` mapeada para `TB_CURSO`.
- Entidade `Matricula` mapeada para `TB_MATRICULA`.
- Relacionamento `@OneToMany` de `Curso` para `Matricula`.
- Relacionamento `@ManyToOne` de `Matricula` para `Curso`.
- Chave estrangeira `FK_CURSO_MATRICULA`.
- Testes com H2 em memoria para validar a criacao das tabelas e a persistencia do relacionamento.

## Como verificar

Execute:

```bash
mvn test
```

Os testes verificam:

- se as tabelas `TB_CURSO` e `TB_MATRICULA` foram criadas;
- se uma matricula e persistida vinculada a um curso;
- se o relacionamento entre as entidades esta funcionando.
