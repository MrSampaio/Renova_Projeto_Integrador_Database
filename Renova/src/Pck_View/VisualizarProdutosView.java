package Pck_View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VisualizarProdutosView extends JFrame{
    public VisualizarProdutosView(){
        setTitle("Visualizar Produto");
        setBounds(100, 100, 800, 700);

        DefaultTableModel modelo = new DefaultTableModel(); // modelo da tabela
        JTable tabela = new JTable(modelo);

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Descrição");
        modelo.addColumn("Estoque");
        modelo.addColumn("Status");

        JScrollPane painel=new JScrollPane(tabela);
        painel.setBounds(20,250,800,120);
        add(painel);
    }

    static void main(String[] args) {
        new VisualizarProdutosView().setVisible(true);
    }

    public void renderizarTabela(){

    }

}
