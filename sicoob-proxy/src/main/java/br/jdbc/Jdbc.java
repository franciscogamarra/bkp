package br.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import br.utils.DevException;

public class Jdbc {

    private static final String URL = "jdbc:db2://DB2D1020.homologacao.com.br:50001/CRE_TBP";
    private static final String USER = "usrbnd";
    private static final String PASSWORD = "123456";
    
    private final Connection con;
    
    private Jdbc(Connection con) {
        this.con = con;
    }
    
    /**
     * Executa um SELECT e imprime TODAS as colunas dinamicamente.
     */
    public void print(String sql) {
        System.out.println("\n>> Executando SQL:\n" + sql + "\n");

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            
            // Cabeçalho das colunas
            for (int i = 1; i <= colCount; i++) {
                System.out.print(meta.getColumnLabel(i) + "\t");
            }
            System.out.println();
            System.out.println("------------------------------------------------------------");
            
            // Linhas do resultado
            while (rs.next()) {
                for (int i = 1; i <= colCount; i++) {
                    Object val = rs.getObject(i);
                    System.out.print((val != null ? val : "NULL") + "\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            throw DevException.build(e);
        }
    }

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

            new Jdbc(conn).print(
                "select * from bnd.prePropostaBndes where numPropostaCredito = 25778294"
            );
            
            new Jdbc(conn).print(
            	"select numPropostaCredito, count(*) as qtd"
            	+ " from bnd.prePropostaBndes"
            	+ " where codFaseArquivoPropostaAtual = 1"
            	+ " group by numPropostaCredito"
            	+ " having count(*) > 1"
            );

        } catch (SQLException e) {
            throw DevException.build(e);
        }
    }
}
