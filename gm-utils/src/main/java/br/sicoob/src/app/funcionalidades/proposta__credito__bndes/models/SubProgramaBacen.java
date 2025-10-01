package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import br.sicoob.SicoobTranspilar;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

@From("@app/funcionalidades/proposta-credito-bndes/models/SubProgramaBacen")
public class SubProgramaBacen {
	
	public int id;
	public String text;
	
	public String getText() {
		return id + " - " + text;
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(SubProgramaBacen.class);
	}
	
}