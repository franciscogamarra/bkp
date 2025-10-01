package br.sicoob;

import br.sicoob.src.SicoobSrc;
import gm.languages.ts.GetClassesUtilizadas;
import gm.utils.classes.ListClass;

public class GetClasses {

	public static ListClass all() {
		return GetClassesUtilizadas.all(SicoobSrc.class);
	}
	
	public static void main(String[] args) {
		all().print();
	}
	
}
