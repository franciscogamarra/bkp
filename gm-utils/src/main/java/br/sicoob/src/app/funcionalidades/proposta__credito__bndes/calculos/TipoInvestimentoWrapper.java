package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.shared.utils.Utils;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.ToJson;
import gm.utils.lambda.F1;
import js.array.Array;
import src.commom.utils.object.Null;

public class TipoInvestimentoWrapper {
	
	public TipoInvestimento value;

	public Array<ItemInvestimentoWrapper> itens = new Array<>();

	public String label;
	
	public PcbCols titles = new PcbCols();
	public PcbCols totais = new PcbCols();

	public int index;
	public int id;

	private static Array<TipoInvestimentoWrapper> instances = new Array<>();
	
	public static void clearInstances() {
		while (instances.length) {
			instances.pop();
		}
	}
	
	public static TipoInvestimentoWrapper get(TipoInvestimento tipoInvestimento) {
		
		if (Utils.isNull(tipoInvestimento)) {
			throw new Error("tipoInvestimento is null");
		}
		
		TipoInvestimentoWrapper o = instances.find(i -> i.value == tipoInvestimento);
		if (Null.is(o)) {
			o = instances.find(i -> i.value.id == tipoInvestimento.id);
			if (Null.is(o)) {
				o = new TipoInvestimentoWrapper(tipoInvestimento);
				instances.push(o);
			} else {
				o.value = tipoInvestimento;
			}
		}
		
		o.refresh();
		
		return o;
	}
	
	private TipoInvestimentoWrapper(TipoInvestimento tipoInvestimento) {
		this.value = tipoInvestimento;
		id = this.value.id;
	}
	
	private void refresh() {
		
		Array<ItemInvestimentoWrapper> itensOriginais = itens;
		
		itens = new Array<>();
		
		if (possuiItens()) {
			value.itensInvestimento.forEach(/*ItemInvestimento*/item -> {
				if (!Null.is(item)) {
					itens.push(ItemInvestimentoWrapper.get(this, item, itensOriginais));
				}
			});
		}

		titles.col7 = "Total Financiado";
		titles.col8 = "Recursos Próprios";
		titles.col9 = "Ações";
		
		if (isValores()) {
			titles.col5 = "Valor Bruto do Item";
		} else {

			titles.col4 = "Valor Unitário";
			titles.col5 = "Valor Total";
			titles.col6 = "Unitário Financiado";
			
			titles.col3 = "Quantidade";
			if (isEquipamento()) {
				titles.col1 = "Equipamento";
				titles.col2 = "Fabricante";
			}

			totais.col2 = "Totais:";
			totais.col3 = getQuantidade() + "";
			totais.col4 = getSumValorUnitario().formatRs();
			totais.col6 = getValorUnitarioFinanciado().formatRs();
			
		}
		
		totais.col5 = getValorTotal().formatRs();
		totais.col7 = getValorTotalFinanciado().formatRs();
		totais.col8 = getValorRecursosProprios().formatRs();
		
	}
	
	public void setIndex(int index) {
		this.index = index;
		label = "Tipo Investimento Nº " + (index + 1);
	}
	
	public boolean isDescricaoPreenchida() {
		return !Null.is(value.id) && value.id > 0;
	}
	
	public String getLabel() {
		String s = label;
		if (isDescricaoPreenchida()) {
			s += " - " + value.id + " - " + value.descricao;
		}
		return s;
	}

	public boolean isValid() {
		return !Utils.isNull(value) && !Utils.isNull(value.id);
	}
	
	public boolean isFinanciavel() {
		return isValid() && value.id != 34;
	}
	
	private Num sum(F1<ItemInvestimentoWrapper, Num> get) {
		if (possuiItens()) {
			return Num.sum(itens, get);
		} else {
			return Num.ZERO;
		}
	}
	
	public Num getQuantidade() {
		return sum((/*ItemInvestimentoWrapper*/item) -> item.getQuantidade());
	}

	public Num getSumValorUnitario() {
		return sum((/*ItemInvestimentoWrapper*/item) -> item.getUnitario());
	}

	public Num getValorUnitarioFinanciado() {
		return sum((/*ItemInvestimentoWrapper*/item) -> item.getUnitarioFinanciado());
	}

	public Num getValorTotal() {
		return sum((/*ItemInvestimentoWrapper*/item) -> item.getTotal());
	}

	public Num getValorTotalFinanciado() {
		return sum((/*ItemInvestimentoWrapper*/item) -> item.getTotalFinanciado());
	}

	public Num getValorRecursosProprios() {
		return sum((/*ItemInvestimentoWrapper*/item) -> item.getRecursosProprios());
	}
	
	public boolean isValores() {
		return value.bolEquipamento == false && value.bolQtdTipoInvestimento == false;
	}

	public boolean isEquipamento() {
		return value.bolEquipamento;
	}
	
	public boolean isQuantidades() {
		return value.bolQtdTipoInvestimento && value.bolEquipamento == false;
	}
	
	public int getQtdItens() {
		if (isValid() && !Utils.isNull(value.itensInvestimento)) {
			return value.itensInvestimento.lengthArray();
		} else {
			return 0;
		}
	}

	public int getQtdItensMaxima() {
		return isValid() ? (isValores() ? 1 : 99) : 0;
	}
	
	public boolean possuiItens() {
		return getQtdItens() > 0;
	}
	
	public boolean podeAdicionarItens() {
		return isValid() && getQtdItens() < getQtdItensMaxima();
	}
	
	public boolean exibirTotais() {
		return getQtdItens() > 1;
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(TipoInvestimentoWrapper.class);
	}
	
	@Override
	public String toString() {
		return ToJson.get(this).toString("");
	}
	
}