package Pck_Control;
import Pck_Model.ProdutoModel;
import Pck_Persistencia.ProdutoPersistencia;

import java.util.ArrayList;

public class ProdutoControl {
    public void CadastrarProduto(String nomeProduto, String descricao, double preco, String status){

        ProdutoModel produto = new ProdutoModel();

        produto.setNomeProduto(nomeProduto);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setStatus(status);

        ProdutoPersistencia persistencia = new ProdutoPersistencia();

        persistencia.cadastrarProduto(produto);

    }

    public ArrayList<ProdutoModel> listarProdutos() throws Exception {

        ProdutoPersistencia persistencia = new ProdutoPersistencia();
        return persistencia.listarProduto();
    }

    public ProdutoModel buscarProdutoID(int pesquisa) throws Exception{
        ProdutoPersistencia persistencia = new ProdutoPersistencia();

        return persistencia.buscarProdutosID(pesquisa);
    }

    public ArrayList<ProdutoModel> buscarProdutoNome(String pesquisa) throws Exception{
        ProdutoPersistencia persistencia = new ProdutoPersistencia();

        return persistencia.buscarProdutosNome(pesquisa);
    }


}
