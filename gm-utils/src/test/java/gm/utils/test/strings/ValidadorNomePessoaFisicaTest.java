package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.ValidadorNomePessoaFisica;

public class ValidadorNomePessoaFisicaTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("francisco de azevedo gamarra", "Francisco de Azevedo Gamarra");
		test("francisco de azevedo gamarra  00", "Francisco de Azevedo Gamarra");
		test("FRANCISCO DE AZEVEDO GAMARRA  00", "Francisco de Azevedo Gamarra");
		test("00 FRANCISCO DE AZEVEDO GAMARRA", "Francisco de Azevedo Gamarra");
		test("00 FRANCISCO DE A GAMARRA", "Francisco de A Gamarra");
	}

	private static void test(String s, String esperado) {
		String x = ValidadorNomePessoaFisica.instance.formatParcial(s);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
