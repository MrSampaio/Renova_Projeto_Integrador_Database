package Pck_Model;

public class ProdutoModel {
    private int idProduto;
    private String nomeProduto;
    private String descricao;
    private double preco;
    private String status;


    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        if(idProduto <= 0){
            throw new IllegalArgumentException("Erro ao adicionar id do produto.");
        } else{
            this.idProduto = idProduto;
        }
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        if(nomeProduto == null || nomeProduto.isEmpty() || nomeProduto.length() <= 0){
            throw new IllegalArgumentException("O campo de 'nome do produto' é obrigatório!");
        } else{
            this.nomeProduto = nomeProduto;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if(descricao == null || descricao.isEmpty() || descricao.length() <= 0){
            throw new IllegalArgumentException("O campo de 'descricao' é obrigatório!");
        } else{
            this.descricao = descricao;
        }
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if(preco <= 0.0){
            throw new IllegalArgumentException("Informe um valor válido ao produto.");
        } else{
            this.preco = preco;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(!status.toUpperCase().equals("DISPONÍVEL") && !status.toUpperCase().equals("RESERVADO") && !status.toUpperCase().equals("VENDIDO")){
            throw new IllegalArgumentException("Erro ao atribuir status do produto.");
        } else{
            this.status = status;
        }

    }
}
