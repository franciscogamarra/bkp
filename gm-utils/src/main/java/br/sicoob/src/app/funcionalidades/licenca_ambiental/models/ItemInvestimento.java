package br.sicoob.src.app.funcionalidades.licenca_ambiental.models;

import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.JSon;
import js.annotations.TypeJs;

@TypeJs @ImportStatic @From("@app/funcionalidades/proposta-credito-bndes/models/item-investimento.model")
public class ItemInvestimento {

	public Integer id;
	public Integer qtdItemInvestimento;
	public String descInformacaoAdicional;
	public String descricaoEquipamento;
	public String descricaoFabricante;
	public Integer codProdutoBNDES;
	public Double valorUnitarioItem;
	public Double valorUnitarioFinanciadoItem;
	public Double valorTotalFinanciadoItem;
	public Double valorTotalItem;

	@IgnorarDaquiPraBaixo
	private ItemInvestimento() {}

	public static ItemInvestimento json() {
		return new ItemInvestimento();
	}

	public ItemInvestimento id(Integer id) {
		this.id = id;
		return this;
	}

	public ItemInvestimento qtdItemInvestimento(Integer qtdItemInvestimento) {
		this.qtdItemInvestimento = qtdItemInvestimento;
		return this;
	}

	public ItemInvestimento descInformacaoAdicional(String descInformacaoAdicional) {
		this.descInformacaoAdicional = descInformacaoAdicional;
		return this;
	}

	public ItemInvestimento descricaoEquipamento(String descricaoEquipamento) {
		this.descricaoEquipamento = descricaoEquipamento;
		return this;
	}

	public ItemInvestimento descricaoFabricante(String descricaoFabricante) {
		this.descricaoFabricante = descricaoFabricante;
		return this;
	}

	public ItemInvestimento codProdutoBNDES(Integer codProdutoBNDES) {
		this.codProdutoBNDES = codProdutoBNDES;
		return this;
	}

	public ItemInvestimento valorUnitarioItem(Double valorUnitarioItem) {
		this.valorUnitarioItem = valorUnitarioItem;
		return this;
	}

	public ItemInvestimento valorUnitarioFinanciadoItem(Double valorUnitarioFinanciadoItem) {
		this.valorUnitarioFinanciadoItem = valorUnitarioFinanciadoItem;
		return this;
	}

	public ItemInvestimento valorTotalFinanciadoItem(Double valorTotalFinanciadoItem) {
		this.valorTotalFinanciadoItem = valorTotalFinanciadoItem;
		return this;
	}

	public ItemInvestimento valorTotalItem(Double valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		ItemInvestimento.json()
		.id(0)
		.qtdItemInvestimento(0)
		.descInformacaoAdicional("")
		.descricaoEquipamento("")
		.descricaoFabricante("")
		.codProdutoBNDES(0)
		.valorUnitarioItem(null)
		.valorUnitarioFinanciadoItem(null)
		.valorTotalFinanciadoItem(null)
		.valorTotalItem(null)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(ItemInvestimento.class, false);
		GerarJson.exec();
	}

	@Override
	public String toString() {
		return JSon.toJson(this);
	}
}
