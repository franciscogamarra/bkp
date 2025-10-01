package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @From("@app/funcionalidades/proposta-credito-bndes/models/produto-bndes.model")
public class ProdutoBndes {

	public Integer id;
	public String descricao;

	@IgnorarDaquiPraBaixo
	private ProdutoBndes() {}

	public static ProdutoBndes json() {
		return new ProdutoBndes();
	}

	public ProdutoBndes id(Integer id) {
		this.id = id;
		return this;
	}

	public ProdutoBndes descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		ProdutoBndes.json()
		.id(0)
		.descricao("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(ProdutoBndes.class, false);
		GerarJson.exec();
	}
}
