package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/tipo-evento.model")
public class TipoEvento {

	public Integer id;
	public String descricao;

	@IgnorarDaquiPraBaixo
	private TipoEvento() {}

	public static TipoEvento json() {
		return new TipoEvento();
	}

	public TipoEvento id(Integer id) {
		this.id = id;
		return this;
	}

	public TipoEvento descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		TipoEvento.json()
		.id(0)
		.descricao("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(TipoEvento.class, false);
		GerarJson.exec();
	}
}
