package gm.utils.jpa;

public class DriverJDBCSqlServerJtds extends DriverJDBCSqlServerBase {

	private static DriverJDBC instance;
	
	public static final DriverJDBC getInstance() {
		
		if (instance == null) {
			instance = new DriverJDBCSqlServerJtds();
		}
		
		return instance;
	}
	
	@Override
	public String getUrl(String banco) {
		return "jdbc:jtds:"+getNomeBanco()+"://" + banco;
	}
	
}