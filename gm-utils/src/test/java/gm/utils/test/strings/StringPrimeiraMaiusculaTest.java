package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringPrimeiraMaiuscula;

public class StringPrimeiraMaiusculaTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("abcdefg", "Abcdefg");
		test("abcd efg", "Abcd efg");
		test("0 abcd efg", "0 abcd efg");
		test("", "");
		test(null, null);
	}

	private static void test(String s, String esperado) {
		String x = StringPrimeiraMaiuscula.exec(s);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
