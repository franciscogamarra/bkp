package gm.utils.date;

import java.util.Calendar;
import java.util.Date;

public class DataSemHora {

	private Data data;

	public DataSemHora() {
		set(Data.hoje());
	}
	
	public DataSemHora(int ano, int mes, int dia) {
		set(ano, mes, dia);
	}

	public DataSemHora(Calendar value) {
		set(value);
	}

	public DataSemHora(Data value) {
		set(value);
	}

	public DataSemHora(Date value) {
		set(value);
	}
	
	/* setters */
	
	private DataSemHora set(Date value) {
		return set(new Data(value));
	}

	public DataSemHora set(Calendar value) {
		data = new Data(value);
		data.zeraTime();
		return this;
	}
	
	public DataSemHora set(int ano, int mes, int dia) {
		data = new Data(ano, mes, dia);
		data.zeraTime();
		return this;
	}
	
	public DataSemHora set(Data value) {
		data = value.copy();
		data.zeraTime();
		return this;
	}

	/* getters */

	public int getAno() {
		return data.getAno();
	}

	public int getMes() {
		return data.getMes();
	}
	
	public int getDia() {
		return data.getDia();
	}
	
	/* converters */
	

	@Override
	public String toString() {
		return data.format("[yyyy]-[mm]-[dd]");
	}

	public String format(String s) {
		return data.format(s);
	}

	public boolean eq(DataSemHora o) {
		return o != null && toInt() == o.toInt();
	}
	
	public int getAnoMes() {
		return getAno() * 100 + getMes();
	}
	
	public int toInt() {
		return getAnoMes() * 100 + getDia();
	}
	
	public DataSemHora removeDias(int dias) {
		data = data.menos(dias);
		return this;
	}
	
	public DataSemHora menosDias(int dias) {
		return copy().removeDias(dias);
	}
	
	public DataSemHora copy() {
		return new DataSemHora(getAno(), getMes(), getDia());
	}

	public static void main(String[] args) {
		System.out.println( DataComHora.now().menosMinutos(10) );
	}

	public static DataSemHora hoje() {
		return new DataSemHora();
	}
	public static DataSemHora amanha() {
		return new DataSemHora(Data.amanha());
	}

	public Calendar toCalendar() {
		return data.getCalendar();
	}
	
}