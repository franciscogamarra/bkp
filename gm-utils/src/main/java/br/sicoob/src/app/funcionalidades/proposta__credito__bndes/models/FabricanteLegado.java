package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/fabricante-legado.model")
public class FabricanteLegado {

	public Long codigoFabricante;
	public String descricaoFabricante;

	@IgnorarDaquiPraBaixo
	private FabricanteLegado() {}

	public static FabricanteLegado json() {
		return new FabricanteLegado();
	}

	public FabricanteLegado codigoFabricante(Long codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
		return this;
	}

	public FabricanteLegado descricaoFabricante(String descricaoFabricante) {
		this.descricaoFabricante = descricaoFabricante;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		FabricanteLegado.json()
		.codigoFabricante(null)
		.descricaoFabricante("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(FabricanteLegado.class, false);
		GerarJson.exec();
	}
}
