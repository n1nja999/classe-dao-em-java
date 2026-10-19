package dao;

import infra.ConexaoSQLite;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public Produto criar(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getEstoque());
            ps.executeUpdate();

            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    produto.setId(chaves.getLong(1));
                } else {
                    try (PreparedStatement idPs = conexao.prepareStatement("SELECT last_insert_rowid()");
                         ResultSet rs = idPs.executeQuery()) {
                        if (rs.next()) produto.setId(rs.getLong(1));
                    }
                }
            }
            return produto;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar produto", e);
        }
    }

    @Override
    public Optional<Produto> buscarPorId(long id) {
        String sql = "SELECT id, nome, preco, estoque FROM produto WHERE id = ?";
        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto", e);
        }
    }

    @Override
    public List<Produto> listarTodos() {
        String sql = "SELECT id, nome, preco, estoque FROM produto ORDER BY id";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) produtos.add(mapear(rs));
            return produtos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
    }

    @Override
    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, estoque = ? WHERE id = ?";
        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getEstoque());
            ps.setLong(4, produto.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }

    @Override
    public boolean excluir(long id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conexao = ConexaoSQLite.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto", e);
        }
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        return new Produto(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getDouble("preco"),
                rs.getInt("estoque")
        );
    }
}
