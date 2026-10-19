package dao;

import model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoDAO {
    Produto criar(Produto produto);
    Optional<Produto> buscarPorId(long id);
    List<Produto> listarTodos();
    boolean atualizar(Produto produto);
    boolean excluir(long id);
}
