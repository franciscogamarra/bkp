package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.PcbTest001;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.CustosFinanceiros;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import br.sicoob.src.app.shared.forms.StateForm;
import br.sicoob.src.app.shared.forms.StateFormControl;
import br.sicoob.src.app.shared.utils.Asserts;
import br.sicoob.src.app.shared.utils.ChaveValor;
import gm.utils.classes.UClass;
import src.commom.utils.array.Itens;
import src.commom.utils.comum.Print;
import src.commom.utils.comum.SeparaMilhares;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringExtraiCaracteresBaseadoEmString;
import src.commom.utils.string.StringObrig;
import src.commom.utils.string.StringSplit;

public class Transpilar {

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbFinanciamentoDistribuicao.class);
		SicoobTranspilar.exec(PcbCols.class);
		SicoobTranspilar.exec(PcbCalculo.class);
		SicoobTranspilar.exec(PcbFinanciamentoDistribuicaoItem.class);
		SicoobTranspilar.exec(ChaveValor.class);
		SicoobTranspilar.exec(Asserts.class);
		SicoobTranspilar.exec(ItemInvestimentoWrapper.class);
		SicoobTranspilar.exec(TipoInvestimentoWrapper.class);
		SicoobTranspilar.exec(ProgramaWrapper.class);
		SicoobTranspilar.exec(Num.class);
		SicoobTranspilar.exec(Erro.class);
		SicoobTranspilar.exec(Print.class);
		SicoobTranspilar.exec(Itens.class);
		SicoobTranspilar.exec(StateForm.class);
		SicoobTranspilar.exec(StateFormControl.class);
		SicoobTranspilar.exec(StringBeforeFirst.class);
		SicoobTranspilar.exec(StringAfterFirst.class);
		SicoobTranspilar.exec(SeparaMilhares.class);
		SicoobTranspilar.exec(StringContains.class);
		SicoobTranspilar.exec(StringObrig.class);
		SicoobTranspilar.exec(StringSplit.class);
		SicoobTranspilar.exec(StringExtraiCaracteresBaseadoEmString.class);
		UClass.classesDaMesmaPackage(CustosFinanceiros.class).forEach(i -> SicoobTranspilar.exec(i));
		UClass.classesDaMesmaPackage(PcbTest001.class).forEach(i -> SicoobTranspilar.exec(i));
		UClass.classesDaMesmaPackage(AdicionaItensForm.class).forEach(i -> SicoobTranspilar.exec(i));
	}
	
}
