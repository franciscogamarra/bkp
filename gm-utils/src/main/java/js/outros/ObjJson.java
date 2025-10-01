package js.outros;

import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.Any;
import gm.utils.comum.SystemPrint;
import gm.utils.map.MapSoFromObject;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import js.annotations.Support;

@Any @Support
public class ObjJson extends JS {

	/* para facilitar, pois muitos objetos tem o atributo id */
	public String id;
	
	private Atributos as;
	
	private void init() {
		if (as == null) {
			as = AtributosBuild.get(getClass());
		}
	}
	
	public final <T> T array(String key) {
		init();
		Atributo a = as.get(key);
		if (a == null) {
			return null;
		} else {
			return a.get(this);
		}
	}

	public final void arraySet(String key, Object value) {
		init();
		Atributo a = as.get(key);
		if (a != null) {
			a.set(this, value);
		}
	}
	
	public static ObjJson json() {
		return new ObjJson();
	}
	
	@Override
	public String toString() {
		return MapSoFromObject.get(this).asJson().toString("\n");
	}

	public void print() {
		SystemPrint.ln(this);
	}
	
}
