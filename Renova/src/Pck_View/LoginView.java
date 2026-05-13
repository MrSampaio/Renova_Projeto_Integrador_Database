package Pck_View;

import Pck_Control.LoginUsuarioControl;
import Pck_Model.LoginUsuarioModel;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;

public class LoginView extends JFrame{

    JButton entrarBtn = new JButton("Entrar");
    JButton cadastrarBtn = new JButton("Cadastre-se aqui");
    JTextField emailCliente = new JTextField();
    JPasswordField senhaCliente = new JPasswordField();

    public LoginView(){
        setTitle("Bem-Vindo(a) ao Renova!");
        setBounds(100, 100, 800, 700);

        getContentPane().setBackground(new Color(199, 255, 235));
        getContentPane().setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        java.net.URL imgUrl = getClass().getResource("/imgs/logo.png");

        if (imgUrl != null) {
            ImageIcon iconeOriginal = new ImageIcon(imgUrl);
            Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon iconeFinal = new ImageIcon(imagemRedimensionada);

            JLabel labelImagem = new JLabel(iconeFinal);
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            getContentPane().add(labelImagem, gbc);
        } else {
            System.err.println("Aviso: Imagem /imgs/logo.png não encontrada!");
        }

        gbc.insets = new Insets(30, 0, 10, 0);

        JLabel tituloPagina = new JLabel("Faça seu login");
        tituloPagina.setFont(new Font("Segoe UI", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 1; // LINHA 1
        gbc.gridwidth = 2; // Centraliza nas duas colunas
        getContentPane().add(tituloPagina, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = 1; // Volta a ocupar apenas 1 coluna de cada lado

        // label email
        JLabel labelEmailCliente = new JLabel("Email: ");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 2; // LINHA 2
        getContentPane().add(labelEmailCliente, gbc);

        // input email
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 2; // LINHA 2
        emailCliente.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(emailCliente, gbc);

        // label senha
        JLabel labelSenhaCliente = new JLabel("Senha:");
        gbc.gridx = 0; // coluna 0 (esquerda)
        gbc.gridy = 3; // LINHA 3
        getContentPane().add(labelSenhaCliente, gbc);

        // input senha
        gbc.gridx = 1; // coluna 1 (direita)
        gbc.gridy = 3; // LINHA 3
        senhaCliente.setPreferredSize(new Dimension(300, 35));
        getContentPane().add(senhaCliente, gbc);

        gbc.gridwidth = 2; // Os botões ocupam as duas colunas para centralizar

        // entrar btn
        entrarBtn.setPreferredSize(new Dimension(250, 40));
        entrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // entrarBtn.setBackground(new Color(0, 120, 215)); // Azul padrão
        entrarBtn.setForeground(Color.BLACK); // Texto branco
        entrarBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4; // LINHA 4
        getContentPane().add(entrarBtn, gbc);

        // cadastrar btn
        cadastrarBtn.setPreferredSize(new Dimension(250, 40));
        gbc.gridx = 0;
        gbc.gridy = 5; // LINHA 5
        getContentPane().add(cadastrarBtn, gbc);

        eventos();
    }

    private void eventos(){

        entrarBtn.addActionListener( _ ->{
            LoginUsuarioControl usuarioControl = new LoginUsuarioControl();

            String email = emailCliente.getText();
            String senha = new String(senhaCliente.getPassword()); // Jeito mais seguro de pegar senha do JPasswordField

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
    }
}