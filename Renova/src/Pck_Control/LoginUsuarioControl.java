package Pck_Control;

import Pck_Model.UsuarioModel;
import Pck_Persistencia.LoginUsuario;

public class LoginUsuarioControl {


    public void LoginUsuarioControl(String email, String senha) throws Exception{

        UsuarioModel usuario = new UsuarioModel();

        usuario.setEmail(email);
        usuario.setSenha(senha);

        LoginUsuario loginUsuario = new LoginUsuario();

    }

}
