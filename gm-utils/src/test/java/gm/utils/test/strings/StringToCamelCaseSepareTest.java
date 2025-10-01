package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringToCamelCaseSepare;

public class StringToCamelCaseSepareTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test(" 26239  UNIVERSIDADE FEDERAL DO PARA".toLowerCase(),"26239 Universidade Federal do Para");
		test("UmNome-","Um Nome");
		test("399 - HSBC Bank Brasil S.A. - Banco Múltiplo (HSBC)", "399 HSBC Bank Brasil Sa Banco Múltiplo HSBC");
		test("StringToCamelCaseSepareTest","String To Camel Case Separe Test");
		test("stringToCamelCaseSepareTest","String To Camel Case Separe Test");
		test("nomeNoBancoOMesmoDaClasse","Nome no Banco o Mesmo da Classe");
		test("","");
	}

	private static void test(String s, String esperado) {
		String x = StringToCamelCaseSepare.exec(s);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
