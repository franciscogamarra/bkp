package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class TiposInvestimento {

	public static final Array<TipoInvestimento> itens = new Array<>();
//	public static final Array<TipoInvestimento> itens2424 = new Array<>();
//	public static final Array<TipoInvestimento> itens2425 = new Array<>();
	public static final TipoInvestimento item1 = TipoInvestimento.json().id(1).descricao("Equipamentos Finamizáveis (cadastrados no CFI)").bolQtdTipoInvestimento(true).bolEquipamento(true).bolEquipamentoCredenciado(true).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item8 = TipoInvestimento.json().id(8).descricao("Serviço associado").bolQtdTipoInvestimento(false).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item9 = TipoInvestimento.json().id(9).descricao("Estudos e Projetos").bolQtdTipoInvestimento(true).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item14 = TipoInvestimento.json().id(14).descricao("Item 14").bolQtdTipoInvestimento(false).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item17 = TipoInvestimento.json().id(17).descricao("Softwares (Cadastrados no PROSOFT)").bolQtdTipoInvestimento(true).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item18 = TipoInvestimento.json().id(18).descricao("Item 18").bolQtdTipoInvestimento(true).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item32 = TipoInvestimento.json().id(32).descricao("Obras Civis e Instalações").bolQtdTipoInvestimento(true).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	public static final TipoInvestimento item34 = TipoInvestimento.json().id(34).descricao("Itens não financiáveis").bolQtdTipoInvestimento(true).bolEquipamento(false).bolEquipamentoCredenciado(false).bolPermiteSomenteUmItem(false).programaTipoInvestimentos(new Array<>()).bolCalculaIndiceParticipacaoBNDES(false).itensInvestimento(new Array<>());
	
	public static TipoInvestimento findById(int id) {
		Array<TipoInvestimento> list = itens.filter(i -> i.id == id);
		return list.length ? list.array(0) : null;
	}
	
	static {
		itens.push(item1);
		itens.push(item8);
		itens.push(item9);
		itens.push(item14);
		itens.push(item17);
		itens.push(item18);
		itens.push(item32);
		itens.push(item34);
//		itens2424.push(item1);
//		itens2424.push(item8);
//		itens2424.push(item17);
//		itens2424.push(item34);
//		itens2425.push(item1);
//		itens2425.push(item17);
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(TiposInvestimento.class);
	}
	
}
