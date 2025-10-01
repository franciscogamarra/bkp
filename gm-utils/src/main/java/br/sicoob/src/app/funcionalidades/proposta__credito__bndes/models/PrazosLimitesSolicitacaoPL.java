package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.JSon;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/prazo-limite-solicitacao-pl.model")
public class PrazosLimitesSolicitacaoPL {

	public int id;
	public int prazoLimiteDia;

	@IgnorarDaquiPraBaixo
	private PrazosLimitesSolicitacaoPL() {}

	public static PrazosLimitesSolicitacaoPL json() {
		return new PrazosLimitesSolicitacaoPL();
	}

	public PrazosLimitesSolicitacaoPL id(int id) {
		this.id = id;
		return this;
	}

	public PrazosLimitesSolicitacaoPL prazoLimiteDia(int prazoLimiteDia) {
		this.prazoLimiteDia = prazoLimiteDia;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		PrazosLimitesSolicitacaoPL.json()
		.id(0)
		.prazoLimiteDia(0)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(PrazosLimitesSolicitacaoPL.class, false);
		GerarJson.exec();
	}

	@Override
	public String toString() {
		return JSon.toJson(this);
	}
}
