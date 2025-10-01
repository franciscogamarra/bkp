package br.caixa.parametrosSimulacao.dtos;

import src.commom.utils.tempo.DataHora;

public class MockParametroSimulacaoMain {
	
	public ParametrosSimulacaoPrincipalDto principal;
	public final DataHora dataHora;
	
	public MockParametroSimulacaoMain(DataHora dataHora) {
		if (dataHora == null) {
			throw new NullPointerException("dataHora == null");
		}
		this.dataHora = dataHora;
	}
	
}
