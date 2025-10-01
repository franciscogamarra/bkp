package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.EquipamentoLegado;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class EquipamentosLegados {

	public static final Array<EquipamentoLegado> itens = new Array<>();
	public static final EquipamentoLegado item1 = EquipamentoLegado.json().codigoEquipamento(619183).descricaoEquipamento("CSM EQUIPLATING").fabricante(FabricantesLegados.CSM).modelo("BETONEIRA 1 TRAÇO PROFISSIONAL 400L C/MOTOR P").sequencial(1);
	public static final EquipamentoLegado item2 = EquipamentoLegado.json().codigoEquipamento(1164961).descricaoEquipamento("ARGAMASSADEIRA").fabricante(FabricantesLegados.CSM).modelo("20 LITROS").sequencial(2);
	public static final EquipamentoLegado item3 = EquipamentoLegado.json().codigoEquipamento(1539860).descricaoEquipamento("GUINCHO DE COLUNA").fabricante(FabricantesLegados.CSM).modelo("GUINCHO DE COLUNA 220KG").sequencial(3);
//	http://localhost:9080/cre-concessao-bndes-api-web/api/equipamentos-legado?termo=null&codigoFabricante=76840537000121&equipamentoNovoUsado=false
	static {
		itens.push(item1);
		itens.push(item2);
		itens.push(item3);
	}	
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(EquipamentosLegados.class);
	}

}