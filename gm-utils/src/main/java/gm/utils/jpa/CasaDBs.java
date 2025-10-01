package gm.utils.jpa;

public class CasaDBs {

	public static ConexaoJdbc teste() {
//		return ConexaoJdbc.get("localhost:5432/teste", DriverJDBC.PostgreSQL, "postgres", "postgres");
		return ConexaoJdbc.get("localhost:5433/sipeagro2", DriverJDBC.PostgreSQL, "postgres", "postgres");
	}

	public static void main(String[] args) {
		teste().testaConexao();
	}

}
