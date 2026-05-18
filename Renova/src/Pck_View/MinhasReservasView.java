package Pck_View;

import Pck_Control.ProdutoControl;
import Pck_Control.ReservaControl;
import Pck_Model.LoginUsuarioModel;
import Pck_Model.ProdutoModel;
import Pck_Model.ReservaModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MinhasReservasView extends JFrame{
    LoginUsuarioModel usuarioLogado;

    DefaultTableModel modelo = new DefaultTableModel();
    ReservaControl control = new ReservaControl();

    public MinhasReservasView(LoginUsuarioModel usuarioLogado){
        this.usuarioLogado = usuarioLogado;

        setTitle("Meus pedidos");
        setBounds(300, 0, 1000, 850);

        // define o layout absoluto
        getContentPane().setLayout(null);

        JLabel tituloPagina = new JLabel("Visualize seus pedidos");
        tituloPagina.setFont(new Font("Segoe UI", Font.BOLD, 25));

        tituloPagina.setBounds(10, 10, 300, 40);
        getContentPane().add(tituloPagina);

        JTable tabela = new JTable(modelo);
        modelo.addColumn("Código Reserva");
        modelo.addColumn("Total Reserva");
        modelo.addColumn("Método de pagamento");
        modelo.addColumn("Data da reserva");
        modelo.addColumn("Data limite de retirada");
        modelo.addColumn("Status da reserva");

        JScrollPane painel = new JScrollPane(tabela);
        painel.setBounds(10, 70, 900, 150);
        getContentPane().add(painel);

    }

    private void carregarTabela(){

        try {
            modelo.setRowCount(0);// Limpa a tabela
            int idUsuario = usuarioLogado.getIdUsuario();
            ArrayList<ReservaModel> lista = control.listarReservas(idUsuario);

            for(ReservaModel p : lista) {

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
