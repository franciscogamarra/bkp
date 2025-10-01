package gm.utils.test.strings;

import org.junit.Assert;
import org.junit.Test;

import gm.utils.comum.UAssert;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringCompare;

public class StringBeforeLastTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("abcdefg", "g", "abcdef");
		test("abcdecg", "c", "abcde");
		test("abcdefg", "x", null);
		test("aaaaa", "a", "aaaa");
		UAssert.assertTrows(() -> StringBeforeLast.obrig("x", "z"), "s em branco");
		UAssert.assertTrows(() -> StringBeforeLast.obrig("x", "0x"), "s em branco");
		UAssert.assertTrows(() -> StringBeforeLast.obrig("x", "x"), "s em branco");
		StringBeforeLast.obrig("0x", "x");
	}

	private static void test(String s, String substring, String esperado) {
		String x = StringBeforeLast.get(s, substring);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
