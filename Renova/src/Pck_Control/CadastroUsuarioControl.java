package Pck_Control;

import Pck_Model.CadastroUsuarioModel;
import Pck_Persistencia.CadastroUsuarioPersistencia;


public class CadastroUsuarioControl {
    public void CadastroUsuarioControl(String email, String senha, String telefone, String nome, String tipoUsuario) throws Exception{
        CadastroUsuarioModel cadastroUsuario = new CadastroUsuarioModel();

        cadastroUsuario.setEmail(email);
        cadastroUsuario.setSenha(senha);
        cadastroUsuario.setTelefone(telefone);
        cadastroUsuario.setNome(nome);
        cadastroUsuario.setTipoUsuario(tipoUsuario);

        CadastroUsuarioPersistencia cadastroPersistencia = new CadastroUsuarioPersistencia();

        cadastroPersistencia.CadastroUsuario(cadastroUsuario);

    }
}
