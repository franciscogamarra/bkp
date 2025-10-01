package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import src.commom.utils.string.StringTrim;

public class CadastrarEquipes {
	
	private static final ConexaoJdbc con = CaixaDBs.con;

	private static final String INSERT = ""
		+ "insert into lce_legado.lcevw009_eqpe_espa("
		+ "nu_equipe, no_equipe, no_equipe_reduzido, nu_pais, de_razao_social, ic_selecao, sg_uf"
		+ ")"
		+ " select "
		+ "  nu_equipe = {nu_equipe}"
		+ ", no_equipe = {no_equipe}"
		+ ", no_equipe_reduzido = {no_equipe_reduzido}"
		+ ", nu_pais = {nu_pais}"
		+ ", de_razao_social = {de_razao_social}"
		+ ", ic_selecao = {ic_selecao}"
		+ ", sg_uf = {sg_uf}"
		+ ";"
	;

	private static final String SELECT = ""
		+ " select nu_equipe, no_equipe, no_equipe_reduzido, no_equipe_reduzido, nu_pais, de_razao_social, ic_selecao, sg_uf from lce.lcevw009_eqpe_espa order by 1 limit 2000"
	;
	
	public static void main(String[] args) {
		
//		CaixaDBs.sqlServerAdmin.exec("truncate table lce_legado.lcevw009_eqpe_espa");
		
		ListString list = new ListString();
		
		Lst<MapSO> itens = con.selectMap(SELECT);
		
		itens.forEach(i -> {

			String sql = INSERT;
			
			for (String key : i.getKeys()) {
				Object value = i.get(key);
				String s;
				
				if (value == null) {
					s = "null";
				} else if (value instanceof Integer) {
					s = value.toString();
				} else {
					 s = "'" + StringTrim.plus(value.toString()).replace("'", "''") + "'";
				}
				
				sql = sql.replace("{"+key+"}", s);
			}
			
			sql = StringTrim.plus(sql);
			
//			CaixaDBs.sqlServerAdmin.exec(sql);
//			
			list.add(sql);
			
		});
		
		
		list.save("C:/desenvolvimento/dev/quarkus/LOTERIAS-SILCE-carrinho/src/main/resources/scripts/carga-equipes.sql");
		
//		CaixaDBs.con.tryExec("delete from lce.lcetb002_apostador where nu_apostador = " + id);
//		CaixaDBs.con.tryExec(INSERT_APOSTADOR.replace(":id", ""+id).replace(":xxx", xxx).replace(":cpf", cpf));
//		CaixaDBs.con.tryExec(INSERT_COMPRA.replace(":id", ""+id));
		
	}
	
}
