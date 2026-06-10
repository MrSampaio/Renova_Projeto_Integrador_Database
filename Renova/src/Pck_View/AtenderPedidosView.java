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

public class AtenderPedidosView extends JFrame {

    LoginUsuarioModel usuarioLogado;
    ReservaControl control = new ReservaControl();

    // Modelos e Tabelas
    DefaultTableModel modeloReservas = new DefaultTableModel();
    DefaultTableModel modeloProdutos = new DefaultTableModel();
    JTable tabelaReservas = new JTable(modeloReservas);
    JTable produtosReserva = new JTable(modeloProdutos);

    // Campos de Busca
    JLabel labelBuscaId = new JLabel("Pesquisar por ID da Reserva:");
    JTextField inputBuscaId = new JTextField();
    JButton btnBuscaId = new JButton("Buscar ID");

    JLabel labelBuscaNome = new JLabel("Pesquisar por Nome do Cliente:");
    JTextField inputBuscaNome = new JTextField();
    JButton btnBuscaNome = new JButton("Buscar Nome");

    JButton btnRecarregar = new JButton("Limpar Filtros / Recarregar Tudo");

    // Botões de Ação do Funcionário
    JButton btnConcluir = new JButton("✔ Concluir Pedido");
    JButton btnCancelar = new JButton("✖ Cancelar Pedido");

    JButton btnVoltar = new JButton("Voltar");

    public AtenderPedidosView(LoginUsuarioModel usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Painel de Atendimento de Pedidos");
        setBounds(300, 0, 1000, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(243, 236, 208));
        getContentPane().setLayout(new GridBagLayout());

        getContentPane().setLayout(null);

        // --- CABEÇALHO ---
        JLabel tituloPagina = new JLabel("Gestão de Pedidos");
        tituloPagina.setFont(new Font("Segoe UI", Font.BOLD, 25));
        tituloPagina.setBounds(10, 10, 300, 40);
        getContentPane().add(tituloPagina);

        JLabel subtituloPagina = new JLabel("Selecione um pedido na tabela para visualizar os itens e alterar seu status.");
        subtituloPagina.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtituloPagina.setForeground(Color.BLUE);
        subtituloPagina.setBounds(10, 35, 800, 40);
        getContentPane().add(subtituloPagina);

        // --- TABELA 1: RESERVAS GERAIS (Agora com NOME DO CLIENTE) ---
        modeloReservas.addColumn("Código Reserva");
        modeloReservas.addColumn("Nome do Cliente"); // COLUNA NOVA
        modeloReservas.addColumn("Total Reserva");
        modeloReservas.addColumn("Método Pagamento");
        modeloReservas.addColumn("Data/hora");
        modeloReservas.addColumn("Data limite");
        modeloReservas.addColumn("Status");

        JScrollPane painelReservas = new JScrollPane(tabelaReservas);
        painelReservas.setBounds(10, 80, 960, 180);
        tabelaReservas.setShowGrid(true);
        tabelaReservas.setGridColor(Color.BLACK);
        getContentPane().add(painelReservas);

        // --- SEÇÃO DE BUSCA E FILTROS ---
        labelBuscaId.setBounds(10, 270, 180, 30);
        getContentPane().add(labelBuscaId);
        inputBuscaId.setBounds(200, 270, 100, 30);
        getContentPane().add(inputBuscaId);
        btnBuscaId.setBounds(310, 270, 100, 30);
        getContentPane().add(btnBuscaId);

        labelBuscaNome.setBounds(10, 310, 180, 30);
        getContentPane().add(labelBuscaNome);
        inputBuscaNome.setBounds(200, 310, 200, 30);
        getContentPane().add(inputBuscaNome);
        btnBuscaNome.setBounds(410, 310, 120, 30);
        getContentPane().add(btnBuscaNome);

        btnRecarregar.setBounds(10, 350, 250, 35);
        getContentPane().add(btnRecarregar);

        // --- SEÇÃO DE AÇÕES DO FUNCIONÁRIO ---
        btnConcluir.setBounds(630, 270, 160, 40);
        btnConcluir.setBackground(new Color(40, 167, 69)); // Verde
        btnConcluir.setForeground(Color.BLACK);
        btnConcluir.setFocusPainted(false);
        getContentPane().add(btnConcluir);

        btnCancelar.setBounds(810, 270, 160, 40);
        btnCancelar.setBackground(new Color(220, 53, 69)); // Vermelho
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        getContentPane().add(btnCancelar);

        // --- TABELA 2: PRODUTOS DO PEDIDO SELECIONADO ---
        JLabel produtosPorReserva = new JLabel("Itens do Pedido Selecionado:");
        produtosPorReserva.setFont(new Font("Segoe UI", Font.BOLD, 18));
        produtosPorReserva.setBounds(10, 400, 300, 40);
        getContentPane().add(produtosPorReserva);

        modeloProdutos.addColumn("Código Pedido");
        modeloProdutos.addColumn("Código Produto");
        modeloProdutos.addColumn("Nome");
        modeloProdutos.addColumn("Descrição");
        modeloProdutos.addColumn("Preço");

        JScrollPane scrollProdutos = new JScrollPane(produtosReserva);
        scrollProdutos.setBounds(10, 440, 960, 220);
        produtosReserva.setShowGrid(true);
        produtosReserva.setGridColor(Color.BLACK);
        getContentPane().add(scrollProdutos);

        // --- RODAPÉ ---
        btnVoltar.setBounds(10, 680, 150, 35);
        getContentPane().add(btnVoltar);

        carregarTabela();
        eventos();
    }

    private void carregarTabela() {
        try {
            modeloReservas.setRowCount(0);

            ArrayList<ReservaModel> lista = control.listarTodasReservas();

            for (ReservaModel reserva : lista) {
                modeloReservas.addRow(new Object[]{
                        reserva.getIdReserva(),
                        reserva.getNomeCliente() != null ? reserva.getNomeCliente() : "Desconhecido",
                        "R$ " + String.format("%.2f", reserva.getTotalReserva()),
                        reserva.getMetodoPagamento() != null ? reserva.getMetodoPagamento() : "N/A",
                        reserva.getDataReserva() != null ? reserva.getDataReserva() : "Sem data",
                        reserva.getDataValidade() != null ? reserva.getDataValidade() : "Sem limite",
                        reserva.getStatusReserva() != null ? reserva.getStatusReserva() : "Indefinido"
                });
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + erro.getMessage());
        }
    }

    private void carregarProdutosDaReserva(int idReserva) {
        try {
            modeloProdutos.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void eventos() {
        tabelaReservas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabelaReservas.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    int idReservaClicada = (int) modeloReservas.getValueAt(linhaSelecionada, 0);
                    carregarProdutosDaReserva(idReservaClicada);
                }
            }
        });

        btnBuscaId.addActionListener(e -> {
            String idDigitado = inputBuscaId.getText();
            try {
                int idBusca = Integer.parseInt(idDigitado);
                ReservaModel reserva = control.buscarReservaGeralPorId(idBusca);

                modeloReservas.setRowCount(0);
                modeloProdutos.setRowCount(0);

                if (reserva != null) {
                    modeloReservas.addRow(new Object[]{
                            reserva.getIdReserva(),
                            reserva.getNomeCliente() != null ? reserva.getNomeCliente() : "Desconhecido",
                            "R$ " + String.format("%.2f", reserva.getTotalReserva()),
                            reserva.getMetodoPagamento() != null ? reserva.getMetodoPagamento() : "N/A",
                            reserva.getDataReserva() != null ? reserva.getDataReserva() : "Sem data",
                            reserva.getDataValidade() != null ? reserva.getDataValidade() : "Sem limite",
                            reserva.getStatusReserva() != null ? reserva.getStatusReserva() : "Indefinido"
                    });
                }else {
                    JOptionPane.showMessageDialog(this, "Nenhuma reserva encontrada com o ID " + idBusca, "Aviso", JOptionPane.WARNING_MESSAGE);
                    carregarTabela();
                }
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(this, "Digite um ID numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscaNome.addActionListener(e -> {
            String nomeDigitado = inputBuscaNome.getText();
            if (nomeDigitado.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um nome para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                ArrayList<ReservaModel> lista = control.buscarReservaPorNomeCliente(nomeDigitado);

                modeloReservas.setRowCount(0);
                modeloProdutos.setRowCount(0);

                if (!lista.isEmpty()) {
                    for (ReservaModel reserva : lista) {
                        modeloReservas.addRow(new Object[]{
                                reserva.getIdReserva(),
                                reserva.getNomeCliente(),
                                "R$ " + String.format("%.2f", reserva.getTotalReserva()),
                                reserva.getMetodoPagamento(),
                                reserva.getDataReserva(),
                                reserva.getDataValidade(),
                                reserva.getStatusReserva()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhuma reserva encontrada para o cliente: " + nomeDigitado, "Aviso", JOptionPane.WARNING_MESSAGE);
                    carregarTabela();
                }
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnConcluir.addActionListener(e -> {
            int linhaSelecionada = tabelaReservas.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int idReserva = (int) modeloReservas.getValueAt(linhaSelecionada, 0);
                String statusAtual = (String) modeloReservas.getValueAt(linhaSelecionada, 6); // Agora é a coluna 6 (adicionamos nome)

                if (statusAtual.equalsIgnoreCase("Concluída") || statusAtual.equalsIgnoreCase("Cancelada")) {
                    JOptionPane.showMessageDialog(this, "Este pedido já está fechado e não pode ser alterado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja marcar o pedido #" + idReserva + " como CONCLUÍDO?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    control.atualizarStatusReserva(idReserva, "Concluída");
                    JOptionPane.showMessageDialog(this, "Status alterado para Concluído com sucesso!");
                    carregarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            int linhaSelecionada = tabelaReservas.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int idReserva = (int) modeloReservas.getValueAt(linhaSelecionada, 0);
                String statusAtual = (String) modeloReservas.getValueAt(linhaSelecionada, 6); // Agora é a coluna 6

                if (statusAtual.equalsIgnoreCase("Concluída") || statusAtual.equalsIgnoreCase("Cancelada")) {
                    JOptionPane.showMessageDialog(this, "Este pedido já está fechado e não pode ser alterado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirmacao = JOptionPane.showConfirmDialog(this, "Atenção: Cancelar o pedido #" + idReserva + " fará os produtos voltarem ao estoque.\nConfirma o cancelamento?", "Cancelar Pedido", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    control.atualizarStatusReserva(idReserva, "Cancelada");
                    JOptionPane.showMessageDialog(this, "Pedido cancelado e itens devolvidos ao estoque!");
                    carregarTabela();
                    modeloProdutos.setRowCount(0); // Limpa a tabela de baixo pois o pedido foi cancelado
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnRecarregar.addActionListener(e -> {
            inputBuscaId.setText("");
            inputBuscaNome.setText("");
            modeloProdutos.setRowCount(0);
            carregarTabela();
        });

        btnVoltar.addActionListener(e -> {
            new FuncionarioHomeView(usuarioLogado).setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        LoginUsuarioModel usuarioFake = new LoginUsuarioModel();
        usuarioFake.setIdUsuario(1);
        usuarioFake.setNome("Funcionario Padrão");
        usuarioFake.setTipoUsuario("FUNCIONARIO");

        new AtenderPedidosView(usuarioFake).setVisible(true);
    }
}