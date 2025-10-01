package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.ItemInvestimento;
import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.JSon;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;
import js.array.Array;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/proposta-credito-bndes.model")
public class PropostaCreditoBndes {

	public Programa programa;
	public Array<ItemInvestimento> itensInvestimento;

	@IgnorarDaquiPraBaixo
	private PropostaCreditoBndes() {}

	public static PropostaCreditoBndes json() {
		return new PropostaCreditoBndes();
	}

	public PropostaCreditoBndes programa(Programa programa) {
		this.programa = programa;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		PropostaCreditoBndes.json()
		.programa(null)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(PropostaCreditoBndes.class, false);
		GerarJson.exec();
	}

	@Override
	public String toString() {
		return JSon.toJson(this);
	}
}
