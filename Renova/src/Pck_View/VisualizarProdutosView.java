package Pck_View;

import Pck_Model.ProdutoModel;
import Pck_Control.ProdutoControl;
import Pck_Model.LoginUsuarioModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VisualizarProdutosView extends JFrame{

    LoginUsuarioModel usuarioLogado;

    DefaultTableModel modelo = new DefaultTableModel();
    ProdutoControl control = new ProdutoControl();

    JLabel labelBuscaId = new JLabel("Pesquisar por ID:");
    JTextField inputBuscaId = new JTextField();
    JButton btnBuscaId = new JButton("Buscar ID");

    JLabel labelBuscaNome = new JLabel("Pesquisar por Nome:");
    JTextField inputBuscaNome = new JTextField();
    JButton btnBuscaNome = new JButton("Buscar Nome");

    JButton btnRecarregar = new JButton("Limpar Filtros / Recarregar Tudo");

    static void main(String[] args) {

        LoginUsuarioModel usuarioFake = new LoginUsuarioModel();
        usuarioFake.setIdUsuario(1);
        usuarioFake.setNome("Administrador Teste");
        usuarioFake.setEmail("admin@renova.com");
        usuarioFake.setSenha("123456");

        // TROQUE AQUI PARA TESTAR: "FUNCIONARIO" ou "CLIENTE"
        usuarioFake.setTipoUsuario("FUNCIONARIO");

        new VisualizarProdutosView(usuarioFake).setVisible(true);
    }

    public VisualizarProdutosView(LoginUsuarioModel usuario){
        this.usuarioLogado = usuario;

        setTitle("Visualizar Produto");
        setBounds(100, 100, 800, 700);

        // define o layout absoluto
        getContentPane().setLayout(null);

        // --- TABELA E SCROLLPANE ---
        JTable tabela = new JTable(modelo);
        modelo.addColumn("Código (ID)");
        modelo.addColumn("Nome");
        modelo.addColumn("Descrição");
        modelo.addColumn("Preço");
        modelo.addColumn("Status");

        JScrollPane painel = new JScrollPane(tabela);
        painel.setBounds(10, 50, 760, 350);
        getContentPane().add(painel);

        labelBuscaId.setBounds(20, 430, 120, 30);
        getContentPane().add(labelBuscaId);

        inputBuscaId.setBounds(140, 430, 150, 30);
        getContentPane().add(inputBuscaId);

        btnBuscaId.setBounds(300, 430, 120, 30);
        getContentPane().add(btnBuscaId);


        labelBuscaNome.setBounds(20, 480, 140, 30);
        getContentPane().add(labelBuscaNome);

        inputBuscaNome.setBounds(160, 480, 250, 30);
        getContentPane().add(inputBuscaNome);

        btnBuscaNome.setBounds(420, 480, 120, 30);
        getContentPane().add(btnBuscaNome);

        btnRecarregar.setBounds(20, 550, 250, 35);
        getContentPane().add(btnRecarregar);

        carregarTabela();
        eventos();

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

    private void eventos() {

        btnBuscaId.addActionListener(e -> {
            String idDigitado = inputBuscaId.getText();

            try{
                int idFinal = Integer.parseInt(idDigitado);

                ProdutoModel produtoEncontrado = control.listarProdutosID(idFinal);

                if (produtoEncontrado != null) {

                    modelo.setRowCount(0);

                    if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("FUNCIONARIO")) {
                        modelo.addRow(new Object[]{
                                produtoEncontrado.getIdProduto(),
                                produtoEncontrado.getNomeProduto(),
                                produtoEncontrado.getDescricao(),
                                produtoEncontrado.getPreco(),
                                produtoEncontrado.getStatus()
                        });
                    }

                    else if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("CLIENTE")) {
                        if (!produtoEncontrado.getStatus().equalsIgnoreCase("Reservado")
                                && !produtoEncontrado.getStatus().equalsIgnoreCase("Vendido")) {
                            modelo.addRow(new Object[]{
                                    produtoEncontrado.getIdProduto(),
                                    produtoEncontrado.getNomeProduto(),
                                    produtoEncontrado.getDescricao(),
                                    produtoEncontrado.getPreco(),
                                    produtoEncontrado.getStatus()
                            });
                        } else {
                            JOptionPane.showMessageDialog(this, "Este produto não está mais disponível.");
                            // carregarTabela();
                            inputBuscaId.setText("");
                        }
                    }

                } else{

                    JOptionPane.showMessageDialog(this, "Nenhum produto encontrado com o ID " + idFinal, "Aviso", JOptionPane.WARNING_MESSAGE);
                    inputBuscaId.setText("");
                    // carregarTabela();
                }

            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(this, "Digite um valor numérico e inteiro para realizar a busca por ID.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(this, "Erro inesperado ao buscar: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRecarregar.addActionListener(e ->{
            inputBuscaId.setText("");
            inputBuscaNome.setText("");
            carregarTabela();
        });
    }
}
