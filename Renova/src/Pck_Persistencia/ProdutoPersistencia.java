package Pck_Persistencia;

import Pck_Model.ProdutoModel;
import Pck_DAO.ConexaoMySql;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoPersistencia {

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet resultSet;

    public void cadastrarProduto(ProdutoModel produto){

        String sql = "{CALL PROC_CADASTRAR_PRODUTO(?, ?, ?, ?)}";

        try{
            ConexaoMySql conexaoBD = new ConexaoMySql();

            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);

            stmt = conn.prepareCall(sql);

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setString(4, produto.getStatus());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("O produto " + "'" + produto.getNomeProduto() + "' foi adicionado com sucesso.");
            } else{
                System.out.println("Erro ao cadastrar produto.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco de dados ao cadastrar produto: " + e.getMessage());

        }finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    new ConexaoMySql().desconectar();
                }
            } catch (SQLException e) {
                System.out.println("Falha ao fechar a conexão com o banco - " + e.getMessage());
            }
        }

    }

    public ArrayList<ProdutoModel> listarProduto() throws SQLException {
        String sql = "{CALL PROC_LISTAR_PRODUTOS()}";

        try{
            ConexaoMySql conexaoBD = new ConexaoMySql();

            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);

            ArrayList<ProdutoModel> lista = new ArrayList<>();

            resultSet= stmt.executeQuery();

            while(resultSet.next()){

                ProdutoModel produto = new ProdutoModel();

                produto.setIdProduto(resultSet.getInt("id_produto"));
                produto.setNomeProduto(resultSet.getString("nome_produto"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setStatus(resultSet.getString("status"));

                lista.add(produto);
            }

            if(!lista.isEmpty()){
                System.out.println("Produtos listados com sucesso.");
            } else{
                System.out.println("Erro ao listar produtos.");
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco de dados ao listar produtos: " + e.getMessage());

        }finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    new ConexaoMySql().desconectar();
                }
                if(resultSet != null){
                    resultSet.close();
                }

            } catch (SQLException e) {
                System.out.println("Falha ao fechar a conexão com o banco - " + e.getMessage());
            }
        }
    }

    public ProdutoModel buscarProdutosID(int pesquisa) {

        String sql = "{CALL PROC_BUSCAR_PRODUTO_POR_ID(?)}";

        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;

        try {
            ConexaoMySql conexaoBD = new ConexaoMySql();
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);

            stmt.setInt(1, pesquisa);
            resultSet = stmt.executeQuery();


            if(resultSet.next()) {

                ProdutoModel produtoEncontrado = new ProdutoModel();

                produtoEncontrado.setIdProduto(resultSet.getInt("id_produto"));
                produtoEncontrado.setNomeProduto(resultSet.getString("nome_produto"));
                produtoEncontrado.setDescricao(resultSet.getString("descricao"));
                produtoEncontrado.setPreco(resultSet.getDouble("preco"));
                produtoEncontrado.setStatus(resultSet.getString("status"));

                System.out.println("Produto ID " + pesquisa + " encontrado com sucesso.");

                return produtoEncontrado;

            } else {

                System.out.println("Nenhum produto encontrado com o ID: " + pesquisa);

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco de dados ao buscar produto por ID: " + e.getMessage());

        } finally {
            try {
                if(resultSet != null){
                    resultSet.close();
                }
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    new ConexaoMySql().desconectar();
                }
            } catch (SQLException e) {
                System.out.println("Falha ao fechar a conexão com o banco - " + e.getMessage());
            }
        }
    }

    public ArrayList<ProdutoModel> buscarProdutosNome(String pesquisa) throws SQLException{
        String sql = "{CALL PROC_BUSCAR_PRODUTO_POR_NOME(?)}";

        try{
            ConexaoMySql conexaoBD = new ConexaoMySql();

            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);

            ArrayList<ProdutoModel> lista = new ArrayList<>();

            stmt.setString(1, pesquisa);
            resultSet= stmt.executeQuery();

            while(resultSet.next()){

                ProdutoModel produto = new ProdutoModel();

                produto.setIdProduto(resultSet.getInt("id_produto"));
                produto.setNomeProduto(resultSet.getString("nome_produto"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setStatus(resultSet.getString("status"));

                lista.add(produto);
            }

            if(!lista.isEmpty()){
                System.out.println("Produtos buscados por nome listados com sucesso.");
            } else{
                System.out.println("Erro ao listar produtos por nome.");
                throw new RuntimeException("Nenhum produto encontrado.");
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco de dados ao listar produtos por nome: " + e.getMessage());

        }finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    new ConexaoMySql().desconectar();
                }
                if(resultSet != null){
                    resultSet.close();
                }

            } catch (SQLException e) {
                System.out.println("Falha ao fechar a conexão com o banco - " + e.getMessage());
            }
        }
    }
}
