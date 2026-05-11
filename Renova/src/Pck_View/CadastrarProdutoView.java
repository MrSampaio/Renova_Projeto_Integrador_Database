package Pck_View;
import javax.swing.*;
import java.awt.*;

public class CadastrarProdutoView extends JFrame{

    JTextField nomeProdutoInput = new JTextField();
    JTextArea descricaoInput = new JTextArea();
    JTextField precoInput = new JTextField();

    String[] opcoesStatus = {"Disponível", "Reservado", "Vendido"};
    JComboBox<String> statusInput = new JComboBox<>(opcoesStatus);

    JButton cadastrarProdutoBtn = new JButton("Adicionar produto");
    JButton voltarBtn = new JButton("Voltar");

    public CadastrarProdutoView(){
        setTitle("Cadastrar produto");
        setBounds(100, 100, 800, 700);

        // o GridBagLayout centraliza os elementos automaticamente
        getContentPane().setLayout(new GridBagLayout());

        // objeto que adiciona as restrições pra controlar a posição dos elementos
        GridBagConstraints gbc = new GridBagConstraints();

        // faz com que todos os elementos fiquem na mesma coluna, nesse caso a 0
        gbc.gridx = 0;

        // aplica margem
        gbc.insets = new Insets(10, 0, 10, 0);

        // titulo pagina
        JLabel tituloPagina = new JLabel("Cadastre novos produtos");
        gbc.gridx = 0; // começa na coluna 0
        gbc.gridy = 0; // linha 0
        gbc.gridwidth = 2; // ocupa 2 colunas para ficar centralizado
        getContentPane().add(tituloPagina, gbc);

        gbc.gridwidth = 1; // todos os elementos inseridos apos vao ocupar apenas uma linha

        JLabel labelNomeProduto = new JLabel("Nome do produto: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 1; // linha 1
        getContentPane().add(labelNomeProduto, gbc);

        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 1; // linha 1
        nomeProdutoInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(nomeProdutoInput, gbc);

        JLabel labelDescricaoInput = new JLabel("Descricao do produto: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 2; // linha 1
        getContentPane().add(labelDescricaoInput, gbc);

        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 2; // linha 1
        descricaoInput.setPreferredSize(new Dimension(300, 60));
        descricaoInput.setLineWrap(true);
        descricaoInput.setWrapStyleWord(true);
        descricaoInput.setAutoscrolls(true);
        getContentPane().add(descricaoInput, gbc);

        JLabel labelPreco = new JLabel("Preco do produto: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 3; // linha 1
        getContentPane().add(labelPreco, gbc);

        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 3; // linha 1
        precoInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(precoInput, gbc);

        JLabel labelStatus = new JLabel("Status do produto: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 4; // linha 4 (abaixo do preço)
        getContentPane().add(labelStatus, gbc);

        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 4; // linha 4
        statusInput.setPreferredSize(new Dimension(300, 35));
        statusInput.setSelectedIndex(0);
        getContentPane().add(statusInput, gbc);

        cadastrarProdutoBtn.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        getContentPane().add(cadastrarProdutoBtn, gbc);

        voltarBtn.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        getContentPane().add(voltarBtn, gbc);

        eventos();
    }

    public void eventos(){
        cadastrarProdutoBtn.addActionListener( _ ->{
            System.out.println("Produto sendo cadastrado");
        });

        voltarBtn.addActionListener( _ ->{
           dispose();
        });
    }

    static void main(String[] args) {
        new CadastrarProdutoView().setVisible(true);
    }

}
