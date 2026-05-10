package Pck_View;
import javax.swing.JFrame;

import Pck_Model.LoginUsuarioModel;

public class ClienteHomeView extends JFrame{
    public ClienteHomeView(LoginUsuarioModel usuario){
        setTitle("Home cliente" + usuario.getNome());
        setBounds(100, 100, 800, 700);
    }
}
