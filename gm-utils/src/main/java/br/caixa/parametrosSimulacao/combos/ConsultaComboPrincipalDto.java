package br.caixa.parametrosSimulacao.combos;

import br.caixa.parametrosSimulacao.dtos.Dto;
import gm.utils.comum.Lst;

public class ConsultaComboPrincipalDto extends Dto {
	
	public String versao;
	public Lst<ConsultarCombosOutDto> payload;
}
