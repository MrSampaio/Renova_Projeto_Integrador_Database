package Pck_Control;

import Pck_Model.LoginUsuarioModel;
import Pck_Persistencia.LoginUsuarioPersistencia;

public class LoginUsuarioControl {


    public boolean realizarLogin(String email, String senha) {

        // Monta o Model com os dados que vieram da View
        LoginUsuarioModel usuario = new LoginUsuarioModel();
        usuario.setEmail(email);
        usuario.setSenha(senha);

        // Instancia a Persistência
        LoginUsuarioPersistencia persistencia = new LoginUsuarioPersistencia();

        // Executa o login no banco e RETORNA o true ou false para a View
        return persistencia.login(usuario);
    }

}
