package Pck_View;

import Pck_Model.LoginUsuarioModel;
import javax.swing.*;
import java.awt.*;

public class FuncionarioHomeView extends JFrame {

    JButton btnProdutos = new JButton("Área de Produtos");
    JButton btnVerificarPedido = new JButton("Verificar Pedidos");
    JButton btnAtenderPedido = new JButton("Atender Pedidos");
    JButton btnCadastrarFuncionario = new JButton("Cadastrar Novo Funcionário");
    JButton btnLogout = new JButton("Sair / Logout");

    LoginUsuarioModel usuarioLogado;

    public FuncionarioHomeView(LoginUsuarioModel usuario) {
        this.usuarioLogado = usuario;

        setTitle("Painel do Funcionário - " + usuario.getNome());
        setBounds(100, 100, 800, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(null);

        JLabel labelBemVindo = new JLabel("Bem-vindo(a), " + usuario.getNome() + "!", SwingConstants.CENTER);
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 22));
        labelBemVindo.setBounds(0, 50, 800, 30);
        getContentPane().add(labelBemVindo);

        JLabel labelSubtitulo = new JLabel("Selecione uma opção no menu abaixo:", SwingConstants.CENTER);
        labelSubtitulo.setBounds(0, 90, 800, 20);
        getContentPane().add(labelSubtitulo);

        int xCentro = 250;
        int larguraBotao = 300;
        int alturaBotao = 45;

        btnProdutos.setBounds(xCentro, 160, larguraBotao, alturaBotao);
        getContentPane().add(btnProdutos);

        btnVerificarPedido.setBounds(xCentro, 230, larguraBotao, alturaBotao);
        getContentPane().add(btnVerificarPedido);

        btnAtenderPedido.setBounds(xCentro, 300, larguraBotao, alturaBotao);
        getContentPane().add(btnAtenderPedido);

        btnCadastrarFuncionario.setBounds(xCentro, 370, larguraBotao, alturaBotao);
        getContentPane().add(btnCadastrarFuncionario);

        btnLogout.setBounds(xCentro, 480, larguraBotao, alturaBotao);
        btnLogout.setForeground(Color.RED);
        getContentPane().add(btnLogout);

        eventos();
    }

    private void eventos() {

        btnProdutos.addActionListener(e -> {

            new VisualizarProdutosView(usuarioLogado).setVisible(true);

            dispose();
        });

        btnVerificarPedido.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Em breve: Tela de Verificar Pedidos");
            // new VerificarPedidoView(usuarioLogado).setVisible(true);
        });

        btnAtenderPedido.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Em breve: Tela de Atender Pedidos");
            // new AtenderPedidoView(usuarioLogado).setVisible(true);
        });

        btnCadastrarFuncionario.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Em breve: Tela de Cadastro de Funcionário");
            // new CadastrarFuncionarioView().setVisible(true);
        });

        btnLogout.addActionListener(e -> {

            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja sair do sistema?",
                    "Confirmar Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                dispose();
                // new LoginView().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        try {
            LoginUsuarioModel usuarioFake = new LoginUsuarioModel();
            usuarioFake.setIdUsuario(1);
            usuarioFake.setNome("João Silva");
            usuarioFake.setEmail("joao@renova.com");
            usuarioFake.setSenha("123456");
            usuarioFake.setTipoUsuario("FUNCIONARIO");

            new FuncionarioHomeView(usuarioFake).setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro ao criar usuário fake: " + e.getMessage());
        }
    }
}