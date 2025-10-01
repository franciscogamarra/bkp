package gm.utils.jpa;

public class DriverJDBCSqlServer extends DriverJDBCSqlServerBase {

	protected DriverJDBCSqlServer() {}

	private static DriverJDBC instance;
	
	public static final DriverJDBC getInstance() {
		
		if (instance == null) {
			instance = new DriverJDBCSqlServer();
		}
		
		return instance;
	}

}
