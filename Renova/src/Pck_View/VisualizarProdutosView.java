package Pck_View;

import Pck_Model.ProdutoModel;
import Pck_Control.ProdutoControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VisualizarProdutosView extends JFrame{

    DefaultTableModel modelo = new DefaultTableModel();
    ProdutoControl control = new ProdutoControl();

    JLabel labelBuscaId = new JLabel("Pesquisar por ID:");
    JTextField inputBuscaId = new JTextField();
    JButton btnBuscaId = new JButton("Buscar ID");


    JLabel labelBuscaNome = new JLabel("Pesquisar por Nome:");
    JTextField inputBuscaNome = new JTextField();
    JButton btnBuscaNome = new JButton("Buscar Nome");


    JButton btnRecarregar = new JButton("Limpar Filtros / Recarregar Tudo");

    public VisualizarProdutosView(){
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

    }

    // carrega tabela com dados do banco
    private void carregarTabela(){
        try{
            modelo.setRowCount(0);
            ArrayList<ProdutoModel> lista = control.listarProdutos();
            for(ProdutoModel p:lista){
                modelo.addRow(new Object[]{
                        p.getIdProduto(),
                        p.getNomeProduto(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getStatus()
                });
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar tabela: " + erro.getMessage());
        }
    }

    static void main(String[] args) {
        new VisualizarProdutosView().setVisible(true);
    }

}
