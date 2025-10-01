package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.map.MapSO;
import src.commom.utils.string.StringCamelCase;
import src.commom.utils.string.StringTrim;

public class MapearParametros {

	public static void main(String[] args) {
		
		Lst<MapSO> lst = CaixaDBs.con.selectMap("select * from lce.lcetb007_parametro order by 1");
		
		for (MapSO map : lst) {
			
			int id = map.getInt("nu_parametro");
			String nome = StringTrim.plus(map.getString("no_parametro"));
			int tipo = map.getInt("nu_tipo_dado");
			String valor = StringTrim.plus(map.getString("vr_parametro"));
			
			String nomeTratado = StringCamelCase.exec(nome);
			
			String nomeTipo;
			
			if (tipo == 1) {
				nomeTipo = "int";
//				continue;
			} else if (tipo == 3) {
				nomeTipo = "double";
//				continue;
			} else if (tipo == 4) {
				nomeTipo = "int";
//				continue;
			} else if (tipo == 5) {
				nomeTipo = "double";
//				continue;
			} else if (tipo == 6) {
				nomeTipo = "String";
//				continue;
			} else {
				nomeTipo = "/*"+tipo+"*/";
			}
			
			String s = "public " + nomeTipo + " " + nomeTratado + ";//" + valor + " // " + id;
			
//			SystemPrint.ln(map);
			SystemPrint.ln(s);
			
		}
		
		
//		{nu_tipo_combo=1, no_tipo_combo=Especial, de_tipo_combo=Pacote contendo apostas para o concurso especial vigente.            , dt_criacao_tipo_combo=2019-01-01, nu_ordem_tipo_combo=0, ic_tipo_combo_disponivel=S}
		
	}
	
}