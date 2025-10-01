package br.caixa.parametrosSimulacao.loteca;

import src.commom.utils.string.StringCamelCase;
import src.commom.utils.string.StringEmpty;

//br.gov.caixa.loterias.silce.dominio.broker.parametrosjogos.ParametroEquipe
public class ParametroEquipeDto {
	
	public Boolean indicadorSelecao;
	public String nome;
	public Integer numero;
	public Integer numeroPais;
	public String descricaoCurta;
	public String descricaoLonga;
	public String pais;
	public String siglaPais;
	public String uf;
	
	private static String safe(String s) {
		return StringEmpty.is(s) ? "" : s.trim();
	}
	
	@Override
	public String toString() {
		
		String s = safe(nome);
		
		if (indicadorSelecao != null && !indicadorSelecao && !safe(uf).isEmpty()) {
			s += "/" + safe(uf);
		}
		
		return s;
		
	}

	public String getNomeClass() {
		return StringCamelCase.exec(toString());
	}
	
	public void setNomeClass(String s) {}
	
}
