package Pck_Control;

import Pck_Model.LoginUsuarioModel;
import Pck_Persistencia.LoginUsuarioPersistencia;

public class LoginUsuarioControl {


    public void LoginUsuarioControl(String email, String senha) throws Exception{

        LoginUsuarioModel usuario = new LoginUsuarioModel();

        usuario.setEmail(email);
        usuario.setSenha(senha);

        LoginUsuarioPersistencia loginUsuarioPersistencia = new LoginUsuarioPersistencia();

    }

}
