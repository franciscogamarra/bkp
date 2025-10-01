package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @NaoConverter @From("@app/funcionalidades/proposta-credito-bndes/models/equipamento-legado.model")
public class EquipamentoLegado {

	public Integer codigoEquipamento;
	public String descricaoEquipamento;
	public FabricanteLegado fabricante;
	public Integer sequencial;
	public String modelo;

	@IgnorarDaquiPraBaixo
	private EquipamentoLegado() {}

	public static EquipamentoLegado json() {
		return new EquipamentoLegado();
	}

	public EquipamentoLegado codigoEquipamento(Integer codigoEquipamento) {
		this.codigoEquipamento = codigoEquipamento;
		return this;
	}

	public EquipamentoLegado descricaoEquipamento(String descricaoEquipamento) {
		this.descricaoEquipamento = descricaoEquipamento;
		return this;
	}

	public EquipamentoLegado fabricante(FabricanteLegado fabricante) {
		this.fabricante = fabricante;
		return this;
	}

	public EquipamentoLegado sequencial(Integer sequencial) {
		this.sequencial = sequencial;
		return this;
	}

	public EquipamentoLegado modelo(String modelo) {
		this.modelo = modelo;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		EquipamentoLegado.json()
		.codigoEquipamento(0)
		.descricaoEquipamento("")
		.fabricante(null)
		.sequencial(0)
		.modelo("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(EquipamentoLegado.class, false);
		GerarJson.exec();
	}
}
