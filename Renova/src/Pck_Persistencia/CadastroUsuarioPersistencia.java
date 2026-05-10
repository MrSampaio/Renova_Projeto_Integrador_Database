package Pck_Persistencia;

import Pck_Model.CadastroUsuarioModel;
// import java.sql.ResultSet;
import Pck_DAO.ConexaoMySql;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

// import Pck_View.SucessoCadastroView;

public class CadastroUsuarioPersistencia {

    Connection conn = null;
    CallableStatement stmt = null;
    boolean sucesso = false;

    public void CadastroUsuario(CadastroUsuarioModel cadastroUsuario) throws Exception{

        String sql = "{CALL PROC_CADASTRAR_USUARIO(?, ?, ?, ?, ?)}";

        try{
            ConexaoMySql conexaoBD = new ConexaoMySql();

            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);

            stmt = conn.prepareCall(sql);

            stmt.setString(1, cadastroUsuario.getNome());
            stmt.setString(2, cadastroUsuario.getTelefone());
            stmt.setString(3, cadastroUsuario.getEmail());
            stmt.setString(4, cadastroUsuario.getSenha());
            stmt.setString(5, cadastroUsuario.getTipoUsuario());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Novo usuario cadastrado com sucesso.");
            } else{
                System.out.println("Erro ao cadastrar usuario.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco de dados ao cadastrar usuário: " + e.getMessage());

        }finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    new ConexaoMySql().desconectar();
                }
            } catch (SQLException e) {
                System.out.println("Falha ao fechar a conexão com o banco - " + e.getMessage());
            }
        }

    }
}
