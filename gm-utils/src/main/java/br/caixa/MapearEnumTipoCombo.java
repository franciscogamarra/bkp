package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.date.Data;
import gm.utils.map.MapSO;
import src.commom.utils.string.StringCamelCase;
import src.commom.utils.string.StringTrim;

public class MapearEnumTipoCombo {

	public static void main(String[] args) {
		
		Lst<MapSO> lst = CaixaDBs.con.selectMap("select * from lce.lcetb052_tipo_combo order by 1");
		
		for (MapSO map : lst) {
			
			int id = map.getInt("nu_tipo_combo");
			String nome = StringTrim.plus(map.getString("no_tipo_combo"));
			String descricao = StringTrim.plus(map.getString("de_tipo_combo"));
			Data criacao = map.getData("dt_criacao_tipo_combo");
			Boolean disponivel = map.getBoolean("ic_tipo_combo_disponivel");
			int ordem = map.getInt("nu_ordem_tipo_combo");
			
			String nomeTratado = StringCamelCase.exec(nome);
			
			String s = nomeTratado + "("+id+", \""+nome+"\", \""+descricao+"\", new Data("+criacao.getAno() + ", " + criacao.getMes() + ", " + criacao.getDia() +"), "+ordem+", "+disponivel+"),";
			
//			SystemPrint.ln(map);
			SystemPrint.ln(s);
			
		}
		
		
//		{nu_tipo_combo=1, no_tipo_combo=Especial, de_tipo_combo=Pacote contendo apostas para o concurso especial vigente.            , dt_criacao_tipo_combo=2019-01-01, nu_ordem_tipo_combo=0, ic_tipo_combo_disponivel=S}
		
	}
	
}