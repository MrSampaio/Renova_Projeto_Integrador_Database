package Pck_View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Pck_Control.AgendaControl;

public class AgendaView extends JFrame implements ActionListener {
    private static final long serialVersionUI = 1L;

    JLabel labelTituloTarefa;
    JLabel labelDescricaoTarefa;
    JLabel labelDuracaoDias;

    JLabel tituloTarefaAdicionada;
    JLabel labelTarefaAdicionada;

    JLabel tituloDescricaoAdicionada;
    JLabel labelDescricaoAdicionada;

    JLabel tituloDuracaoDiasAdicionado;
    JLabel labelDuracaoDiasAdicionado;

    JLabel mensagem;

    JTextField caixaTexto;
    JTextField caixaDescricaoTarefa;
    JTextField caixaDuracaoDias;

    JButton botaoInsert;

    AgendaControl agendaControl = new AgendaControl();

    public AgendaView() {
        setTitle("Adicionar tarefas na lista");
        setBounds(100, 100, 600, 450);

        labelTituloTarefa = new JLabel("Título da tarefa");
        labelTituloTarefa.setBounds(20, 20, 100, 20);

        caixaTexto = new JTextField("");
        caixaTexto.setBounds(120, 20, 250, 20);

        labelDescricaoTarefa = new JLabel("Descricao da tarefa");
        labelDescricaoTarefa.setBounds(20, 50, 150, 20);

        caixaDescricaoTarefa = new JTextField("");
        caixaDescricaoTarefa.setBounds(140, 50, 250, 20);

        labelDuracaoDias = new JLabel("Dias de duracao da tarefa");
        labelDuracaoDias.setBounds(20, 80, 150, 20);

        caixaDuracaoDias = new JTextField("");
        caixaDuracaoDias.setBounds(180, 80, 250, 20);

        botaoInsert = new JButton("Enviar");
        botaoInsert.setBounds(20, 120, 100, 20);


        mensagem = new JLabel("");
        mensagem.setBounds(20, 150, 250, 20);

        tituloTarefaAdicionada = new JLabel("");
        tituloTarefaAdicionada.setBounds(20, 170, 250, 20);

        labelTarefaAdicionada = new JLabel("");
        labelTarefaAdicionada.setBounds(100, 170, 250, 20);

        tituloDescricaoAdicionada = new JLabel("");
        tituloDescricaoAdicionada.setBounds(20, 190, 250, 20);

        labelDescricaoAdicionada = new JLabel("");
        labelDescricaoAdicionada.setBounds(140, 190, 250, 20);

        tituloDuracaoDiasAdicionado = new JLabel("");
        tituloDuracaoDiasAdicionado.setBounds(20, 210, 250, 20);

        labelDuracaoDiasAdicionado = new JLabel("");
        labelDuracaoDiasAdicionado.setBounds(130, 210, 250, 20);

        getContentPane().setLayout(null);
        getContentPane().add(labelTituloTarefa);
        getContentPane().add(labelDescricaoTarefa);
        getContentPane().add(labelDuracaoDias);
        getContentPane().add(caixaTexto);
        getContentPane().add(botaoInsert);
        getContentPane().add(caixaDescricaoTarefa);
        getContentPane().add(caixaDuracaoDias);
        getContentPane().add(tituloTarefaAdicionada);
        getContentPane().add(labelTarefaAdicionada);
        getContentPane().add(tituloDescricaoAdicionada);
        getContentPane().add(labelDescricaoAdicionada);
        getContentPane().add(tituloDuracaoDiasAdicionado);
        getContentPane().add(labelDuracaoDiasAdicionado);
        getContentPane().add(mensagem);


        botaoInsert.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == botaoInsert){
           // agendaControl.insertAgenda(caixaTexto.getText());

            if((!caixaTexto.getText().isEmpty()) && (!caixaDescricaoTarefa.getText().isEmpty()) && (!caixaDuracaoDias.getText().isEmpty())) {
                mensagem.setText("Tarefa adicionada com sucesso!");

                tituloTarefaAdicionada.setText("Título tarefa: ");
                labelTarefaAdicionada.setText(caixaTexto.getText());

                tituloDescricaoAdicionada.setText("Descricao da tarefa: ");
                labelDescricaoAdicionada.setText(caixaDescricaoTarefa.getText());

                tituloDuracaoDiasAdicionado.setText("Dias de duracao: ");
                labelDuracaoDiasAdicionado.setText(caixaDuracaoDias.getText());
            } else{
                mensagem.setText("Erro ao adicionar tarefa: preencha todos os campos.");
            }
        }
    }

    public static void main(String[] args) {
        AgendaView tela = new AgendaView();
        tela.setVisible(true);
    }

}


