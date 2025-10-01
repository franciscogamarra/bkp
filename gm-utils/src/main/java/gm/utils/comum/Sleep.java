package gm.utils.comum;

public class Sleep {
	
	public static void sleepHoras(double horas) {
		USystem.sleepHoras(horas);
	}
	
	public static void sleepMinutos(double minutos) {
		USystem.sleepSegundos(minutos * 60);
	}
	
	public static void segundos(double segundos) {
		USystem.sleepSegundos(segundos);
	}
	
	public static void mills(long milisegundos) {
		USystem.sleepMiliSegundos(milisegundos);
	}
	
	
}
