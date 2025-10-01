package br.caixa;

import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBCDB2;
import gm.utils.jpa.DriverJDBCSqlServer;

public class CaixaDBs {
	
	public static final ConexaoJdbc con = ConexaoJdbc.getNew(
		"10.192.224.102:5031/CSD4", DriverJDBCDB2.getInstance(), "SLCEDB01", "SLCEDB01"
	);

	public static final ConexaoJdbc sqlServerLegado = ConexaoJdbc.getNew(
		"sql-loterias-des.database.windows.net;databaseName=lce_legado", DriverJDBCSqlServer.getInstance(), "sqlsilce", "@4587teste%"
	);

	public static final ConexaoJdbc sqlServerLegadoAdmin = ConexaoJdbc.getNew(
		"sql-loterias-des.database.windows.net;databaseName=lce_legado", DriverJDBCSqlServer.getInstance(), "sqladmin", "Girafa05"
	);

	public static final ConexaoJdbc sqlServerCarrinhoRascunho = ConexaoJdbc.getNew(
		"sql-loterias-des.database.windows.net;databaseName=Silce-carrinho-rascunho", DriverJDBCSqlServer.getInstance(), "sqlsilce", "@4587teste%"
	);

	public static final ConexaoJdbc sqlServerCarrinhoRascunhoAdmin = ConexaoJdbc.getNew(
		"sql-loterias-des.database.windows.net;databaseName=Silce-carrinho-rascunho", DriverJDBCSqlServer.getInstance(), "sqladmin", "Girafa05"
	);

	public static final ConexaoJdbc sqlLocal = ConexaoJdbc.getNew(
		"Globalhitss;databaseName=caixa-db2", DriverJDBCSqlServer.getInstance(), "app", "app"
	);
	
//	jdbc:sqlserver://Globalhitss;databaseName=caixa-db2
	
//	
	
	public static void main(String[] args) {
		sqlLocal.testaConexao();
	}
	
	
}

