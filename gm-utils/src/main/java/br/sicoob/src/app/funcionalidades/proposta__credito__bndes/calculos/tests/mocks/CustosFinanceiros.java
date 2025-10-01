package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.CustoFinanceiro;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class CustosFinanceiros {
	public static final CustoFinanceiro o1026 = CustoFinanceiro.json().id(1026).descCustoFinanceiro("Taxa Fixa BNDES - TFB").descComplementar("Taxa Fixa do BNDES - TFB");
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(CustosFinanceiros.class);
	}
	
}
