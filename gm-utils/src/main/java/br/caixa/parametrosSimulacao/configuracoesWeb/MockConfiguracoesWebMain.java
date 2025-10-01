package br.caixa.parametrosSimulacao.configuracoesWeb;

import src.commom.utils.tempo.DataHora;

public class MockConfiguracoesWebMain {
	
	public ConfiguracoesWebPrincipalDto principal;
	public final DataHora dataHora;
	
	public MockConfiguracoesWebMain(DataHora dataHora) {
		if (dataHora == null) {
			throw new NullPointerException("dataHora == null");
		}
		this.dataHora = dataHora;
	}	
	
}
