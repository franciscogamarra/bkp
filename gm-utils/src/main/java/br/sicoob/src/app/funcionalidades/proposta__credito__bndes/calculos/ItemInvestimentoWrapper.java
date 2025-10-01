package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.ItemInvestimento;
import br.sicoob.src.app.shared.utils.Utils;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;

public class ItemInvestimentoWrapper {
	
//	private static int idCount = 0;
	
	public PcbCols values = new PcbCols();
	
	private TipoInvestimentoWrapper tipo;
	public ItemInvestimento value;

	public static ItemInvestimentoWrapper get(TipoInvestimentoWrapper tipo, ItemInvestimento item, Array<ItemInvestimentoWrapper> itensOriginais) {
		ItemInvestimentoWrapper o = itensOriginais.find(i -> i.value == item && i.tipo == tipo);
		if (Null.is(o)) {
			o = new ItemInvestimentoWrapper(tipo, item);
		}
		o.refresh();
		return o;
	}

	private ItemInvestimentoWrapper(TipoInvestimentoWrapper tipo, ItemInvestimento itemInvestimento) {

		if (Utils.isNull(itemInvestimento)) {
			throw new Error("itemInvestimento is null");
		}
		
		this.tipo = tipo;
		this.value = itemInvestimento;
//		this.value.idWrapper = idCount++; 
		
	}
	
	private void refresh() {
		
		values.col5 = getTotal().formatRs();
		values.col7 = getTotalFinanciado().formatRs();
		values.col8 = getRecursosProprios().formatRs();
		
		if (!tipo.isValores()) {

			values.col4 = getUnitario().formatRs();
			values.col6 = getUnitarioFinanciado().formatRs();
			
			values.col3 = value.qtdItemInvestimento + "";
			
			if (tipo.isEquipamento()) {
				
				if (Null.is(value.codProdutoBNDES) && StringEmpty.is(value.descricaoEquipamento)) {
					values.col1 = "N/A";
				} else if (Null.is(value.codProdutoBNDES)) {
					values.col1 = value.descricaoEquipamento.trim();
				} else if (StringEmpty.is(value.descricaoEquipamento)) {
					values.col1 = value.codProdutoBNDES + "";
				} else {
					values.col1 = value.codProdutoBNDES + " - " + value.descricaoEquipamento.trim();
				}
				
				values.col2 = StringEmpty.is(value.descricaoFabricante) ? "N/A" : value.descricaoFabricante.trim();
				
			}
			
		}
		
	}
	
	public Num getQuantidade() {
		Num x = Num.fromNumber(0, value.qtdItemInvestimento);
		if (x.menor(Num.UM)) {
			return Num.UM;
//			throw new Error("Quantidade menor que 1 : " + x);
		}
		return x;
	}
	
	public Num getUnitario() {
		return Num.fromNumber(2, value.valorUnitarioItem);
	}
	
	public Num getTotal() {
		return getQuantidade().vezes(getUnitario());
	}

	public Num getUnitarioFinanciado() {
		return Num.fromNumber(2, value.valorUnitarioFinanciadoItem);
	}

	public Num getTotalFinanciado() {
		return Num.fromNumber(2, value.valorTotalFinanciadoItem);
	}

	public Num getTotalFinanciadoCalculado() {
		return getQuantidade().vezes(getUnitarioFinanciado());
	}
	
	public Num getRecursosProprios() {
		return getTotal().menos(getTotalFinanciado());
	}
	
	public void setUnitarioFinanciado(Num valor) {
		value.valorUnitarioFinanciadoItem = valor.toDouble();
	}
	
	public void setTotalFinanciado(Num valor) {
		value.valorTotalFinanciadoItem = valor.toDouble();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(ItemInvestimentoWrapper.class);
	}

}
