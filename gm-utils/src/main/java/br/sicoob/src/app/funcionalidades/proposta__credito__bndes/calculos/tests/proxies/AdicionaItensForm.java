package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.TipoInvestimentoWrapper;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.BuscarEquipamentosBndesComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.Formulario;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.EquipamentoLegado;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.FabricanteLegado;
import gm.languages.ts.angular.forms.FormGroup;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class AdicionaItensForm {
	
	public static FormGroup form;
	public static Formulario formulario;
	public static TipoInvestimentoWrapper tipo;

	public static void setValorUnitarioItem(double valor) {
		form.get("valorUnitarioItem").setValue(Num.fromNumber(2, valor).toDouble());		
	}

	public static void setValorTotalItem(double valor) {
		form.get("valorTotalItem").setValue(Num.fromNumber(2, valor).toDouble());
	}
	
	public static Num getValorTotalItem() {
		return Num.fromNumberObj(2, form.get("valorTotalItem").value);
	}
	
	public static void setDescricaoInformacaoAdicional(String s) {
		form.get("descInformacaoAdicional").setValue(s);
	}
	
	public static void setQuantidade(int qtd) {
		form.get("qtdItemInvestimento").setValue(qtd);		
	}
	
	public static Integer getQuantidade() {
		return (Integer) form.get("qtdItemInvestimento").value;
	}
	
	public static void setFabricante(FabricanteLegado fabricante) {
		BuscarEquipamentosBndesComponent.instance.form.get("codigoFabricante").setValue(fabricante.codigoFabricante);		
	}

	public static void setEquipamento(EquipamentoLegado equipamento) {
		BuscarEquipamentosBndesComponent.instance.form.get("codigoEquipamento").setValue(equipamento.codigoEquipamento);		
	}
	
	public static void submit() {
		formulario.onSubmit();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(AdicionaItensForm.class);
	}

}