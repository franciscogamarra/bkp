package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.exception.NaoImplementadoException;
import js.annotations.NaoConverter;

@NaoConverter @ImportStatic
@From("@app/components/calculadora-operacoes/calculadora-operacoes.component")
public class CalculadoraOperacoesComponent {
	
	public static final CalculadoraOperacoesComponent instance = new CalculadoraOperacoesComponent();
	public FormGroup formCalculadora = new FormGroup(null);
	
	private CalculadoraOperacoesComponent() {
		formCalculadora.addControl("valorCredito", new FormControl());
	}

	public void onSubmit() {
//		AbaOperacaoComponent.instance.ajustaPercentual
		throw new NaoImplementadoException();
	}
	
}
