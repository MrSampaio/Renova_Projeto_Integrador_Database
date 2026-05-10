package Pck_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {
    private static String sDriver;
    private String sServidor;
    private String sBase;

    private static String connStr;

    static Connection conn;

    private static final String URL = "jdbc:mysql://localhost:3306/bd_agenda_mvc";
    public static final String login = "root";
    public static final String senha = "Root1234";

    public ConexaoMySql(){
        this.sDriver = "com.mysql.cj.jdbc.Driver";
        this.sServidor = "localhost";
        this.connStr = "jdbc:mysql://localhost:3306/renova";
    }

    public static Connection getConn(String login, String senha) throws SQLException {
        conn = null;
        try{
            Class.forName(sDriver);
            conn = DriverManager.getConnection(connStr, login, senha);
            System.out.println("Conectado!!");
            return conn;

        } catch (ClassNotFoundException e){
            String error = "Driver nao encontrado";
            throw new SQLException(error, e);

        } catch (SQLException e){
            String error = "Erro ao obter conexao";
            throw new SQLException(error, e);
        }
    }

    public void desconectar(){
        try{
            if(conn != null){
                conn.close();
                System.out.println("Banco desconectado.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

