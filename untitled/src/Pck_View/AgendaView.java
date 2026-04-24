package Pck_View;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AgendaView extends JFrame {
    private static final long serialVersionUI = 1L;

    JLabel texto;
    JTextField caixaTexto;
    JButton botaoInsert;

    public AgendaView() {
        setTitle("Gabriela cu de rodela");
        setBounds(100, 100, 430, 300);

        texto = new JLabel("Gabriela libera o Murilo");
        texto.setBounds(20, 20, 100, 20);

        caixaTexto = new JTextField("Informe o tamanho do penis do Murilo");
        caixaTexto.setBounds(130, 20, 250, 20);

        botaoInsert = new JButton("Enviar");
        botaoInsert.setBounds(20, 80, 100, 20);

        getContentPane().setLayout(null);
        getContentPane().add(texto);
        getContentPane().add(caixaTexto);
        getContentPane().add(botaoInsert);


    }

    public static void main(String[] args) {
        AgendaView tela = new AgendaView();
        tela.setVisible(true);
    }

}


