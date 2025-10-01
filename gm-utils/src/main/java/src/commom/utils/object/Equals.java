package src.commom.utils.object;

import js.Js;
import js.JsObject;
import js.array.Array;
import src.commom.utils.string.StringEmpty;

public class Equals {
	
	public static boolean is(Object a, Object b) {
		return a == b || (Null.is(a) && Null.is(b));
	}

	public static boolean deep(Object a, Object b, boolean considerarStringsVaziasComoNulas, boolean considerarArraysVaziosComoNulos) {
		
		if (is(a, b)) {
			return true;
		}
		
		if (considerarStringsVaziasComoNulas) {
			a = getStringTratada(a);
			b = getStringTratada(b);
			if (is(a, b)) {
				return true;
			}
		}
		
		if (considerarArraysVaziosComoNulos) {
			a = getArrayTratado(a);
			b = getArrayTratado(b);
			if (is(a, b)) {
				return true;
			}
		}

		if (Null.is(a) || Null.is(b)) {
			return false;
		}

		if (Js.typeof(a) != Js.typeof(b)) {
			return false;
		}
		
		if (Js.typeof(a) != "object") {
			return false;
		}
		
		if (Array.isArray(a)) {
			
			if (!Array.isArray(b)) {
				return false;
			}
			
			Array<?> arraya = (Array<?>) a;
			Array<?> arrayb = (Array<?>) b;
			
			if (arraya.lengthArray() != arrayb.lengthArray()) {
				return false;
			}
			
			for (int i = 0; i < arraya.lengthArray(); i++) {
				Object aa = arraya.array(i);
				Object bb = arrayb.array(i);
				if (!deep(aa, bb, considerarStringsVaziasComoNulas, considerarArraysVaziosComoNulos)) {
					return false;
				}
			}
			
			return true;
			
		}
		
		return deepKeys(a, b, considerarStringsVaziasComoNulas, considerarArraysVaziosComoNulos) && deepKeys(b, a, considerarStringsVaziasComoNulas, considerarArraysVaziosComoNulos);
		
	}
	
	private static Object getArrayTratado(Object o) {
		if (Array.isArray(o)) {
			Array<?> array = (Array<?>) o;
			if (!array.length) {
				return null;
			}
		}
		return o;
	}

	private static Object getStringTratada(Object o) {
		if (Js.typeof(o) == "string") {
			String s = (String) o;
			if (StringEmpty.is(s)) {
				return null;
			}
		}
		return o;
	}

	private static boolean deepKeys(Object a, Object b, boolean considerarStringsVaziasComoNulas, boolean considerarArraysVaziosComoNulos) {
		
		Array<String> keys = JsObject.keys(a);

		for (int i = 0; i < keys.lengthArray(); i++) {
			String key = keys.array(i);
			Object aa = Js.get(a, key);
			Object bb = Js.get(b, key);
			if (!deep(aa, bb, considerarStringsVaziasComoNulas, considerarArraysVaziosComoNulos)) {
				return false;
			}
		}
		
		return true;
		
	}
	
	
}