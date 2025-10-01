package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.map.MapSO;
import src.commom.utils.string.StringCamelCase;

public class MontarTipoCombo {

	public static void main(String[] args) {
		
		ConexaoJdbc con = CaixaDBs.con;
		
		Lst<MapSO> lst = con.selectMap("select * from lce.lcetb052_tipo_combo order by nu_tipo_combo limit 100");
		
		for (MapSO map : lst) {

			int id = map.getInt("nu_tipo_combo");
			String nome = map.getStringTrim("no_tipo_combo");
			String descricao = map.getStringTrim("de_tipo_combo");
			int ordem = map.getInt("nu_ordem_tipo_combo");
			boolean disponivel = map.isTrue("ic_tipo_combo_disponivel");
			
			String nomeVar = StringCamelCase.exec(nome);
			
			String s = nomeVar + "("+id+", \""+nome+"\", \""+descricao+"\", "+ordem+", "+disponivel+"),";
			SystemPrint.ln(s);
			
		}
		
	}
	
}