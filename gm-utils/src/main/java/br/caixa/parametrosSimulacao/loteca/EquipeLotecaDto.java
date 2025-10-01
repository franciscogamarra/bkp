package br.caixa.parametrosSimulacao.loteca;

import gm.utils.comum.Lst;

//br.gov.caixa.loterias.silce.rest.dto.EquipeDTO
public class EquipeLotecaDto {
	
	public Boolean indicadorSelecao;
	public String nome;
	public Integer numero;
	public Integer numeroPais;
	public String descricaoCurta;
	public String descricaoLonga;
	public String pais;
	public String siglaPais;
	public String uf;
	public String nomeClass;
	
	
	// Lotogol
//	@ApiModelProperty(value = "Identificação do Placar de cada jogo da Lotogol")
	public String placar;
	
	// Loteca
//	@ApiModelProperty(value = "Identificação do Time que teve vitória no jogo da Loteca")
	public boolean vitoria;
	
	public Lst<PlacarDto> placares;
	
	public ParametroEquipeDto parametroEquipe = new ParametroEquipeDto();
	
	public Boolean getIndicadorSelecao() {
		return parametroEquipe.indicadorSelecao;
	}
	public void setIndicadorSelecao(Boolean value) {
		parametroEquipe.indicadorSelecao = value;
	}
	
	public String getNome() {
		return parametroEquipe.nome;
	}
	public void setNome(String s) {
		parametroEquipe.nome = s;
	}
	
	public Integer getNumero() {
		return parametroEquipe.numero;
	}
	public void setNumero(Integer i) {
		parametroEquipe.numero = i;
	}
	
	public Integer getNumeroPais() {
		return parametroEquipe.numeroPais;
	}
	public void setNumeroPais(Integer i) {
		parametroEquipe.numeroPais = i;
	}
	
	public String getDescricaoCurta() {
		return parametroEquipe.descricaoCurta;
	}
	public void setDescricaoCurta(String s) {
		parametroEquipe.descricaoCurta = s;
	}
	
	public String getDescricaoLonga() {
		return parametroEquipe.descricaoLonga;
	}
	public void setDescricaoLonga(String s) {
		parametroEquipe.descricaoLonga = s;
	}
	
	public String getPais() {
		return parametroEquipe.pais;
	}
	public void setPais(String s) {
		parametroEquipe.pais = s;
	}
	
	public String getSiglaPais() {
		return parametroEquipe.siglaPais;
	}
	public void setSiglaPais(String s) {
		parametroEquipe.siglaPais = s;
	}
	
	public String getUf() {
		return parametroEquipe.uf;
	}
	public void setUf(String s) {
		parametroEquipe.uf = s;
	}
	
	public String getNomeClass() {
		return parametroEquipe.getNomeClass();
	}
	public void setNomeClass(String s) {
		parametroEquipe.setNomeClass(s);
	}
	
	public void addPlacar(String valor, boolean selecionado) {
		
		if (placares == null) {
			placares = new Lst<>();
		}
		
		PlacarDto o = new PlacarDto();
		o.valor = valor;
		o.selecionado = selecionado;
		placares.add(o);
		
	}
	
}
