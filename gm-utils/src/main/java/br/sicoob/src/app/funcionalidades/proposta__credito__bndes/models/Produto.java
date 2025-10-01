package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/produto.model")
public class Produto {

	public Integer id;
	public String descricao;

	@IgnorarDaquiPraBaixo
	private Produto() {}

	public static Produto json() {
		return new Produto();
	}

	public Produto id(Integer id) {
		this.id = id;
		return this;
	}

	public Produto descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		Produto.json()
		.id(0)
		.descricao("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(Produto.class, false);
		GerarJson.exec();
	}
}
