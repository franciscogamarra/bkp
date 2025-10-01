package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/custo-financeiro.model")
public class CustoFinanceiro {

	public Integer id;
	public String descCustoFinanceiro;
	public String descComplementar;

	@IgnorarDaquiPraBaixo
	private CustoFinanceiro() {}

	public static CustoFinanceiro json() {
		return new CustoFinanceiro();
	}

	public CustoFinanceiro id(Integer id) {
		this.id = id;
		return this;
	}

	public CustoFinanceiro descCustoFinanceiro(String descCustoFinanceiro) {
		this.descCustoFinanceiro = descCustoFinanceiro;
		return this;
	}

	public CustoFinanceiro descComplementar(String descComplementar) {
		this.descComplementar = descComplementar;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		CustoFinanceiro.json()
		.id(0)
		.descCustoFinanceiro("")
		.descComplementar("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(CustoFinanceiro.class, false);
		GerarJson.exec();
	}
}
