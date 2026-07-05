package br.gerador.support;

import br.definicoes.Model;
import br.definicoes.Sistema;

// no back o dto se chama EmpRuralImovelDTO
public class PessoaSimples {

	public static void exec(Sistema sistema) {
		
		Model model = sistema.newModelRapido(PessoaSimples.class);
		
		model.setOnNewCampo(campo -> {
			campo.front.edit.cols(3);
		});
		
		model.newCpfOuCnpj("cpfCnpj").readOnly();
		model.newString("nome", 100).readOnly();

	}
	
}