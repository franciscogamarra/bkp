package src.commom.utils.comum;

import gm.languages.ts.javaToTs.JavaToTs;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Js;
import js.html.Element;
import js.support.console;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;

public class Dom {
	
	public static Element getById(String id) {
		Element o = Js.document.getElementById(id);
		return Null.is(o) ? null : o;
	}
	
	public static boolean exists(String id) {
		return getById(id) != null;
	}
	
	private static void focusIdTry(String id, int vez) {
		
		Element o = getById(id);
		
		if (Null.is(o)) {
			
			if (vez > 10) {
				console.error("Não foi possível encontrar o elemento com id " + id);
			} else {
				Js.setTimeout(() -> focusIdTry(id, vez+1), 100);
			}
			
		} else {
			o.focus();
		}
		
		
	}

	public static void focusId(String id) {
		if (StringEmpty.is(id)) {
			return;
		}
		focusIdTry(id, 0);
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		JavaToTs.execJavaToTs(Dom.class).print();
	}

}