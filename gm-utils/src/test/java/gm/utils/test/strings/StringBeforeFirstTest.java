package gm.utils.test.strings;

import org.junit.Test;

import gm.utils.comum.UAssert;
import junit.framework.Assert;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;

public class StringBeforeFirstTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("abcdefg", "g", "abcdef");
		test("abcdefg", "c", "ab");
		test("abcdefg", "x", null);
		test("abcdefg", "a", "");
		UAssert.assertTrows(() -> StringBeforeFirst.obrig("x", "z"), "s em branco");
	}

	private static void test(String s, String substring, String esperado) {
		String x = StringBeforeFirst.get(s, substring);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
