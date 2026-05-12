package Pck_Control;

import Pck_Model.LoginUsuarioModel;
import Pck_Persistencia.LoginUsuarioPersistencia;

public class LoginUsuarioControl {

    public LoginUsuarioModel realizarLogin(String email, String senha) {

        LoginUsuarioModel usuario = new LoginUsuarioModel();
        usuario.setEmail(email);
        usuario.setSenha(senha);

        LoginUsuarioPersistencia persistencia = new LoginUsuarioPersistencia();

        return persistencia.login(usuario);
    }

}
