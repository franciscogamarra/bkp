package br.caixa.parametrosSimulacao.combos;

import src.commom.utils.tempo.DataHora;

public class MockConsultaComboMain {
	
	public ConsultaComboPrincipalDto principal;
	public final DataHora dataHora;
	
	public MockConsultaComboMain(DataHora dataHora) {
		if (dataHora == null) {
			throw new NullPointerException("dataHora == null");
		}
		this.dataHora = dataHora;
	}	
	
}
