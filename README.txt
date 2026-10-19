ProjetoDAO - Java + JDBC + SQLite

Requisitos:
- JDK 17 ou superior
- Maven

Como executar:
1. Abra a pasta ProjetoDAO em uma IDE (IntelliJ IDEA, Eclipse ou VS Code).
2. Aguarde o Maven baixar a dependência sqlite-jdbc.
3. Execute src/main/java/Main.java.
4. O programa cria automaticamente a pasta dados e a tabela produto.

Estrutura:
- model/Produto.java: entidade Produto
- infra/ConexaoSQLite.java: conexão e criação da tabela
- dao/ProdutoDAO.java: contrato CRUD
- dao/ProdutoDAOImpl.java: implementação JDBC
- Main.java: exemplo de uso

O banco será criado em dados/loja.db, relativo à pasta de execução.
