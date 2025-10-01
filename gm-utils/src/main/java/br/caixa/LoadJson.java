package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.string.ListString;

public class LoadJson {
	
	public static void main(String[] args) {
		
		ListString list = new ListString();
		list.load("C:\\desenvolvimento\\dev\\quarkus\\LOTERIAS-SILCE-carrinho\\src\\test\\java\\br\\caixa\\loterias\\silce\\resources\\aposta\\incluir\\loteca\\Req_Res_Loteca_2.txt");
		list.rtrim();
		
		while (!list.get(0).contentEquals("#REQUEST")) {
			list.remove(0);
		}
		
		list.remove(0);

		while (!list.getLast().contentEquals("#RESPONSE")) {
			list.removeLast();
		}

		while (!list.getLast().contentEquals("}")) {
			list.removeLast();
		}
		
		list.trimPlus();
		
		MapSO map = MapSoFromJson.get(list.toString(""));
		map.print();
		
		list.clear();
		
		Lst<MapSO> partidas = map.getSubList("partidasLoteca");
		for (MapSO partida : partidas) {
			
			list.add();
			list.add("o = addPartida();");
			
			if (partida.get("empate") != null) {
				list.add("o.empate = " + partida.getBoolean("empate") + ";");
			}
			
			list.add("o.preenchida = " + partida.getBoolean("preenchida") + ";");
			addEquipe(1, partida, list);
			addEquipe(2, partida, list);
			
		}
		
		SystemPrint.ln("======================");
		
		list.print();
		
	}

	private static void addEquipe(int equipe, MapSO partida, ListString list) {
		
		MapSO map = partida.get("equipe" + equipe);
		
		list.add();
		list.add("o.equipe"+equipe+" = new EquipeDto();");
		list.add("o.equipe"+equipe+".setIndicadorSelecao("+map.get("indicadorSelecao")+");");
		list.add("o.equipe"+equipe+".setNome(\""+map.getString("nome")+"\");");
		list.add("o.equipe"+equipe+".setNumero("+map.getInt("numero")+");");
		list.add("o.equipe"+equipe+".setNumeroPais("+map.getIntObrig("numeroPais")+");");
		list.add("o.equipe"+equipe+".setDescricaoCurta(\""+map.getString("descricaoCurta")+"\");");
		list.add("o.equipe"+equipe+".setPais(\""+map.getString("pais")+"\");");
		list.add("o.equipe"+equipe+".setSiglaPais(\""+map.getString("siglaPais")+"\");");
		list.add("o.equipe"+equipe+".setUf(\""+map.getString("uf")+"\");");
		list.add("o.equipe"+equipe+".setNomeClass(\""+map.getString("nomeClass")+"\");");
		
		if (map.get("vitoria") != null) {
			list.add("o.equipe"+equipe+".vitoria = " + map.isTrue("vitoria") + ";");
		}
		
		Lst<MapSO> placares = map.getSubList("placares");
		for (MapSO placar : placares) {
			list.add("o.equipe"+equipe+".addPlacar(\""+placar.getString("valor")+"\", "+placar.getBoolean("selecionado")+");");
		}
		
		
	}
	
}