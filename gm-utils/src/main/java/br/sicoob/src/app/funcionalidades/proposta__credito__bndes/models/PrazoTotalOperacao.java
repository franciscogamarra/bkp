package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/prazo-total-operacao.model")
public class PrazoTotalOperacao {

	public Integer id;
	public String sigla;
	public String descricao;
	public Integer prazoTotalMes;

	@IgnorarDaquiPraBaixo
	private PrazoTotalOperacao() {}

	public static PrazoTotalOperacao json() {
		return new PrazoTotalOperacao();
	}

	public PrazoTotalOperacao id(Integer id) {
		this.id = id;
		return this;
	}

	public PrazoTotalOperacao sigla(String sigla) {
		this.sigla = sigla;
		return this;
	}

	public PrazoTotalOperacao descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public PrazoTotalOperacao prazoTotalMes(Integer prazoTotalMes) {
		this.prazoTotalMes = prazoTotalMes;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		PrazoTotalOperacao.json()
		.id(0)
		.sigla("")
		.descricao("")
		.prazoTotalMes(0)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(PrazoTotalOperacao.class, false);
		GerarJson.exec();
	}
}
