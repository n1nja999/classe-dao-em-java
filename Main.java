import dao.ProdutoDAO;
import dao.ProdutoDAOImpl;
import infra.ConexaoSQLite;
import model.Produto;

public class Main {
    public static void main(String[] args) {
        try {
            ConexaoSQLite.criarTabela();
            ProdutoDAO dao = new ProdutoDAOImpl();

            Produto produto = new Produto("Notebook", 3500.00, 10);
            dao.criar(produto);
            System.out.println("Produto cadastrado: " + produto);

            dao.buscarPorId(produto.getId())
                    .ifPresent(p -> System.out.println("Encontrado: " + p));

            System.out.println("Todos os produtos:");
            dao.listarTodos().forEach(System.out::println);

            produto.setPreco(3200.00);
            produto.setEstoque(8);
            if (dao.atualizar(produto)) {
                System.out.println("Produto atualizado!");
            }

            // Descomente para excluir o produto de teste:
            // if (dao.excluir(produto.getId())) {
            //     System.out.println("Produto excluído!");
            // }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
