package gm.utils.test.data;

import js.outros.Date;
import js.support.console;
import src.commom.utils.date.BaseData;

public class BaseDataTest {

//	@Test
	public void exec() {
		Date date = new Date(2020, 1, 1, 0, 0, 0, 0);
		console.log(date.getFullYear());
		console.log(date.getMonth());
		console.log(date.getDate());
		BaseData data = BaseData.toData(date);
		console.log(data);
		console.log(data.toDate());
	}

//	private void all(Data date, int ano, int mes, int dia, int hora, int minuto, int segundo) {
//		date(date, ano, mes, dia);
//		time(date, hora, minuto, segundo);
//	}

//	private void date(Data date, int ano, int mes, int dia) {
//		UAssert.eq(date.getAno(), ano, "ano");
//		UAssert.eq(date.getMes(), mes, "mes");
//		UAssert.eq(date.getDia(), dia, "dia");
//	}

//	private void time(Data date, int hora, int minuto, int segundo) {
//		UAssert.eq(date.getHora(), hora, "hora");
//		UAssert.eq(date.getMinuto(), minuto, "minuto");
//		UAssert.eq(date.getSegundo(), segundo, "segundo");
//	}

	public static void main(String[] args) {
		new BaseDataTest().exec();
	}

}
