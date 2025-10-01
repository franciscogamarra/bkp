package gm.utils.jpa;

import java.util.List;

import gm.utils.abstrato.ExtraidId;
import gm.utils.abstrato.IdCompare;
import gm.utils.abstrato.ObjetoComId;
import gm.utils.classes.UClass;
import gm.utils.comum.UAssert;
import gm.utils.comum.UType;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import src.commom.utils.integer.IntegerParse;

public class UIdObject {

//	public static Integer getId(IEntity<?> o) {
//		if (o == null) return null;
//		return o.getId();
//	}

	public static Integer getId(ObjetoComId<?> o) {
		return IntegerParse.toInt(ExtraidId.exec(o));
	}
//	public static String getText(IdObject o) {
//		if (o == null) {
//			return null;
//		} else {
//			return o.getText();
//		}
//	}

	public static Integer getId(Object o) {

		UAssert.notEmpty(o, "o == null");

		if (UType.isPrimitiva(o) || (o instanceof List)) {
			return null;
		}

		// Method method = Utils.getMethod("getId", o.getClass());
		//
		// if ( method == null ) {
		// method = Utils.getMethod("getId" + o.getClass().getSimpleName(),
		// o.getClass());
		// }
		//
		// if ( method != null ) {
		// return Utils.getValue(method, o);
		// }

		Atributos atributos = AtributosBuild.get(UClass.getClass(o));
		Atributo atributoId = atributos.getId();
		if (atributoId == null) {
			return null;
		}
		return atributoId.getInt(o);
	}

	public static void checaObrig(Object o, String nome, int id) {
		if (o == null) {
			throw UException.runtime( String.format("Não encontrado: %s -> %d", nome, id));
		}
	}

	public static boolean eq(ObjetoComId<?> a, ObjetoComId<?> b) {
		return IdCompare.eq(a, b);
	}
	public static boolean ne(ObjetoComId<?> a, ObjetoComId<?> b) {
		return !eq(a, b);
	}

}
