package gm.utils.test;

import org.junit.Test;

import gm.utils.comum.UAssert;
import gm.utils.date.Data;
import src.commom.utils.casewhen.Case;

public class CaseWhenTest {

	@Test
	public void exec() {

		Data data = new Data(2020,1,1);
		Data data2 = new Data(2020,1,2);
		Data data3 = new Data(2020,1,2);

		Data value =
			new Case<Data>()
				.when(() -> data == null, () -> new Data())
				.when(() -> data.isHoje(), () -> new Data().add(5))//melhor esta
				.when0(() -> data.isHoje(), data2)//do que esta, pois o obj soh serah criado se necessario
				.when0(() -> data.isUltimoDiaDoMes(),
					new Case<Data>()
						.when1(data.isHoje(), data)
					.end()
				)
				.other0(data3)
			.end();

		UAssert.eq(value, data2, "?");

	}

	public static void main(String[] args) {
		new CaseWhenTest().exec();

		/*
		 * (*1) -> se o result nao fosse primitivo e nao ti
		 * */

	}

}
