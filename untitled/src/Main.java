//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static class Produto{
        private String nome;
        private String preco;
        private String porcentagem_desconto;

        public void setNome(String nome) {
            if(nome != null && nome.length() > 0){
                this.nome = nome;
                System.out.println("O nome eh: " + this.nome);
            } else{
                System.out.println("Digite um nome valido!");
            }

        }

        public void setPreco(String preco) {

            if(preco != null){
                double valorConvertido = Double.parseDouble(preco);

                if(valorConvertido > 0){
                    this.preco = preco;
                    System.out.println("O preco eh: " + this.preco);
                } else{
                    System.out.println("Informe um preco valido!");
                }
            }
        }

        public String getPreco() {

            return this.preco;
        }

        public void setPorcentagem_desconto(String porcentagem_desconto) {
            if(porcentagem_desconto != null){
                double porcentagem_desconto_convertida = Double.parseDouble(porcentagem_desconto);
                if(porcentagem_desconto_convertida != 0){

                    double valorConvertido = Double.parseDouble(preco);


                    double newPrice = (valorConvertido - (valorConvertido * (porcentagem_desconto_convertida/100)));
                    System.out.println("O valor do desconto inserido eh: " + porcentagem_desconto_convertida + "%");
                    System.out.println("O valor do produto com desconto eh: " + newPrice);

                } else{
                    System.out.println("Insira uma porcentagem de desconto valida!");
                }
            }


        }


    }
    public static void main(String[] args) {

        Produto arroz = new Produto();

        arroz.setNome("Arroz integral");

        arroz.setPreco("100");
        arroz.setPreco("-1");

        arroz.setPorcentagem_desconto("10.5");

        String preco = arroz.getPreco();

        System.out.println(preco);

    }

}