package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.JSon;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;
import js.array.Array;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/programa.model")
public class Programa {

	public Integer id;
	public String descricao;
	public String sigla;
	public Double taxaJurosBasica;
	public Double taxaJurosRisco;
	public Integer produtoBndes;
	public Integer linhaBndes;
	public Boolean bolFinancEquipNovoUsado;
	public String siglaPrograma;
	public Boolean bolExigeItemInvestimento;
	public Boolean bolEnviaQtdEmpregado;
	public Boolean bolHabilitaCadastroPl;
	public Double taxaJurosRiscoMax;
	public Double taxaJurosRiscoMin;
	public Double taxaJurosRiscoPadrao;
	public Boolean bolHabilitaEnvioPlataformaCredito;
	public Boolean bolLicencaDispensaAmb;
	public Boolean bolLicencaAmbItemDiverso;
	public Boolean bolLicencaAmbExcecao;
	public CustoFinanceiro custoFinanceiro;
	public Boolean bolExigeDataEmissaoCartaFianca;
	public Boolean bolFinalidadeLiberacao;
	public Boolean bolFinalidadeLiberacaoExcecao;
	public String indiceBndes;
	public String codigoPrograma;
	public String percCustoIntermediacaoFinanceira;
	public String paramInfoComplementarAgricola;
	public String paramInfoComplementarProjetoInvestimento;
	public String paramInfoResultadoEsperado;
	public Array<PrazosLimitesSolicitacaoPL> prazosLimitesSolicitacaoPL;
	public Array<TipoEvento> tipoEventos;

	@IgnorarDaquiPraBaixo
	private Programa() {}

	public static Programa json() {
		return new Programa();
	}

	public Programa id(Integer id) {
		this.id = id;
		return this;
	}

	public Programa descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public Programa sigla(String sigla) {
		this.sigla = sigla;
		return this;
	}

	public Programa taxaJurosBasica(Double taxaJurosBasica) {
		this.taxaJurosBasica = taxaJurosBasica;
		return this;
	}

	public Programa taxaJurosRisco(Double taxaJurosRisco) {
		this.taxaJurosRisco = taxaJurosRisco;
		return this;
	}

	public Programa produtoBndes(Integer produtoBndes) {
		this.produtoBndes = produtoBndes;
		return this;
	}

	public Programa linhaBndes(Integer linhaBndes) {
		this.linhaBndes = linhaBndes;
		return this;
	}

	public Programa bolFinancEquipNovoUsado(Boolean bolFinancEquipNovoUsado) {
		this.bolFinancEquipNovoUsado = bolFinancEquipNovoUsado;
		return this;
	}

	public Programa siglaPrograma(String siglaPrograma) {
		this.siglaPrograma = siglaPrograma;
		return this;
	}

	public Programa bolExigeItemInvestimento(Boolean bolExigeItemInvestimento) {
		this.bolExigeItemInvestimento = bolExigeItemInvestimento;
		return this;
	}

	public Programa bolEnviaQtdEmpregado(Boolean bolEnviaQtdEmpregado) {
		this.bolEnviaQtdEmpregado = bolEnviaQtdEmpregado;
		return this;
	}

	public Programa bolHabilitaCadastroPl(Boolean bolHabilitaCadastroPl) {
		this.bolHabilitaCadastroPl = bolHabilitaCadastroPl;
		return this;
	}

	public Programa taxaJurosRiscoMax(Double taxaJurosRiscoMax) {
		this.taxaJurosRiscoMax = taxaJurosRiscoMax;
		return this;
	}

	public Programa taxaJurosRiscoMin(Double taxaJurosRiscoMin) {
		this.taxaJurosRiscoMin = taxaJurosRiscoMin;
		return this;
	}

	public Programa taxaJurosRiscoPadrao(Double taxaJurosRiscoPadrao) {
		this.taxaJurosRiscoPadrao = taxaJurosRiscoPadrao;
		return this;
	}

	public Programa bolHabilitaEnvioPlataformaCredito(Boolean bolHabilitaEnvioPlataformaCredito) {
		this.bolHabilitaEnvioPlataformaCredito = bolHabilitaEnvioPlataformaCredito;
		return this;
	}

	public Programa bolLicencaDispensaAmb(Boolean bolLicencaDispensaAmb) {
		this.bolLicencaDispensaAmb = bolLicencaDispensaAmb;
		return this;
	}

	public Programa bolLicencaAmbItemDiverso(Boolean bolLicencaAmbItemDiverso) {
		this.bolLicencaAmbItemDiverso = bolLicencaAmbItemDiverso;
		return this;
	}

	public Programa bolLicencaAmbExcecao(Boolean bolLicencaAmbExcecao) {
		this.bolLicencaAmbExcecao = bolLicencaAmbExcecao;
		return this;
	}

	public Programa custoFinanceiro(CustoFinanceiro custoFinanceiro) {
		this.custoFinanceiro = custoFinanceiro;
		return this;
	}

	public Programa bolExigeDataEmissaoCartaFianca(Boolean bolExigeDataEmissaoCartaFianca) {
		this.bolExigeDataEmissaoCartaFianca = bolExigeDataEmissaoCartaFianca;
		return this;
	}

	public Programa bolFinalidadeLiberacao(Boolean bolFinalidadeLiberacao) {
		this.bolFinalidadeLiberacao = bolFinalidadeLiberacao;
		return this;
	}

	public Programa bolFinalidadeLiberacaoExcecao(Boolean bolFinalidadeLiberacaoExcecao) {
		this.bolFinalidadeLiberacaoExcecao = bolFinalidadeLiberacaoExcecao;
		return this;
	}

	public Programa indiceBndes(String indiceBndes) {
		this.indiceBndes = indiceBndes;
		return this;
	}

	public Programa codigoPrograma(String codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
		return this;
	}

	public Programa percCustoIntermediacaoFinanceira(String percCustoIntermediacaoFinanceira) {
		this.percCustoIntermediacaoFinanceira = percCustoIntermediacaoFinanceira;
		return this;
	}

	public Programa paramInfoComplementarAgricola(String paramInfoComplementarAgricola) {
		this.paramInfoComplementarAgricola = paramInfoComplementarAgricola;
		return this;
	}

	public Programa paramInfoComplementarProjetoInvestimento(String paramInfoComplementarProjetoInvestimento) {
		this.paramInfoComplementarProjetoInvestimento = paramInfoComplementarProjetoInvestimento;
		return this;
	}

	public Programa paramInfoResultadoEsperado(String paramInfoResultadoEsperado) {
		this.paramInfoResultadoEsperado = paramInfoResultadoEsperado;
		return this;
	}

	public Programa prazosLimitesSolicitacaoPL(Array<PrazosLimitesSolicitacaoPL> prazosLimitesSolicitacaoPL) {
		this.prazosLimitesSolicitacaoPL = prazosLimitesSolicitacaoPL;
		return this;
	}

	public Programa tipoEventos(Array<TipoEvento> tipoEventos) {
		this.tipoEventos = tipoEventos;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		Programa.json()
		.id(0)
		.descricao("")
		.sigla("")
		.taxaJurosBasica(null)
		.taxaJurosRisco(null)
		.produtoBndes(0)
		.linhaBndes(0)
		.bolFinancEquipNovoUsado(true)
		.siglaPrograma("")
		.bolExigeItemInvestimento(true)
		.bolEnviaQtdEmpregado(true)
		.bolHabilitaCadastroPl(true)
		.taxaJurosRiscoMax(null)
		.taxaJurosRiscoMin(null)
		.taxaJurosRiscoPadrao(null)
		.bolHabilitaEnvioPlataformaCredito(true)
		.bolLicencaDispensaAmb(true)
		.bolLicencaAmbItemDiverso(true)
		.bolLicencaAmbExcecao(true)
		.custoFinanceiro(null)
		.bolExigeDataEmissaoCartaFianca(true)
		.bolFinalidadeLiberacao(true)
		.bolFinalidadeLiberacaoExcecao(true)
		.indiceBndes("")
		.codigoPrograma("")
		.percCustoIntermediacaoFinanceira("")
		.paramInfoComplementarAgricola("")
		.paramInfoComplementarProjetoInvestimento("")
		.paramInfoResultadoEsperado("")
		.prazosLimitesSolicitacaoPL(null)
		.tipoEventos(null)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(Programa.class, false);
		GerarJson.exec();
	}

	@Override
	public String toString() {
		return JSon.toJson(this);
	}
}
