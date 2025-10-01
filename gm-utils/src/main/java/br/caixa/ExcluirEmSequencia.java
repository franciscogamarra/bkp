package br.caixa;

import gm.utils.jpa.ConexaoJdbc;

public class ExcluirEmSequencia {
	
	public static void main(String[] args) {
//		new ExcluirEmSequencia("lcetb031_codigo_resgate", "nu_codigo_resgate", 0);
//		new ExcluirEmSequencia("lcetb009_aposta_comprada", "nu_aposta_comprada", 0);
//		new ExcluirEmSequencia("lcetb011_aposta", "nu_aposta", 0);
//		new ExcluirEmSequencia("lcetb013_compra", "nu_compra", 0);
	}

	private final String SQLUnique;
	private final String SQL;
	private Integer primeiro;
	
	public ExcluirEmSequencia(String table, String column, int primeiro) {
		this.primeiro = primeiro;
		SQLUnique = "select "+column+" from lce."+table+" where "+column+" > :primeiro order by 1 limit :limit";
		SQL = "delete from lce."+table+" where "+column+" in (" + SQLUnique + " )";
		exec();
	}
	
	public static final ConexaoJdbc con = CaixaDBs.con;
	
	public void exec() {
		
		while (primeiro != null) {
			while (exec(SQL, 1111));
			while (exec(SQL, 511));
			while (exec(SQL, 111));
			while (exec(SQL, 11));
			while (exec(SQL, 2));
			while (exec(SQL, 1));
			primeiro = con.selectInt(rep(SQLUnique, 1));
		}
		
	}

	private boolean exec(String sql, int limit) {

		try {
			return con.exec(rep(sql, limit)) > 0;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	private String rep(String sql, int limit) {
		return sql.replace(":primeiro", primeiro + "").replace(":limit", limit + "");
	}
	
}