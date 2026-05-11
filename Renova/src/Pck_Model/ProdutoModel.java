package Pck_Model;

public class ProdutoModel {
    private String nomeProduto;
    private String descricao;
    private Double preco;
    private String status;

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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        if(preco == null || preco <= 0.0){
            throw new IllegalArgumentException("Informe um valor válido ao produto.");
        } else{
            this.descricao = descricao;
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
