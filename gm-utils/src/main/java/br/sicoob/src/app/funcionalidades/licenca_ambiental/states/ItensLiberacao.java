package br.sicoob.src.app.funcionalidades.licenca_ambiental.states;

import br.sicoob.SicoobDeploy;
import br.sicoob.SicoobTranspilar;
import br.sicoob.src.bla.ControleItemLiberacao;
import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.JsObject;
import js.array.Array;
import src.commom.utils.object.Null;

@ImportStatic
@From("@app/funcionalidades/licenca-ambiental/states/ItensLiberacao")
public class ItensLiberacao extends JS {
	
	public static Array<ControleItemLiberacao> value = new Array<ControleItemLiberacao>();
	
	public static void set(Array<ControleItemLiberacao> itens) {
		
		if (Null.is(itens)) {
			ItensLiberacao.value = new Array<ControleItemLiberacao>();
		} else {
			ItensLiberacao.value = itens.map(i -> (as(ppp(i), ControleItemLiberacao.class)));
		}
		
	}
	
	public static Array<ControleItemLiberacao> get() {
		return ItensLiberacao.value;
	}
	
	public static boolean isEmpty() {
		return !ItensLiberacao.get().length;
	}

	public static void add(ControleItemLiberacao o) {
		value.push(o);
	}

	public static void remove(int index) {
		value.splice(index, 1);
	}

	public static void change(int index, ControleItemLiberacao o) {
		JsObject.assign(ItensLiberacao.value.array(index), o);
	}
	
	public static void clear() {
		ItensLiberacao.value = new Array<ControleItemLiberacao>();
	}

	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(ItensLiberacao.class);
		SicoobDeploy.main(args);
	}

//	this.controleItem
	
}