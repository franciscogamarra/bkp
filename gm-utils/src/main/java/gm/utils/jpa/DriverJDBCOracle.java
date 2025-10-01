package gm.utils.jpa;

import gm.utils.exception.NaoImplementadoException;

public class DriverJDBCOracle extends DriverJDBC {

	private DriverJDBCOracle() {}

	private static DriverJDBC instance;
	
	public static final DriverJDBC getInstance() {
		
		if (instance == null) {
			instance = new DriverJDBCOracle();
		}
		
		return instance;
	}
	@Override
	public String getDialect() {
		return "org.hibernate.dialect.Oracle9Dialect";
	}

	@Override
	public String getDriverName() {
		return "oracle.jdbc.OracleDriver";
	}

	@Override
	public String getNomeBanco() {
		return "oracle";
	}

	@Override
	protected int getVersao() {
		return 20221123;
	}
	
	@Override
	public String getUrl(String banco) {
		return "jdbc:oracle:thin:" + banco;
	}
	
	@Override
	protected String getSqlTest() {
		return "select 1 from dual";
	}
	
	@Override
	public Class<?> getTipoColuna(String s) {
		
		if ("NUMBER".contentEquals(s)) {
			return Integer.class;
		}
		
		if ("VARCHAR2".contentEquals(s)) {
			return String.class;
		}
		
		throw new NaoImplementadoException(s);
		
	}

}
