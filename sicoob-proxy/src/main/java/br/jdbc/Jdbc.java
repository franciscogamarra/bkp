package br.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {

//    private static final String URL = "jdbc:db2://DB2TCRE.homologacao.com.br:50001/CRE_TMP";
//    private static final String URL = "jdbc:db2://DB2TCRE.homologacao.com.br:50001/cre_tmp:sslConnection=true;";
//    private static final String URL = "jdbc:db2://DB2T302.homologacao.com.br:50001/ADT11";
    private static final String URL = "jdbc:db2://DB2D1020.homologacao.com.br:50001/CRE_TBP";
    private static final String USER = "usrbnd";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(com.ibm.db2.jcc.DB2Driver.class.getName());
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver DB2 não encontrado!", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Conexão realizada com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
