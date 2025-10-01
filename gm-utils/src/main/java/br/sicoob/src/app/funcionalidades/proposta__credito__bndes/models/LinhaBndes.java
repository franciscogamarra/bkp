package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @From("@app/funcionalidades/proposta-credito-bndes/models/linha-bndes.model")
public class LinhaBndes {

	public Integer id;
	public String descricao;

	@IgnorarDaquiPraBaixo
	private LinhaBndes() {}

	public static LinhaBndes json() {
		return new LinhaBndes();
	}

	public LinhaBndes id(Integer id) {
		this.id = id;
		return this;
	}

	public LinhaBndes descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		LinhaBndes.json()
		.id(0)
		.descricao("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(LinhaBndes.class, false);
		GerarJson.exec();
	}
}
