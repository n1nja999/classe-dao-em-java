package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ConexaoSQLite {
    private static final String URL = "jdbc:sqlite:dados/loja.db";

    public static Connection conectar() throws SQLException {
        try {
            Files.createDirectories(Path.of("dados"));
        } catch (IOException e) {
            throw new SQLException("Não foi possível criar a pasta dados.", e);
        }
        return DriverManager.getConnection(URL);
    }

    public static void criarTabela() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS produto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "preco REAL NOT NULL, " +
                "estoque INTEGER NOT NULL)";
        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        }
    }
}
