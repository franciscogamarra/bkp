package br.caixa.parametrosSimulacao.dtos;

import gm.utils.comum.Lst;

public class ParametrosSimulacaoDto extends Dto {

	public Double valorMinimoCarrinho;
	public Integer quantidadeMaximaApostasCarrinho;
	public Lst<ParametroDto> parametros;
}
