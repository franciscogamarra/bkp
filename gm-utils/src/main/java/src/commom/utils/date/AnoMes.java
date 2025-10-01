package src.commom.utils.date;

import src.commom.utils.integer.IntegerFormat;

public class AnoMes {

	private int ano;
	private int mes;

	public AnoMes(int ano, int mes) {
		this.ano = ano;
		this.mes = mes;
	}
	
	public void back() {
		mes--;
		if (mes == 0) {
			mes = 12;
			ano--;
		}
	}
	
	public void next() {
		mes++;
		if (mes == 13) {
			mes = 1;
			ano++;
		}
	}
	
	@Override
	public String toString() {
		return IntegerFormat.xx(mes) + "/" + ano;
	}
	
	public int toInt() {
		return ano * 100 + mes;
	}
	
}