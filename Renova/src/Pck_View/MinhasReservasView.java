package Pck_View;

import Pck_Control.ReservaControl;
import Pck_Model.LoginUsuarioModel;
import Pck_Model.ProdutoModel;
import Pck_Model.ReservaModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MinhasReservasView extends JFrame{
    LoginUsuarioModel usuarioLogado;

    ProdutoModel produtoModel = new ProdutoModel();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modeloProdutos = new DefaultTableModel();
    ReservaControl control = new ReservaControl();

    JTable produtosReserva = new JTable(modeloProdutos);
    JTable tabela = new JTable(modelo);

    JLabel labelBuscaId = new JLabel("Pesquisar por ID:");
    JTextField inputBuscaId = new JTextField();
    JButton btnBuscaId = new JButton("Buscar");

    JButton btnRecarregar = new JButton("Limpar Filtro / Recarregar Tudo");

    JButton btnVoltar = new JButton("Voltar");

    public MinhasReservasView(LoginUsuarioModel usuarioLogado){
        this.usuarioLogado = usuarioLogado;

        setTitle("Meus pedidos");
        setBounds(300, 0, 1000, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(243, 236, 208));
        getContentPane().setLayout(new GridBagLayout());

        // define o layout absoluto
        getContentPane().setLayout(null);



        JLabel tituloPagina = new JLabel("Visualize seus pedidos");
        tituloPagina.setFont(new Font("Segoe UI", Font.BOLD, 25));
        tituloPagina.setBounds(10, 10, 300, 40);
        getContentPane().add(tituloPagina);

        JLabel subtituloPagina = new JLabel("Clique sobre o pedido para visualizar os produtos");
        subtituloPagina.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtituloPagina.setForeground(Color.BLUE);
        subtituloPagina.setBounds(10, 35, 800, 40);
        getContentPane().add(subtituloPagina);


        modelo.addColumn("Código Reserva");
        modelo.addColumn("Total Reserva");
        modelo.addColumn("Método de pagamento");
        modelo.addColumn("Data/hora da reserva");
        modelo.addColumn("Data limite de retirada");
        modelo.addColumn("Status da reserva");

        JScrollPane painel = new JScrollPane(tabela);
        painel.setBounds(10, 70, 900, 150);
        getContentPane().add(painel);

        tabela.setShowGrid(true);
        tabela.setShowHorizontalLines(true);
        tabela.setShowVerticalLines(true);
        tabela.setGridColor(Color.BLACK);
        tabela.setIntercellSpacing(new Dimension(2, 2));

        labelBuscaId.setBounds(20, 240, 120, 30);
        getContentPane().add(labelBuscaId);

        inputBuscaId.setBounds(140, 240, 150, 30);
        getContentPane().add(inputBuscaId);

        btnBuscaId.setBounds(300, 240, 120, 30);
        getContentPane().add(btnBuscaId);

        btnRecarregar.setBounds(20, 280, 250, 35);
        getContentPane().add(btnRecarregar);

        JLabel produtosPorReserva = new JLabel("Produtos da reserva");
        produtosPorReserva.setFont(new Font("Segoe UI", Font.BOLD, 20));

        produtosPorReserva.setBounds(10, 350, 300, 40);
        getContentPane().add(produtosPorReserva);

        JScrollPane scrollSelecionados = new JScrollPane(produtosReserva);

        modeloProdutos.addColumn("Código Pedido");
        modeloProdutos.addColumn("Código Produto");
        modeloProdutos.addColumn("Nome");
        modeloProdutos.addColumn("Descrição");
        modeloProdutos.addColumn("Preço");

        produtosReserva.setShowGrid(true);
        produtosReserva.setShowHorizontalLines(true);
        produtosReserva.setShowVerticalLines(true);
        produtosReserva.setGridColor(Color.BLACK);
        produtosReserva.setIntercellSpacing(new Dimension(2, 2));

        scrollSelecionados.setBounds(10, 400, 900, 300);
        getContentPane().add(scrollSelecionados);

        btnVoltar.setBounds(20, 750, 250, 35);
        getContentPane().add(btnVoltar);

        carregarTabela();
        eventos();

    }

    private void carregarTabela(){

        try {
            modelo.setRowCount(0);// Limpa a tabela
            int idUsuario = usuarioLogado.getIdUsuario();
            ArrayList<ReservaModel> lista = control.listarReservas(idUsuario);

            for(ReservaModel reserva : lista) {

                modelo.addRow(new Object[]{
                        reserva.getIdReserva(),
                        "R$ " + String.format("%.2f", reserva.getTotalReserva()),
                        reserva.getMetodoPagamento(),
                        reserva.getDataReserva(),
                        reserva.getDataValidade(),
                        reserva.getStatusReserva()
                });
            }

        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + erro.getMessage());
        }
    }

    // preenche a tabela inferior com os produtos vinculados à linha mestre clicada
    private void carregarProdutosDaReserva(int idReserva) {
        try {
            modeloProdutos.setRowCount(0); // Limpa os detalhes anteriores
            ArrayList<ProdutoModel> produtos = control.listarProdutosPorReserva(idReserva);

            for (ProdutoModel p : produtos) {
                modeloProdutos.addRow(new Object[]{
                        idReserva,
                        p.getIdProduto(),
                        p.getNomeProduto(),
                        p.getDescricao(),
                        "R$ " + String.format("%.2f", p.getPreco())
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos do pedido: " + e.getMessage());
        }
    }

    private void eventos() {

        // GATILHO: detecta cliques na tabela de reservas superiores
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();

                if (linhaSelecionada >= 0) {
                    // pega o valor da coluna 0 (código reserva) da linha clicada
                    int idReservaClicada = (int) modelo.getValueAt(linhaSelecionada, 0);

                    // chama o método para preencher a tabela de baixo
                    carregarProdutosDaReserva(idReservaClicada);
                }
            }
        });

        // ação do botão de recarregar filtros
        btnRecarregar.addActionListener(e -> {
            inputBuscaId.setText("");
            modeloProdutos.setRowCount(0);
            carregarTabela();
        });

        btnBuscaId.addActionListener(e -> {
            String idDigitado = inputBuscaId.getText();

            try {
                int idBusca = Integer.parseInt(idDigitado);
                int idUsuario = usuarioLogado.getIdUsuario();

                ReservaModel reserva = control.buscarReservaPorId(idBusca, idUsuario);

                modelo.setRowCount(0);
                modeloProdutos.setRowCount(0);

                if (reserva != null) {
                    modelo.addRow(new Object[]{
                            reserva.getIdReserva(),
                            "R$ " + String.format("%.2f", reserva.getTotalReserva()),
                            reserva.getMetodoPagamento(),
                            reserva.getDataReserva(),
                            reserva.getDataValidade(),
                            reserva.getStatusReserva()
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhuma reserva encontrada com o ID " + idBusca + " para o seu usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    carregarTabela();
                }

            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um número inteiro válido para a busca.", "Erro de preenchimento", JOptionPane.ERROR_MESSAGE);
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e ->{
            new ClienteHomeView(usuarioLogado).setVisible(true);
            dispose();
        });
    }

    static void main(String[] args) {
        LoginUsuarioModel usuarioFake = new LoginUsuarioModel();
        usuarioFake.setIdUsuario(1);
        usuarioFake.setNome("Administrador Teste");
        usuarioFake.setEmail("admin@renova.com");
        usuarioFake.setSenha("123456");

        // TROQUE AQUI PARA TESTAR: "FUNCIONARIO" ou "CLIENTE"
        usuarioFake.setTipoUsuario("CLIENTE");

        new MinhasReservasView(usuarioFake).setVisible(true);
    }

}
