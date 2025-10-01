package gm.utils.jpa.dbs;

import gm.utils.exception.NaoImplementadoException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBC;

public class SicoobDBs {

	public static void main(String[] args) {
//		sqlserver_dev().testaConexao();
//		sqlserver_hmg().testaConexao();
//		db2_dev().testaConexao();
//		db2_hmg().testaConexao();
//		bla().criarViewsParaFuncionamento();
//		db2_ti().testaConexao();
		db2_ti().selectMap("select * from bnd.programabndes").print();
		
//		System.out.println(
//			db2_dev().selectInt("select IDENDERECOPESSOA FROM CLI.VIW_ENDERECOPESSOAHISTORICO limit 1")				
//		);
		
//		Database URL    jdbc:db2://DB2T303:50001/CRE_TBP:currentSchema=BND
		
//		DB2D1018 -> DB2D1020 - 28/05  
//		DB2D1012 -> DB2D1021 - 28/05
//		DB2D1013 -> DB2D1022 - 28/05  
		
	}
	
	public static ConexaoJdbc sqlserver_dev() {
		return ConexaoJdbc.get("SQLH113:1433;DatabaseName=BD0001_Jacinta2_COPIA", DriverJDBC.MSSQLServer, "usrgesin2", "Usr@2Gesin**");
	}

	public static ConexaoJdbc sqlserver_hmg() {
		return ConexaoJdbc.get("SQLH112:1433;DatabaseName=BDHOMOL_0001;Instance=I02", DriverJDBC.MSSQLServer, "Usrgesin2", "Usr@2Gesin**");
	}
	
	public static ConexaoJdbc db2_dev() {
		return ConexaoJdbc.get("DB2D1020:50001/CRE_TBP:currentSchema=BND;", DriverJDBC.DB2, "usrcre", "123456");
	}
	
	public static ConexaoJdbc db2_hmg() {
		return ConexaoJdbc.get("DB2HCRE:50001/CRE_TBP:currentSchema=BND;", DriverJDBC.DB2, "fabiom.silva", "Sicoob@01");
	}
	
	public static ConexaoJdbc db2_ti() {
//		usrcre 123456
//		return ConexaoJdbc.get("DB2TCRE.homologacao.com.br:50001/CRE_TBP:currentSchema=BND;", DriverJDBC.DB2, "usrcre", "123456");
		return ConexaoJdbc.get("DB2TCRE.homologacao.com.br:50001/CRE_TBP:currentSchema=BND;", DriverJDBC.DB2, "usrbnd", "123456");
	}

	public static ConexaoJdbc hmg() {
		throw new NaoImplementadoException();
//		return ConexaoJdbc.get("SQLH112\\i02;DatabaseName=BD0001_Jacinta2_COPIA", DriverJDBC.MSSQLServer, "usrgesin2", "Usr@2Gesin**");
	}

}
