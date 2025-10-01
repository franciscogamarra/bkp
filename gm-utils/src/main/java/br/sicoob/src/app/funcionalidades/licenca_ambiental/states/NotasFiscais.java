package br.sicoob.src.app.funcionalidades.licenca_ambiental.states;

import br.sicoob.SicoobDeploy;
import br.sicoob.SicoobTranspilar;
import br.sicoob.src.bla.NotaFiscalBndes;
import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.JsObject;
import js.array.Array;
import src.commom.utils.object.Null;

@ImportStatic
@From("@app/funcionalidades/licenca-ambiental/states/NotasFiscais")
public class NotasFiscais extends JS {
	
	public static Array<NotaFiscalBndes> value = new Array<NotaFiscalBndes>();

	public static void set(Array<NotaFiscalBndes> nfs) {
		
		if (Null.is(nfs)) {
			NotasFiscais.value = new Array<NotaFiscalBndes>();
		} else {
			NotasFiscais.value = nfs.map(i -> (as(ppp(i), NotaFiscalBndes.class)));
		}
		
	}

	public static Array<NotaFiscalBndes> get() {
		return NotasFiscais.value;
	}
	
	public static boolean isEmpty() {
		return !NotasFiscais.get().length;
	}

	public static void add(NotaFiscalBndes o) {
		value.push(o);
	}

	public static void remove(int index) {
		value.splice(index, 1);
	}

	public static void change(int index, NotaFiscalBndes o) {
		JsObject.assign(NotasFiscais.value.array(index), o);
	}

	public static void clear() {
		NotasFiscais.value = new Array<NotaFiscalBndes>();
	}

	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(NotasFiscais.class);
		SicoobDeploy.main(args);
	}
	
}