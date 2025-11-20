package br.utils;

public class Sleep {

	public static void milesimos(int tempo) {
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {

		}
	}

	public static void segundos(int tempo) {
		milesimos(tempo * 1000);
	}

	public static void minutos(int tempo) {
		segundos(tempo * 60);
	}

}
