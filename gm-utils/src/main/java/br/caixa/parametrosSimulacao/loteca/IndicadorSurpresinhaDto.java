package br.caixa.parametrosSimulacao.loteca;

import br.caixa.parametrosSimulacao.dtos.Dto;

public class IndicadorSurpresinhaDto extends Dto {
	
	public Integer valor;
	
	public static final IndicadorSurpresinhaDto naoSurpresinha = new IndicadorSurpresinhaDto();
	public static final IndicadorSurpresinhaDto surpresinha = new IndicadorSurpresinhaDto();
	public static final IndicadorSurpresinhaDto surpresinhaNumerica = new IndicadorSurpresinhaDto();
	static {
		naoSurpresinha.valor = 1;
		surpresinha.valor = 2;
		surpresinhaNumerica.valor = 3;
	}
	
}
