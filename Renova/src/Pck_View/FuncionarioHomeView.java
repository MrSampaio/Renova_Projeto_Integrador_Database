package Pck_View;

import Pck_Model.LoginUsuarioModel;
import javax.swing.JFrame;

public class FuncionarioHomeView extends JFrame{
    public FuncionarioHomeView(LoginUsuarioModel usuario){
        setTitle("Home Funcionario" + usuario.getNome());
        setBounds(100, 100, 800, 700);


    }
}
