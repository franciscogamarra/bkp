package gm.utils.abstrato;

public class ExtraidId {
	public static Object exec(Object o) {
		if (o == null) {
			return null;
		} else if (o instanceof ObjetoComId) {
			ObjetoComId<?> x = (ObjetoComId<?>) o;
			return x.getId();
		} else {
			return null;
		}
	}
}
