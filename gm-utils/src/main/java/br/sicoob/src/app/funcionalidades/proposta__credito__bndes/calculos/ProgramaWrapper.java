package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import br.sicoob.src.app.shared.forms.StateFormControl;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.object.Null;

public class ProgramaWrapper {

	private StateFormControl state;
	private int ultimoIdCalculado = 0;

	public ProgramaWrapper(StateFormControl state) {
		this.state = state;
	}
	
	private Programa get() {
		return (Programa) state.get();
	}
	
	public void set(Programa value) {
		state.set(value);
	}
	
	private boolean isNull() {
		return Null.is(get());
	}
	
	public boolean exigeInvestimentos() {
		return !isNull() && get().bolExigeItemInvestimento;
	}
	
	public int getId() {
		return isNull() ? 0 : get().id;
	}
	
	public boolean mudouDesdeOUltimoCalculo() {
		if (ultimoIdCalculado == getId()) {
			return false;
		} else {
			ultimoIdCalculado = getId();
			return true;
		}
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(ProgramaWrapper.class);
	}
}
