package br.gerador.support;

import br.definicoes.Model;
import br.definicoes.Sistema;

// no back o dto se chama EmpRuralImovelDTO
public class BuscaPessoa {

	public static void exec(Sistema sistema) {
		
		Model model = sistema.newModelRapido(BuscaPessoa.class);
		
		model.front.pkg = "imovel";
		
		model.setOnNewCampo(campo -> {
			campo.front.edit.cols(3);
		});
		
		model.newCpfOuCnpj("cpfOuCnpj").titulo("Proprietário");
		
	}
	
}