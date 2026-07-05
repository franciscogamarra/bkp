package br.gerador.imovel;

import br.definicoes.Model;
import br.definicoes.Sistema;

// no back o dto se chama EmpRuralImovelDTO
public class Imovel {

	public static void exec(Sistema sistema) {
		
		Model model = sistema.newModelRapido(Imovel.class);
		
		model.front.pkg = "imovel";
		
		model.setOnNewCampo(campo -> {
			campo.front.edit.cols(3).readOnly();
		});
		
		model.newString("title", 100).readOnly();
		model.newString("areaImovelCompleta", 100).readOnly();
		model.newString("uf", 2).readOnly();
		model.newString("municipio", 100).readOnly();
		model.newBooleanFalse("urbano").readOnly();
		model.newInt("idLocalidade").readOnly().notNullFalse();
		
	}
	
}