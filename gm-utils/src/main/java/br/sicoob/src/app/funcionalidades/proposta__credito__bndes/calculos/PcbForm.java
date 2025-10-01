package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.LinhaBndes;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Produto;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import br.sicoob.src.app.shared.forms.StateForm;
import br.sicoob.src.app.shared.forms.StateFormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import lombok.Getter;

@Getter
public class PcbForm {

	private StateForm stateForm;
	private StateFormControl itensInvestimento;
	private StateFormControl percentualFinanciado;
	private StateFormControl percentualRecursoProprio;
	private StateFormControl valorFinanciadoOut;
	private StateFormControl valorRecursosProprios;
	private StateFormControl percTotalInvestimento;
	private StateFormControl valorTotalInvestimento;
	private ProgramaWrapper programa;
	private StateFormControl produto;
	private StateFormControl linhaBndes;

	public PcbForm(FormGroup form) {
		
		stateForm = new StateForm(() -> form);
		itensInvestimento = stateForm.build("itensInvestimento");
		percentualFinanciado = stateForm.build("percFinanciada");
		percentualRecursoProprio = stateForm.build("percRecursoProprio");
		valorFinanciadoOut = stateForm.build("valorFinanciado");
		valorRecursosProprios = stateForm.build("valorRecursosProprios");
		percTotalInvestimento = stateForm.build("percTotalInvestimento");
		valorTotalInvestimento = stateForm.build("valorTotalInvestimento");
		produto = stateForm.build("produto");
		linhaBndes = stateForm.build("linhaBndes");
		programa = new ProgramaWrapper(stateForm.build("programa"));
		
	}
	
	public void setProduto(Produto produto) {
		this.produto.set(produto.id);
	}

	public void setLinhaBndes(LinhaBndes linhaBndes) {
		this.linhaBndes.set(linhaBndes.id);
	}

	public void setPrograma(Programa programa) {
		this.programa.set(programa);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbForm.class);
	}
	
}
