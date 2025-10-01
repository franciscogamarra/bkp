package gm.utils.date;

//import gm.utils.comum.SystemPrint;
import gm.utils.comum.ULog;
import js.support.console;
import src.commom.utils.integer.IntegerParse;
//import src.commom.utils.tempo.DataHora;
public class Cronometro {

	private long time;

	public Cronometro(){
		restart();
	}

	public void restart(){
		time = System.currentTimeMillis();
		//SystemPrint.ln("start: " + DataHora.now());
	}

	public long tempo(){
		return System.currentTimeMillis() - time;
	}

	public void print(String s) {
		console.log(s + ": " + tempo());
		//SystemPrint.ln("finish: " + DataHora.now());
	}

	public void print() {
		print("mills");
	}

	public static void print(Cronometro cron, String x) {
		long tempo = cron.tempo();
		if (tempo < 10) {
			return;
		}
		String s = x + " Start: " + cron.time + " / Time: " + tempo;
		ULog.debug( s );
	}

	public int segundos(){
		return IntegerParse.toInt(tempo() / 1000);
	}

	public int minutos() {
		return IntegerParse.toInt(segundos() / 60);
	}

	public int horas() {
		return IntegerParse.toInt(minutos() / 60);
	}

	public static Cronometro start() {
		return new Cronometro();
	}
	
	@Override
	public String toString() {

		int horas = horas();
		int minutos = minutos();
		int segundos = segundos();
		long milesimos = tempo();
		
		String s = "";
		
		if (segundos > 0) {
			milesimos -= segundos * 1000;
		}
		
		if (horas > 0) {
			s += ", " + horas + " horas";
			minutos -= horas * 60;
			minutos -= horas * 60;
		}
		
		if (minutos > 0) {
			s += ", " + minutos + " minutos";
		}

		if (segundos > 0) {
			s += ", " + segundos + " segundos";
			milesimos -= segundos * 1000;
		}

		if (milesimos > 0) {
			s += ", " + milesimos + " milesimos";
		}
		
		if (s.isEmpty()) {
			return "instantaneo";
		}
		
		return s.substring(2);
	}

}
