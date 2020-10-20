package com.lauriavictor.util;

import com.lauriavictor.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static Connection conn;
    private static final String URL = "jdbc:mysql://localhost:3306/usuarios_db";
    private static final String USUARIO = "root";
    private static final String SENHA = null;
    
    public static Connection getCon() throws ErroSistema {
        if (conn == null) {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(URL, USUARIO, SENHA);
                } catch (SQLException ex) {
                    throw new ErroSistema("Não foi possivel estabelecer uma conexão com o banco de dados.", ex);
                }
            } catch (ClassNotFoundException ex) {
                throw new ErroSistema("Driver JDBC não foi encontrado.", ex);
            }
        }
        return conn;
    }
    
    public static void fecharConexao() throws ErroSistema {
        if(conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Não foi possível fechar a conexão com o banco de dados.", ex);
            }
        } 
    }
}
