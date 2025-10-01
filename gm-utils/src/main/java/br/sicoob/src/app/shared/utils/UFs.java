package br.sicoob.src.app.shared.utils;

import br.sicoob.src.app.funcionalidades.proposta_credito_bndes.models.unidade_federativa.model.ts.UnidadeFederativa;
import gm.languages.ts.javaToTs.JS;
import js.array.Array;
import src.commom.utils.array.Itens;

public class UFs extends JS {

	public static final Itens<UnidadeFederativa> itens = new Itens<>();
	
	public static void setArrayLst(Array<UnidadeFederativa> os) {
		
		os.forEach(i -> {
			
			UnidadeFederativa o = getBySigla(i.siglaUF);
			
			if (isNull_(o)) {
				itens.add(i);
			} else if (o != i) {
				o.nomeUF = i.nomeUF;
			}
			
		});
		
	}
	
	public static UnidadeFederativa getBySigla(String sigla) {
		return itens.unique(i -> i.siglaUF == sigla);
	}
	
}
