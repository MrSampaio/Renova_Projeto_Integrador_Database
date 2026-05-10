package Pck_View;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

public class IndexView extends JFrame{
    public IndexView(){
        setTitle("Bem-vindo(a) ao Renova!");
        setBounds(100, 100, 800, 700);

        // o GridBagLayout centraliza os elementos automaticamente
        getContentPane().setLayout(new GridBagLayout());

        // objeto que adicioan as restrições pra controlar a posição dos elementos
        GridBagConstraints gbc = new GridBagConstraints();

        // faz com que todos os elementos fiquem na mesma coluna, nesse caso a 0
        gbc.gridx = 0;

        // aplica margem
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel tituloPagina = new JLabel("Como deseja prosseguir?");
        gbc.gridy = 0; // adiciona na primeira linha
        getContentPane().add(tituloPagina, gbc);

        JButton funcionario = new JButton("Sou funcionário");
        funcionario.setPreferredSize(new Dimension(400, 100));
        gbc.gridy = 1; // adiciona na segunda linha
        getContentPane().add(funcionario, gbc);

        JButton cliente = new JButton("Sou cliente");
        cliente.setPreferredSize(new Dimension(400, 100));
        gbc.gridy = 2; // adiciona na terceira linha
        getContentPane().add(cliente, gbc);

    }
}