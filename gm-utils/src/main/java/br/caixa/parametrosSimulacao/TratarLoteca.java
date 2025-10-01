package br.caixa.parametrosSimulacao;

import gm.utils.string.ListString;

public class TratarLoteca {

	public static void main(String[] args) {
		
		ListString list = new ListString();
		list.load("C:\\desenvolvimento\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\java\\br\\caixa\\parametrosSimulacao\\dtos\\Mock.java");
		list.trimPlus();
		list.removeIfNotStartsWith("o.principal.partidasLoteca.getLast()");
		list.replaceTexto("o.principal.partidasLoteca.getLast()", "o");
		list.removeIf(s -> s.endsWith(".placares = new Lst<>();"));
		list.removeIf(s -> s.endsWith(".placares.add(new PlacareDto());"));
		
		list.replaceEach(s -> {
			
			if (s.startsWith("o.numero = ")) {
				return "o = addPartida();";
			}

			if (s.startsWith("o.vitoria = ")) {
				return s;
			}
			
//			o.equipe1.addPlacar("0", false);
			
			return s;
			
		});
		
		list.print();
		
		
	}
	
}