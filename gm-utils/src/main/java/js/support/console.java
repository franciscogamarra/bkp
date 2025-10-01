package js.support;

import gm.utils.anotacoes.Ignorar;
import gm.utils.comum.SystemPrint;

@Ignorar
public class console {

	public static boolean disableYellowBox;

	public static void log() {
		SystemPrint.ln();
	}
	
	public static void log(Object o) {
		SystemPrint.ln(o);
	}
	
	public static void log(Object a, Object b) {
		log(a);
		log(b);
	}
	public static void error(Object... os) {
		for (Object o : os) {
			SystemPrint.err(o);
		}
	}
	public static void warn(Object... os) {
		error(os);
	}
}
