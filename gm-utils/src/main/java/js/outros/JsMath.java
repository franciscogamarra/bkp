package js.outros;

import gm.utils.anotacoes.Ignorar;

@Ignorar
public class JsMath {

	private static java.util.Random random = new java.util.Random();
	
	public static double random() {
		
		String s = "";

		while (s.length() < 10) {
			s += random.nextInt(10);
		}
		
		return Double.parseDouble("0." + s);
	}
	
//	arredonda para baixo
	public static int floor(double value) {
		Double d = Math.floor(value);
		return d.intValue();
	}

//	arredonda para cima
	public static int ceil(double value) {
		Double d = Math.ceil(value);
		return d.intValue();
	}

//	arredonda por proximidade
	public static int round(double value) {
		Long d = Math.round(value);
		return d.intValue();
	}
	
}