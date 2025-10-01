package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringRemoveAcentos;

public class StringRemoveAcentosTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("órgão", "orgao");
		test("", "");
		test(null, null);
	}

	private static void test(String s, String esperado) {
		String x = StringRemoveAcentos.exec(s);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
