package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @From("@app/funcionalidades/proposta-credito-bndes/models/programa-tipo-investimento.model")
public class ProgramaTipoInvestimento {

	public Programa programa;
	public TipoInvestimento tipoInvestimento;
	public Boolean bolTipoExclusivo;
	public Boolean bolDescItemObrigatorio;

	@IgnorarDaquiPraBaixo
	private ProgramaTipoInvestimento() {}

	public static ProgramaTipoInvestimento json() {
		return new ProgramaTipoInvestimento();
	}

	public ProgramaTipoInvestimento programa(Programa programa) {
		this.programa = programa;
		return this;
	}

	public ProgramaTipoInvestimento tipoInvestimento(TipoInvestimento tipoInvestimento) {
		this.tipoInvestimento = tipoInvestimento;
		return this;
	}

	public ProgramaTipoInvestimento bolTipoExclusivo(Boolean bolTipoExclusivo) {
		this.bolTipoExclusivo = bolTipoExclusivo;
		return this;
	}

	public ProgramaTipoInvestimento bolDescItemObrigatorio(Boolean bolDescItemObrigatorio) {
		this.bolDescItemObrigatorio = bolDescItemObrigatorio;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		ProgramaTipoInvestimento.json()
		.programa(null)
		.tipoInvestimento(null)
		.bolTipoExclusivo(true)
		.bolDescItemObrigatorio(true)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(ProgramaTipoInvestimento.class, false);
		GerarJson.exec();
	}
}
