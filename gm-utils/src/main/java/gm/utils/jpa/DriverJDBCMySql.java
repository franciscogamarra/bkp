package gm.utils.jpa;

public class DriverJDBCMySql extends DriverJDBC {

	private DriverJDBCMySql() {}

	public static final DriverJDBC instance = new DriverJDBCMySql();

	@Override
	public String getDialect() {
		return "org.hibernate.dialect.MySQLDialect";
	}

	@Override
	protected int getVersao() {
		return 20220205;
	}

}
