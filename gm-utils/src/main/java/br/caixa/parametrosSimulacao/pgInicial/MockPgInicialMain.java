package br.caixa.parametrosSimulacao.pgInicial;

import src.commom.utils.tempo.DataHora;

public class MockPgInicialMain {
	
	public PgInicialPrincipalDto principal;
	public final DataHora dataHora;
	
	public MockPgInicialMain(DataHora dataHora) {
		if (dataHora == null) {
			throw new NullPointerException("dataHora == null");
		}
		this.dataHora = dataHora;
	}	
	
}
