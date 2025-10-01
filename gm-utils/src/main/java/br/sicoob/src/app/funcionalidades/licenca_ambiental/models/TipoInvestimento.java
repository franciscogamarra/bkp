package br.sicoob.src.app.funcionalidades.licenca_ambiental.models;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.ProgramaTipoInvestimento;
import gm.languages.ts.javaToTs.GerarJson;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.JSon;
import js.annotations.TypeJs;
import js.array.Array;

@TypeJs @ImportStatic @From("@funcionalidades/proposta-credito-bndes/models/tipo-investimento.model")
public class TipoInvestimento {

	public Integer id;
	public Array<ItemInvestimento> itensInvestimento;
	public Array<ProgramaTipoInvestimento> programaTipoInvestimentos;
	public String descricao;
	public Boolean bolQtdTipoInvestimento;
	public Boolean bolEquipamento;
	public Boolean bolEquipamentoCredenciado;
	public Boolean bolCalculaIndiceParticipacaoBNDES;
	public Boolean bolPermiteSomenteUmItem;

	@IgnorarDaquiPraBaixo
	private TipoInvestimento() {}

	public static TipoInvestimento json() {
		return new TipoInvestimento();
	}

	public TipoInvestimento id(Integer id) {
		this.id = id;
		return this;
	}

	public TipoInvestimento itensInvestimento(Array<ItemInvestimento> itensInvestimento) {
		this.itensInvestimento = itensInvestimento;
		return this;
	}

	public TipoInvestimento programaTipoInvestimentos(Array<ProgramaTipoInvestimento> programaTipoInvestimentos) {
		this.programaTipoInvestimentos = programaTipoInvestimentos;
		return this;
	}

	public TipoInvestimento descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public TipoInvestimento bolQtdTipoInvestimento(Boolean bolQtdTipoInvestimento) {
		this.bolQtdTipoInvestimento = bolQtdTipoInvestimento;
		return this;
	}

	public TipoInvestimento bolEquipamento(Boolean bolEquipamento) {
		this.bolEquipamento = bolEquipamento;
		return this;
	}

	public TipoInvestimento bolEquipamentoCredenciado(Boolean bolEquipamentoCredenciado) {
		this.bolEquipamentoCredenciado = bolEquipamentoCredenciado;
		return this;
	}

	public TipoInvestimento bolCalculaIndiceParticipacaoBNDES(Boolean bolCalculaIndiceParticipacaoBNDES) {
		this.bolCalculaIndiceParticipacaoBNDES = bolCalculaIndiceParticipacaoBNDES;
		return this;
	}

	public TipoInvestimento bolPermiteSomenteUmItem(Boolean bolPermiteSomenteUmItem) {
		this.bolPermiteSomenteUmItem = bolPermiteSomenteUmItem;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		TipoInvestimento.json()
		.id(0)
		.itensInvestimento(null)
		.programaTipoInvestimentos(null)
		.descricao("")
		.bolQtdTipoInvestimento(true)
		.bolEquipamento(true)
		.bolEquipamentoCredenciado(true)
		.bolCalculaIndiceParticipacaoBNDES(true)
		.bolPermiteSomenteUmItem(true)
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(TipoInvestimento.class, false);
		GerarJson.exec();
	}

	@Override
	public String toString() {
		return JSon.toJson(this);
	}
}
