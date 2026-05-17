package Pck_View;

import Pck_Control.ProdutoControl;
import Pck_Model.LoginUsuarioModel;
import Pck_Model.ProdutoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class RealizarPedidoView extends JFrame{

    LoginUsuarioModel usuarioLogado;

    DefaultTableModel modelo = new DefaultTableModel();
    ProdutoControl control = new ProdutoControl();

    DefaultTableModel modeloSelecionados = new DefaultTableModel();

    ArrayList<ProdutoModel> carrinho = new ArrayList<>();

    JLabel labelBuscaId = new JLabel("Pesquisar por ID:");
    JTextField inputBuscaId = new JTextField();
    JButton btnBuscaId = new JButton("Buscar ID");

    JLabel labelBuscaNome = new JLabel("Pesquisar por Nome:");
    JTextField inputBuscaNome = new JTextField();
    JButton btnBuscaNome = new JButton("Buscar Nome");

    JLabel tituloCarrinho = new JLabel("Carrinho");
    JLabel totalCarrinho = new JLabel("R$00,00");

    JButton btnRecarregar = new JButton("Limpar Filtros / Recarregar Tudo");
    JButton btnVoltar = new JButton("Voltar");
    JButton btnAdicionar = new JButton("Adicionar item");
    JButton btnRemover = new JButton("Remover item");

    // GridBagConstraints gbc = new GridBagConstraints();

    public RealizarPedidoView(LoginUsuarioModel usuario){
        this.usuarioLogado = usuario;

        setTitle("Realizar novo pedido");
        setBounds(300, 0, 1000, 850);

        // define o layout absoluto
        getContentPane().setLayout(null);

        JLabel tituloPagina = new JLabel("Realize sua reserva!");
        tituloPagina.setFont(new Font("Segoe UI", Font.BOLD, 25));

        tituloPagina.setBounds(10, 10, 300, 40);
        getContentPane().add(tituloPagina);

        JLabel subtituloPagina = new JLabel("Pesquise e selecione o produto desejado e clique no botão 'adicionar' para adicionar o item ao carrinho.");
        subtituloPagina.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtituloPagina.setForeground(Color.BLUE);
        subtituloPagina.setBounds(10, 35, 800, 40);
        getContentPane().add(subtituloPagina);

        // --- TABELA PRODUTOS E SCROLLPANE ---
        JTable tabela = new JTable(modelo);
        modelo.addColumn("Código (ID)");
        modelo.addColumn("Nome");
        modelo.addColumn("Descrição");
        modelo.addColumn("Preço");
        modelo.addColumn("Status");

        JScrollPane painel = new JScrollPane(tabela);
        painel.setBounds(10, 70, 900, 150);
        getContentPane().add(painel);

        labelBuscaId.setBounds(20, 240, 120, 30);
        //getContentPane().add(labelBuscaId);

        inputBuscaId.setBounds(140, 240, 150, 30);
        //getContentPane().add(inputBuscaId);

        btnBuscaId.setBounds(300, 240, 120, 30);
        //getContentPane().add(btnBuscaId);

        labelBuscaNome.setBounds(20, 240, 140, 30);
        getContentPane().add(labelBuscaNome);

        inputBuscaNome.setBounds(160, 240, 250, 35);
        getContentPane().add(inputBuscaNome);

        btnAdicionar.setBounds(430, 240, 250,35);
        btnAdicionar.setForeground(Color.BLUE);
        getContentPane().add(btnAdicionar);

        btnRecarregar.setBounds(20, 290, 250, 35);
        getContentPane().add(btnRecarregar);

        btnBuscaNome.setBounds(420, 290, 120, 30);
        //getContentPane().add(btnBuscaNome);

        modeloSelecionados.addColumn("Código (ID)");
        modeloSelecionados.addColumn("Nome");
        modeloSelecionados.addColumn("Descrição");
        modeloSelecionados.addColumn("Preço");
        modeloSelecionados.addColumn("Status");

        JTable produtosSelecionadosTabela = new JTable(modeloSelecionados);
        JScrollPane scrollSelecionados = new JScrollPane(produtosSelecionadosTabela);

        tituloCarrinho.setBounds(10, 430, 300, 100);
        tituloCarrinho.setFont(new Font("Segoe UI", Font.BOLD, 20));
        getContentPane().add(tituloCarrinho);

        totalCarrinho.setBounds(10, 450, 300, 100);
        totalCarrinho.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        totalCarrinho.setForeground(Color.GREEN);
        getContentPane().add(totalCarrinho);

        scrollSelecionados.setBounds(10, 500, 900, 150);
        getContentPane().add(scrollSelecionados);

        btnRemover.setBounds(20, 660, 120, 35);
        btnRemover.setForeground(Color.RED);
        getContentPane().add(btnRemover);

        btnVoltar.setBounds(20, 700, 250, 35);
        getContentPane().add(btnVoltar);

        carregarTabela();
        eventos();

        btnAdicionar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();

            if (linhaSelecionada >= 0) {
                int idProduto = (int) modelo.getValueAt(linhaSelecionada, 0);

                boolean jaExiste = carrinho.stream().anyMatch(p -> p.getIdProduto() == idProduto);

                if (jaExiste) {
                    JOptionPane.showMessageDialog(this, "Esta peça já está no seu pedido!");
                    return;
                }

                try{
                    ProdutoModel produto = control.buscarProdutoID(idProduto);

                    carrinho.add(produto);
                    atualizarTabelaPedido();

                } catch (Exception erro){
                    JOptionPane.showMessageDialog(null, erro);
                }


            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto no catálogo primeiro.");
            }
        });
        btnRemover.addActionListener(e -> {
            int linhaSelecionada = produtosSelecionadosTabela.getSelectedRow();

            if (linhaSelecionada >= 0) {
                carrinho.remove(linhaSelecionada);
                atualizarTabelaPedido();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item no seu pedido para remover.");
            }
        });
    }

    private void atualizarTabelaDinamicaNome() {
        String pesquisa = inputBuscaNome.getText();

        // caso a pesquisa esteja vazia, recarrega a tabela original
        if (pesquisa.trim().isEmpty()) {
            carregarTabela();
            return;
        }

        modelo.setRowCount(0); // limpa a tabela superior

        try {
            ArrayList<ProdutoModel> lista = control.buscarProdutoNome(pesquisa);

            for(ProdutoModel p : lista) {

                if(p.getStatus().equalsIgnoreCase("Disponível")) {
                    modelo.addRow(new Object[]{
                            p.getIdProduto(),
                            p.getNomeProduto(),
                            p.getDescricao(),
                            p.getPreco(),
                            p.getStatus()
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Erro na busca dinâmica: " + e.getMessage());
        }
    }

    // carrega tabela com dados do banco
    private void carregarTabela(){
        try {
            modelo.setRowCount(0); // Limpa a tabela
            ArrayList<ProdutoModel> lista = control.listarProdutos();

            for(ProdutoModel p : lista) {

                // se for FUNCIONARIO, mostra TUDO
                if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("FUNCIONARIO")) {
                    modelo.addRow(new Object[]{
                            p.getIdProduto(),
                            p.getNomeProduto(),
                            p.getDescricao(),
                            p.getPreco(),
                            p.getStatus()
                    });
                }

                // se for CLIENTE, oculta os reservados e vendidos (mostra só os disponíveis)
                else if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("CLIENTE")) {

                    if(!p.getStatus().equalsIgnoreCase("Reservado") && !p.getStatus().equalsIgnoreCase("Vendido")) {
                        modelo.addRow(new Object[]{
                                p.getIdProduto(),
                                p.getNomeProduto(),
                                p.getDescricao(),
                                p.getPreco(),
                                p.getStatus()
                        });
                    }
                }
            }

        } catch(Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + erro.getMessage());
        }
    }

    private void eventos(){
        inputBuscaNome.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                atualizarTabelaDinamicaNome();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                atualizarTabelaDinamicaNome();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {

            }
        });

        inputBuscaId.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                atualizarTabelaDinamicaNome();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                atualizarTabelaDinamicaNome();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {

            }
        });

        btnRecarregar.addActionListener(e -> {
            inputBuscaNome.setText("");
            inputBuscaId.setText("");
            carregarTabela();
        });

        btnVoltar.addActionListener(e ->{
            new ClienteHomeView(usuarioLogado).setVisible(true);
        });

    }

    private void atualizarTabelaPedido() {
        modeloSelecionados.setRowCount(0);
        double total = 0.0;

        for (ProdutoModel p : carrinho) {
            modeloSelecionados.addRow(new Object[]{ p.getIdProduto(), p.getNomeProduto(), p.getPreco() });
            total += p.getPreco();
        }

        // labelTotalPedido.setText("Total: R$ " + total);
    }

    static void main(String[] args) {

        LoginUsuarioModel usuarioFake = new LoginUsuarioModel();
        usuarioFake.setIdUsuario(1);
        usuarioFake.setNome("Administrador Teste");
        usuarioFake.setEmail("admin@renova.com");
        usuarioFake.setSenha("123456");

        // TROQUE AQUI PARA TESTAR: "FUNCIONARIO" ou "CLIENTE"
        usuarioFake.setTipoUsuario("CLIENTE");

        new RealizarPedidoView(usuarioFake).setVisible(true);
    }
}
