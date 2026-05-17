package Pck_Persistencia;

import Pck_Model.ProdutoModel;
import Pck_DAO.ConexaoMySql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservaPersistencia {
    public boolean finalizarPedido(int idUsuario, double total, String metodoPagamento, ArrayList<ProdutoModel> carrinho){
        Connection conn = null;
        CallableStatement stmtReserva = null;
        CallableStatement stmtItens = null;

        try {
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);

            // remove o salvamento automático
            conn.setAutoCommit(false);

            String sqlReserva = "{CALL PROC_CRIAR_RESERVA(?, ?, ?, ?)}";
            stmtReserva = conn.prepareCall(sqlReserva);

            stmtReserva.setInt(1, idUsuario);
            stmtReserva.setDouble(2, total);
            stmtReserva.setString(3, metodoPagamento);

            // diz que o 4º parâmetro é de SAÍDA (OUT) e é do tipo Inteiro
            stmtReserva.registerOutParameter(4, java.sql.Types.INTEGER);

            stmtReserva.execute();

            // pega o id que a procedure gerou
            int idReservaGerado = stmtReserva.getInt(4);

            String sqlItens = "{CALL PROC_INSERIR_ITEM_RESERVA(?, ?)}";
            stmtItens = conn.prepareCall(sqlItens);

            for (ProdutoModel produto : carrinho) {
                stmtItens.setInt(1, idReservaGerado);
                stmtItens.setInt(2, produto.getIdProduto());

                stmtItens.executeUpdate(); // roda a procedure pra todos os produtos
            }

            // caso tenha dado certo, registra no banco
            conn.commit();
            return true;

        } catch (SQLException e) {
           // caso de errado
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Erro crítico ao tentar dar Rollback: " + rollbackEx.getMessage());
            }
            throw new RuntimeException("Erro ao finalizar pedido. Compra cancelada! " + e.getMessage());

        } finally {
            try {
                if (stmtReserva != null) {
                    stmtReserva.close();
                }
                if (stmtItens != null){
                    stmtItens.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    new ConexaoMySql().desconectar();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
