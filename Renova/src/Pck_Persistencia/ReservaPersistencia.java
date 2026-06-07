package Pck_Persistencia;

import Pck_Model.ProdutoModel;
import Pck_DAO.ConexaoMySql;
import Pck_Model.ReservaModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    public ArrayList<ReservaModel> listarReservasPorUsuario(int idUsuario){

        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;

        String sql = "{CALL PROC_LISTAR_RESERVAS_POR_USUARIO(?)}";

        // Formatadores para deixar a data no padrão brasileiro
        SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatoDataSimples = new SimpleDateFormat("dd/MM/yyyy");

        try{
            ConexaoMySql conexaoBD = new ConexaoMySql();

            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);

            ArrayList<ReservaModel> lista = new ArrayList<>();

            stmt.setInt(1, idUsuario);
            resultSet= stmt.executeQuery();

            while(resultSet.next()){

                ReservaModel reserva = new ReservaModel();

                reserva.setIdReserva(resultSet.getInt("id_reserva"));
                reserva.setTotalReserva(resultSet.getDouble("total_reserva"));
                reserva.setMetodoPagamento(resultSet.getString("metodo_pagamento"));

                java.sql.Timestamp tsReserva = resultSet.getTimestamp("data_reserva");
                if (tsReserva != null) {
                    reserva.setDataReserva(formatoDataHora.format(tsReserva));
                }

                // Para DATE (data limite de validade)
                java.sql.Date dtValidade = resultSet.getDate("data_validade");
                if (dtValidade != null) {
                    reserva.setDataValidade(formatoDataSimples.format(dtValidade));
                }

                reserva.setStatusReserva(resultSet.getString("status_reserva"));
                lista.add(reserva);
            }

            if(!lista.isEmpty()){
                System.out.println("Reservas listadas com sucesso.");
            } else{
                System.out.println("Erro ao listar reservas.");
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco de dados ao listar reservas: " + e.getMessage());

        }finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    new ConexaoMySql().desconectar();
                }
                if(resultSet != null){
                    resultSet.close();
                }

            } catch (SQLException e) {
                System.out.println("Falha ao fechar a conexão com o banco - " + e.getMessage());
            }
        }
    }

    public ArrayList<ProdutoModel> listarProdutosPorReserva(int idReserva) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        String sql = "{CALL PROC_LISTAR_PRODUTOS_POR_RESERVA(?)}";
        ArrayList<ProdutoModel> lista = new ArrayList<>();

        try {
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);
            stmt.setInt(1, idReserva);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                ProdutoModel p = new ProdutoModel();
                p.setIdProduto(resultSet.getInt("id_produto"));
                p.setNomeProduto(resultSet.getString("nome_produto"));
                p.setDescricao(resultSet.getString("descricao"));
                p.setPreco(resultSet.getDouble("preco"));
                lista.add(p);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos da reserva: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public ReservaModel buscarReservaPorId(int idReserva, int idUsuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;

        String sql = "{CALL PROC_BUSCAR_RESERVA_POR_ID(?, ?)}";

        java.text.SimpleDateFormat formatoDataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.text.SimpleDateFormat formatoDataSimples = new java.text.SimpleDateFormat("dd/MM/yyyy");

        try {
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);

            stmt.setInt(1, idReserva);
            stmt.setInt(2, idUsuario);

            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                ReservaModel reserva = new ReservaModel();
                reserva.setIdReserva(resultSet.getInt("id_reserva"));
                reserva.setTotalReserva(resultSet.getDouble("total_reserva"));
                reserva.setMetodoPagamento(resultSet.getString("metodo_pagamento"));

                java.sql.Timestamp tsReserva = resultSet.getTimestamp("data_reserva");
                if (tsReserva != null) {
                    reserva.setDataReserva(formatoDataHora.format(tsReserva));
                }

                java.sql.Date dtValidade = resultSet.getDate("data_validade");
                if (dtValidade != null) {
                    reserva.setDataValidade(formatoDataSimples.format(dtValidade));
                }

                reserva.setStatusReserva(resultSet.getString("status_reserva"));
                return reserva;

            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar reserva por ID: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Falha ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // LISTAR TODAS AS RESERVAS
    public ArrayList<ReservaModel> listarTodasReservas() {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        String sql = "{CALL PROC_LISTAR_TODAS_RESERVAS()}";

        java.text.SimpleDateFormat formatoDataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.text.SimpleDateFormat formatoDataSimples = new java.text.SimpleDateFormat("dd/MM/yyyy");

        try {
            ConexaoMySql conexaoBD = new ConexaoMySql();
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);
            ArrayList<ReservaModel> lista = new ArrayList<>();
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                ReservaModel reserva = new ReservaModel();
                reserva.setIdReserva(resultSet.getInt("id_reserva"));
                reserva.setNomeCliente(resultSet.getString("nome_cliente")); // Campo novo!
                reserva.setTotalReserva(resultSet.getDouble("total_reserva"));
                reserva.setMetodoPagamento(resultSet.getString("metodo_pagamento"));

                java.sql.Timestamp tsReserva = resultSet.getTimestamp("data_reserva");
                if (tsReserva != null) reserva.setDataReserva(formatoDataHora.format(tsReserva));

                java.sql.Date dtValidade = resultSet.getDate("data_validade");
                if (dtValidade != null) reserva.setDataValidade(formatoDataSimples.format(dtValidade));

                reserva.setStatusReserva(resultSet.getString("status_reserva"));
                lista.add(reserva);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as reservas: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // BUSCAR RESERVA GERAL POR ID
    public ReservaModel buscarReservaGeralPorId(int idReserva) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        String sql = "{CALL PROC_BUSCAR_RESERVA_GERAL_POR_ID(?)}";

        java.text.SimpleDateFormat formatoDataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.text.SimpleDateFormat formatoDataSimples = new java.text.SimpleDateFormat("dd/MM/yyyy");

        try {
            ConexaoMySql conexaoBD = new ConexaoMySql();
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);
            stmt.setInt(1, idReserva);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                ReservaModel reserva = new ReservaModel();
                reserva.setIdReserva(resultSet.getInt("id_reserva"));
                reserva.setNomeCliente(resultSet.getString("nome_cliente")); // Campo novo!
                reserva.setTotalReserva(resultSet.getDouble("total_reserva"));
                reserva.setMetodoPagamento(resultSet.getString("metodo_pagamento"));

                java.sql.Timestamp tsReserva = resultSet.getTimestamp("data_reserva");
                if (tsReserva != null) reserva.setDataReserva(formatoDataHora.format(tsReserva));

                java.sql.Date dtValidade = resultSet.getDate("data_validade");
                if (dtValidade != null) reserva.setDataValidade(formatoDataSimples.format(dtValidade));

                reserva.setStatusReserva(resultSet.getString("status_reserva"));
                return reserva;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar reserva geral por ID: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // BUSCAR RESERVAS POR NOME DO CLIENTE
    public ArrayList<ReservaModel> buscarReservaPorNomeCliente(String nomeCliente) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        String sql = "{CALL PROC_BUSCAR_RESERVA_POR_NOME_CLIENTE(?)}";

        java.text.SimpleDateFormat formatoDataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.text.SimpleDateFormat formatoDataSimples = new java.text.SimpleDateFormat("dd/MM/yyyy");

        try {
            ConexaoMySql conexaoBD = new ConexaoMySql();
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);
            stmt.setString(1, nomeCliente);
            ArrayList<ReservaModel> lista = new ArrayList<>();
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                ReservaModel reserva = new ReservaModel();
                reserva.setIdReserva(resultSet.getInt("id_reserva"));
                reserva.setNomeCliente(resultSet.getString("nome_cliente")); // Campo novo!
                reserva.setTotalReserva(resultSet.getDouble("total_reserva"));
                reserva.setMetodoPagamento(resultSet.getString("metodo_pagamento"));

                java.sql.Timestamp tsReserva = resultSet.getTimestamp("data_reserva");
                if (tsReserva != null) reserva.setDataReserva(formatoDataHora.format(tsReserva));

                java.sql.Date dtValidade = resultSet.getDate("data_validade");
                if (dtValidade != null) reserva.setDataValidade(formatoDataSimples.format(dtValidade));

                reserva.setStatusReserva(resultSet.getString("status_reserva"));
                lista.add(reserva);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar reservas por nome: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // 4. ATUALIZAR STATUS DA RESERVA
    public void atualizarStatusReserva(int idReserva, String novoStatus) {
        Connection conn = null;
        CallableStatement stmt = null;
        String sql = "{CALL PROC_ATUALIZAR_STATUS_RESERVA(?, ?)}";

        try {
            ConexaoMySql conexaoBD = new ConexaoMySql();
            conn = ConexaoMySql.getConn(ConexaoMySql.login, ConexaoMySql.senha);
            stmt = conn.prepareCall(sql);
            stmt.setInt(1, idReserva);
            stmt.setString(2, novoStatus);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da reserva: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) new ConexaoMySql().desconectar();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
