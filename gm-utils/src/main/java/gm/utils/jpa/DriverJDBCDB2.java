package gm.utils.jpa;

public class DriverJDBCDB2 extends DriverJDBC {

	private DriverJDBCDB2() {}

	private static DriverJDBC instance;
	
	public static final DriverJDBC getInstance() {
		
		if (instance == null) {
			instance = new DriverJDBCDB2();
		}
		
		return instance;
	}

	@Override
	public String getDefaultSchema() {
		return "lce";
	}

	@Override
	public String getDriverName() {
		return "com.ibm.db2.jcc.DB2Driver";
	}

	@Override
	public String getNomeBanco() {
		return "db2";
	}

	@Override
	protected int getVersao() {
		return 20230225;
	}
	
	@Override
	protected String getSqlTest() {
		return "select 1 from sysibm.sysdummy1";
	}

}
