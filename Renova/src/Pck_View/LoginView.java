package Pck_View;

import Pck_Control.LoginUsuarioControl;
import Pck_Model.LoginUsuarioModel;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

public class LoginView extends JFrame{

    JButton entrarBtn = new JButton("Entrar");
    JButton cadastrarBtn = new JButton("Cadastre-se aqui");
    JButton voltarBtn = new JButton("Voltar");

    JTextField emailCliente = new JTextField();
    JPasswordField senhaCliente = new JPasswordField();

    public LoginView(){
        setTitle("Bem-Vindo(a) ao Renova!");
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

        entrarBtn.addActionListener( _ ->{
            LoginUsuarioControl usuarioControl = new LoginUsuarioControl();

            String email = emailCliente.getText();
            String senha = senhaCliente.getText();

            try{
                LoginUsuarioModel usuarioLogado = usuarioControl.realizarLogin(email, senha);

                if(usuarioLogado != null){

                    if(usuarioLogado.getTipoUsuario().toUpperCase().equals("CLIENTE")){
                        new ClienteHomeView(usuarioLogado).setVisible(true);
                    } else{
                        new FuncionarioHomeView(usuarioLogado).setVisible(true);
                    }

                    dispose();
                } else{
                    JOptionPane.showMessageDialog(this, "E-mail ou senha incorretos.",
                            "Erro",
                            JOptionPane.WARNING_MESSAGE);
                }

            } catch (IllegalArgumentException erroModel) {
                JOptionPane.showMessageDialog(this, erroModel.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (RuntimeException erroBanco) {
                JOptionPane.showMessageDialog(this, "Erro no servidor: " + erroBanco.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        });

        cadastrarBtn.addActionListener(_ -> {
            new CadastroClienteView().setVisible(true);

            dispose();

        });

        voltarBtn.addActionListener(_ ->{
            new IndexView().setVisible(true);

            // fecha a pagina atual
            dispose();
        });

    }
}
