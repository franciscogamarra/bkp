package br.caixa.parametrosSimulacao.loteca;

import src.commom.utils.tempo.DataHora;

public class MockPartidaLotecaMain {
	
	public IncluirApostaInDto principal;
	public final DataHora dataHora;
	
	public MockPartidaLotecaMain(DataHora dataHora) {
		if (dataHora == null) {
			throw new NullPointerException("dataHora == null");
		}
		this.dataHora = dataHora;
	}
	
}
