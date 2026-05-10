package Pck_View;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

public class LoginCliente extends JFrame{

    JButton entrarBtn = new JButton("Entrar");
    JButton cadastrarBtn = new JButton("Cadastre-se aqui");
    JButton voltarBtn = new JButton("Voltar");

    public LoginCliente(){
        setTitle("Login Cliente");
        setBounds(100, 100, 800, 700);

        // o GridBagLayout centraliza os elementos automaticamente
        getContentPane().setLayout(new GridBagLayout());

        // objeto que adiciona as restrições pra controlar a posição dos elementos
        GridBagConstraints gbc = new GridBagConstraints();

        // faz com que todos os elementos fiquem na mesma coluna, nesse caso a 0
        gbc.gridx = 0;

        // aplica margem
        gbc.insets = new Insets(10, 0, 10, 0);

        // titulo paginaa
        JLabel tituloPagina = new JLabel("Faça seu login");
        gbc.gridx = 0; // começa na coluna 0
        gbc.gridy = 0; // linha 0
        gbc.gridwidth = 2; // ocupa 2 colunas para ficar centralizado
        getContentPane().add(tituloPagina, gbc);

        gbc.gridwidth = 1; // todos os elementos inseridos apos vao ocupar apenas uma linha

        // label email
        JLabel labelEmailCliente = new JLabel("Email: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 1; // linha 1
        getContentPane().add(labelEmailCliente, gbc);

        // input email
        JTextField emailCliente = new JTextField();
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 1; // linha 1
        emailCliente.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(emailCliente, gbc);

        // label senha
        JLabel labelSenhaCliente = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(labelSenhaCliente, gbc);

        // input senha
        JPasswordField senhaCliente = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        senhaCliente.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(senhaCliente, gbc);

        gbc.gridwidth = 2; // todos os elementos inseridos apos vao ocupar duas linhas

        // entrar btn
        entrarBtn.setPreferredSize(new Dimension(250, 40));
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        getContentPane().add(entrarBtn, gbc);

        // cadastrar btn
        cadastrarBtn.setPreferredSize(new Dimension(250, 40));
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        getContentPane().add(cadastrarBtn, gbc);

        // voltar btn
        voltarBtn.setPreferredSize(new Dimension(250, 40));
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        getContentPane().add(voltarBtn, gbc);

        eventos();

    }

    private void eventos(){
        voltarBtn.addActionListener(e ->{
            new IndexView().setVisible(true);

            // fecha a pagina atual
            dispose();
        });

        cadastrarBtn.addActionListener(e -> {
            new CadastroCliente().setVisible(true);

        });

    }
}
