package Pck_View;
import javax.swing.*;

import Pck_Model.LoginUsuarioModel;

import java.awt.*;

public class ClienteHomeView extends JFrame{

    JButton btnProdutos = new JButton("Área de Produtos");
    JButton btnMeusPedidos = new JButton("Meus Pedidos");
    JButton btnNovoPedido = new JButton("Realizar novo pedido");
    JButton btnLogout = new JButton("Sair / Logout");

    LoginUsuarioModel usuarioLogado;

    public ClienteHomeView(LoginUsuarioModel usuario) {
        this.usuarioLogado = usuario;

        setTitle("Painel do Cliente - " + usuario.getNome());
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

        btnMeusPedidos.setBounds(xCentro, 230, larguraBotao, alturaBotao);
        getContentPane().add(btnMeusPedidos);

        btnNovoPedido.setBounds(xCentro, 300, larguraBotao, alturaBotao);
        getContentPane().add(btnNovoPedido);

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

        btnMeusPedidos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Em breve: Tela de Meus Pedidos");
            // new VerificarPedidoView(usuarioLogado).setVisible(true);
        });

        btnNovoPedido.addActionListener(e -> {
            new RealizarPedidoView(usuarioLogado).setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> {

            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja sair do sistema?",
                    "Confirmar Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
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
            usuarioFake.setTipoUsuario("CLIENTE");

            new ClienteHomeView(usuarioFake).setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro ao criar usuário fake: " + e.getMessage());
        }
    }
}
