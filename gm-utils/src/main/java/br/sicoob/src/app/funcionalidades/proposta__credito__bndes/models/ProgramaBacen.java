package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import br.sicoob.SicoobTranspilar;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

@From("@app/funcionalidades/proposta-credito-bndes/models/ProgramaBacen")
public class ProgramaBacen {
	
	public int id;
	public String text;
	public Array<SubProgramaBacen> subProgramas;
	
	public String getText() {
		return id + " - " + text;
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(ProgramaBacen.class);
	}
	
}