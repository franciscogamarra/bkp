package src.commom.utils.object;

import js.outros.Date;

public class Tempo {
	public static void exec(long start) {
		long end = Date.now();
		long dif = end - start;
		if (dif > 1000) {
			throw new RuntimeException("dif > 1000 : " + dif);
		}
	}
}