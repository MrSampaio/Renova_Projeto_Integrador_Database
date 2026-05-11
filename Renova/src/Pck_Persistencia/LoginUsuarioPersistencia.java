package Pck_Persistencia;

import Pck_DAO.ConexaoMySql;
import Pck_Model.LoginUsuarioModel;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoginUsuarioPersistencia{

    public LoginUsuarioModel login(LoginUsuarioModel loginUsuario){
        String sql = "{CALL PROC_LOGIN_USUARIO(?, ?)}";
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet result = null;

        try{
            ConexaoMySql conexaoBD = new ConexaoMySql();
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);

            stmt.setString(1, loginUsuario.getEmail());
            stmt.setString(2, loginUsuario.getSenha());

            result = stmt.executeQuery();

            if(result.next()){
                try{
                    loginUsuario.setIdUsuario(result.getInt("id_usuario"));
                    loginUsuario.setNome(result.getString("nome"));
                    loginUsuario.setTipoUsuario(result.getString("tipo_usuario"));

                    System.out.println("Login feito.");

                    return loginUsuario;

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao atribuir valores ao objeto usuário após login: " + e);
                }

            } else{
                System.out.println("Erro ao logar.");
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados no login: " + e.getMessage());
        } finally {
            try {
                if (result != null) result.close();
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Aviso: Falha ao fechar conexão - " + e.getMessage());
            }
        }

    }
}
