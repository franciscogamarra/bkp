package gm.utils.jpa.dbs;

import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBC;

public class CooperDBs {

	public static final String user = "UserJboss";
	public static final String pass = "teste";


	public static void main(String[] args) {
		homol().criarViewsParaFuncionamento();
	}

	public static ConexaoJdbc get() {
		return homol();
	}

	// spring.datasource.url =
	// jdbc:sqlserver://BD03\\instancia;databaseName=sicop;encrypt=true;trustServerCertificate=true;applicationName=getec/desen/emprestimos-app;

	public static ConexaoJdbc logSistemashomol() {
		return ConexaoJdbc.get("bd03\\instancia;databaseName=logSistemas;trustServerCertificate=true", DriverJDBC.MSSQLServer, user, pass);
	}

	public static ConexaoJdbc notec() {
		return ConexaoJdbc.get("bd03;databaseName=notec;trustServerCertificate=true", DriverJDBC.MSSQLServer, user, pass);
	}

	public static ConexaoJdbc dsnHomol() {
		return ConexaoJdbc.get("bd03\\instancia;databaseName=dsn;trustServerCertificate=true", DriverJDBC.MSSQLServer, user, pass);
	}
	
	public static ConexaoJdbc notecHomol() {
		return ConexaoJdbc.get("bd03\\instancia;databaseName=notec;trustServerCertificate=true", DriverJDBC.MSSQLServer,
				user, pass);
	}

	public static ConexaoJdbc gscHomol() {
		return ConexaoJdbc.get("bd03\\instancia;databaseName=gsc;trustServerCertificate=true", DriverJDBC.MSSQLServer,
				user, pass);
	}

	public static ConexaoJdbc auditoriaHomol() {
		return ConexaoJdbc.get("bd03\\instancia;databaseName=auditoria;trustServerCertificate=true",
				DriverJDBC.MSSQLServer, user, pass);
	}

	public static ConexaoJdbc auditoriaExtra() {
		return ConexaoJdbc.get("HS-TEMPSQL-W1;databaseName=auditoria;trustServerCertificate=true",
				DriverJDBC.MSSQLServer, user, pass);
	}

	public static ConexaoJdbc desen() {
		return getSicop("BD03");
	}

	public static ConexaoJdbc bd05() {
		return getSicop("BD05");
	}

	public static ConexaoJdbc bd09() {
		return getSicop("BD09");
	}

	public static ConexaoJdbc homol() {
		return getSicop("BD03\\instancia");
	}

	public static ConexaoJdbc homolConvenioCooperforte() {
		return getHomol("convenio_cooperforte");
	}

	public static ConexaoJdbc cobrancaHomol() {
		return getHomol("cobranca");
	}

	public static ConexaoJdbc homolWebbank() {
		return getHomol("webbank");
	}

	public static ConexaoJdbc homolConciliacao() {
		return getHomol("conciliacao");
	}

	public static ConexaoJdbc homolLogExtratoWeb() {
		return getHomol("logExtratoWeb");
	}

	public static ConexaoJdbc getHomol(String database) {
		return get("BD03\\instancia", database);
	}

	public static ConexaoJdbc extra() {
		return getSicop("HS-TEMPSQL-W1");
	}

	public static ConexaoJdbc bd12() {
		return getSicop("DS-SQL12-W1");
	}

	private static ConexaoJdbc getSicop(String banco) {
		return get(banco, "sicop");
	}

	public static ConexaoJdbc pgnHomol() {
		return getHomol("pgn");
	}
	
	public static ConexaoJdbc pgn() {
		return ConexaoJdbc.get("bd07.cooperforte.coop\\INSTANCIA;databaseName=PGN", DriverJDBC.MSSQLServer, "UserPGN",
				"teste");
	}

	private static ConexaoJdbc get(String banco, String database) {
		ConexaoJdbc con = ConexaoJdbc.get(banco + ";databaseName=" + database + ";trustServerCertificate=true", DriverJDBC.MSSQLServer, user, pass);
		con.setDatabase(database);
		return con;
	}

}
