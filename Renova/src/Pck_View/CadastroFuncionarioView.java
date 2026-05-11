package Pck_View;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

public class CadastroFuncionarioView extends JFrame{

    JTextField emailInput = new JTextField();
    JTextField passwordInput = new JTextField();
    JTextField confirmPasswordInput = new JTextField();
    JTextField nomeInput = new JTextField();
    JTextField telefoneInput = new JTextField();


    public CadastroFuncionarioView(){
        setTitle("Cadastro");
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
        JLabel tituloPagina = new JLabel("Realize seu cadastro");
        gbc.gridx = 0; // começa na coluna 0
        gbc.gridy = 0; // linha 0
        gbc.gridwidth = 2; // ocupa 2 colunas para ficar centralizado
        getContentPane().add(tituloPagina, gbc);

        gbc.gridwidth = 1; // todos os elementos inseridos apos vao ocupar apenas uma linha

        // label email
        JLabel labelEmailInput = new JLabel("Email: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 1; // linha 1
        getContentPane().add(labelEmailInput, gbc);

        // input email
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 1; // linha 1
        emailInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(emailInput, gbc);


    }
}
