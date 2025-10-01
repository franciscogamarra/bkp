package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.PrazoTotalOperacao;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class PrazosTotaisOperacao {
	
	public static final Array<PrazoTotalOperacao> itens = new Array<>();
	
	static {
		itens.push(PrazoTotalOperacao.json().id(1).sigla("TFB_36").descricao("Até 36 meses").prazoTotalMes(36));
		itens.push(PrazoTotalOperacao.json().id(2).sigla("TFB_60").descricao("Acima de 36 até 60 meses").prazoTotalMes(60));
		itens.push(PrazoTotalOperacao.json().id(3).sigla("TFB_84").descricao("Acima de 60 até 84 meses").prazoTotalMes(84));
		itens.push(PrazoTotalOperacao.json().id(4).sigla("TFB_120").descricao("Acima de 84 a 120 meses").prazoTotalMes(120));
	}

	@IgnorarDaquiPraBaixo
	public static void main(String[] args) {
		SicoobTranspilar.exec(PrazosTotaisOperacao.class);
	}
	
}
