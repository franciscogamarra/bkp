package js.outros;

import gm.utils.anotacoes.Ignorar;
import gm.utils.date.Data;

@Ignorar
public class Date {

	private Data o;
	private int milissegundo;

	public Date() {
		o = new Data();
	}
//	new Date(ano, mês, dia, hora, minuto, segundo, milissegundo);
	public Date(int ano, int mes, int dia, int hora, int minuto, int segundo, int milissegundo) {
		this.milissegundo = milissegundo;
		o = new Data(ano, mes+1, dia, hora, minuto, segundo);
	}

	public Date(long time) {
		o = new Data(time);
	}

	public int getHours() {
		return o.getHora();
	}

	@Deprecated // utilize getFullYear
	public int getYear() {
		return o.getAno()-1900;
	}

	public int getFullYear() {
		return o.getAno();
	}
	
	public int getMinutes() {
		return o.getMinuto();
	}
	
	public int getSeconds() {
		return o.getSegundo();
	}
	
	public int getMilliseconds() {
		return milissegundo;
	}

	public String toDateString() {
		return o.format("[dd]/[mm]/[yyyy]");
	}

	public long getTime() {
		return o.getDate().getTime();
	}

	public void setTime(long time) {
		o = new Data(time);
	}

	public int getDate() {
		return o.getDia();
	}

	public void setDate(int i) {
		o.setDia(i);
	}

	public int getDay() {
		return o.getDiaSemana()-1;
	}

	public int getMonth() {
		return o.getMes()-1;
	}
	public static long now() {
		return System.currentTimeMillis();
	}
	public Data get() {
		return o;
	}
	public String toISOString() {
		return o.format("[yyyy]-[mm]-[dd]T[hh]:[nn]:[ss].000Z");
	}

	@Override
	public String toString() {
		return o.toString();
	}

}
