package Pck_View;
import javax.swing.*;
import java.awt.*;

import Pck_Control.CadastroUsuarioControl;

public class CadastroClienteView extends JFrame {

    JTextField emailInput = new JTextField();
    JPasswordField passwordInput = new JPasswordField();
    JPasswordField confirmPasswordInput = new JPasswordField();
    JTextField nomeInput = new JTextField();
    JTextField telefoneInput = new JTextField();

    JButton cadastrarBtn = new JButton("Cadastrar");
    JButton voltarBtn = new JButton("Voltar");

    public CadastroClienteView(){
        setTitle("Cadastro Cliente");
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
        JLabel tituloPagina = new JLabel("Realize seu cadastro de cliente");
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

        // label senha
        JLabel labelsenhaInput = new JLabel("Senha: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 2; // linha 1
        getContentPane().add(labelsenhaInput, gbc);

        // input senha
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 2; // linha 1
        passwordInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(passwordInput, gbc);

        // label confirmPassword
        JLabel labelconfirmPassword = new JLabel("Confirme sua senha: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 3; // linha 1
        getContentPane().add(labelconfirmPassword, gbc);

        // input confirmPassword
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 3; // linha 1
        confirmPasswordInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(confirmPasswordInput, gbc);

        // label nomeInput
        JLabel labelNomeInput = new JLabel("Nome: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 4; // linha 1
        getContentPane().add(labelNomeInput, gbc);

        // input nome
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 4; // linha 1
        nomeInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(nomeInput, gbc);

        // label telefoneInput
        JLabel labeltelefoneInput = new JLabel("Telefone: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 5; // linha 1
        getContentPane().add(labeltelefoneInput, gbc);

        // input telefone
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 5; // linha 1
        telefoneInput.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(telefoneInput, gbc);

        // cadastrar btn
        cadastrarBtn.setPreferredSize(new Dimension(250, 40));
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        getContentPane().add(cadastrarBtn, gbc);

        // voltarBtn
        voltarBtn.setPreferredSize(new Dimension(250, 40));
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        getContentPane().add(voltarBtn, gbc);

        eventos();
    }

    public void eventos(){
        cadastrarBtn.addActionListener(_ ->{

            CadastroUsuarioControl cadastroUsuario = new CadastroUsuarioControl();

            String nome = nomeInput.getText();
            String email = emailInput.getText();
            String senha = (new String (passwordInput.getPassword()));
            String confirmacaoSenha = (new String(confirmPasswordInput.getPassword()));
            String telefone = telefoneInput.getText();
            String tipoUsuario = "CLIENTE";

            if(!confirmacaoSenha.equals(senha)){
                JOptionPane.showMessageDialog(null, "As senhas não coincidem!!");
            } else{
                try{
                    cadastroUsuario.CadastroUsuarioControl(email, senha, telefone, nome, tipoUsuario);
                    JOptionPane.showMessageDialog(null, nome + ", seu cadastro foi concluído! Realize o login com email e senha para acessar sua conta.");
                    new LoginView().setVisible(true);
                    dispose();
                } catch (Exception erro){
                    JOptionPane.showMessageDialog(null,
                            "Erro ao cadastrar usuário: " + erro.getMessage(),
                            "Erro de Cadastro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        voltarBtn.addActionListener(e ->{
            new LoginView().setVisible(true);

            dispose();
        });
    }

}
