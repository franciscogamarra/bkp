package br;

public class USystem {
	
	public static void sleepHoras(double horas) {
		USystem.sleepMinutos(horas * 60);
	}
	
	public static void sleepMinutos(double minutos) {
		USystem.sleepSegundos(minutos * 60);
	}
	
	public static void sleepSegundos(double segundos) {
		long d = (long) (segundos * 1000);
		USystem.sleepMiliSegundos(d);
	}
	
	public static void sleepMiliSegundos(long milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
}
