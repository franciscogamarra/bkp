package br.caixa.parametrosSimulacao.loteca;

import gm.utils.comum.Lst;

//br.gov.caixa.loterias.silce.rest.dto.PartidaLotecaDTO

public class PartidaLotecaDto {
	
	//este campo não é chamado no back, mas vem da tela
	public Integer numero;
	
	public EquipeLotecaDto equipe1;
	public EquipeLotecaDto equipe2;
	public String descricaoLegenda;
	public String letraLegenda;
	public boolean empate;
	public boolean preenchida;
	
	public Lst<Integer> getPrognosticos() {
		
		Lst<Integer> itens = new Lst<>();
		
		int valorBase = (numero - 1) * 3;
		
		if (equipe1.vitoria) {
			itens.add(valorBase + 1);
		}
		
		if (empate) {
			itens.add(valorBase + 2);
		}
		
		if (equipe2.vitoria) {
			itens.add(valorBase + 3);
		}
		
		return itens;
		
	}
	
	public boolean isDupla() {
		return soma() == 2;
	}
	
	public boolean isTripla() {
		return soma() == 3;
	}
	
	private int soma() {
		return toInt(empate) + toInt(equipe1.vitoria) + toInt(equipe2.vitoria);
	}
	
	private int toInt(boolean value) {
		return value ? 1 : 0;
	}
	
//	private static final Integer QUANTIDADE_PARTIDAS_LOTECA = 14;
//
//	public static final Integer ID_TIME_GENERICO = -1;

}
