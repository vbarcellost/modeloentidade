# Modelagem de entidade

Exercicio do modulo **Modelagem de entidade** da EBAC.

## O que foi pedido

O exercicio pede a criacao da classe entidade `Produto`, seguindo o modelo das aulas de JPA, para que a tabela seja criada automaticamente no banco de dados pelo Hibernate.

Essa foi a classe indicada para a tarefa pratica do modulo.

## O que foi feito

- Projeto Maven em Java 17.
- Entidade `Produto` mapeada para `TB_PRODUTO`.
- Campos `id`, `codigo`, `nome`, `descricao` e `valor`.
- Sequencia `SQ_PRODUTO` para gerar o identificador.
- Configuracao JPA/Hibernate em `persistence.xml`.
- Testes com H2 em memoria para validar a criacao da tabela e a persistencia de um produto.

## Como verificar

Execute:

```bash
mvn test
```

Os testes verificam:

- se a tabela `TB_PRODUTO` foi criada;
- se um `Produto` e persistido e consultado corretamente.
