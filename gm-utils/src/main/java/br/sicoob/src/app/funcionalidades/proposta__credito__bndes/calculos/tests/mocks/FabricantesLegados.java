package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.FabricanteLegado;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class FabricantesLegados {

	public static final Array<FabricanteLegado> itens = new Array<>();
	public static final FabricanteLegado CSM = FabricanteLegado.json().codigoFabricante(76840537000121L).descricaoFabricante("CSM EQUIPLATING");
	public static final FabricanteLegado item2 = FabricanteLegado.json().codigoFabricante(103787000117L).descricaoFabricante("EQUIPLATING INDUSTRIA E COMERCIO DE MAQUINAS E EQUIPAME");
	public static final FabricanteLegado item3 = FabricanteLegado.json().codigoFabricante(76609000144L).descricaoFabricante("JUPLASTT INDUSTRIA E COMERCIO DE MAQUINAS LTDA         ");
	public static final FabricanteLegado item4 = FabricanteLegado.json().codigoFabricante(104890000181L).descricaoFabricante("PACTO MAQUINAS LTDA                                    ");
//	http://localhost:9080/cre-concessao-bndes-api-web/api/equipamentos-legado?termo=null&codigoFabricante=76840537000121&equipamentoNovoUsado=false
	static {
		itens.push(CSM);
		itens.push(item2);
		itens.push(item3);
		itens.push(item4);
	}	
	
	@IgnorarDaquiPraBaixo
	public static void main(String[] args) {
		SicoobTranspilar.exec(FabricantesLegados.class);
	}

}