package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringCompare;

public class StringAfterFirstTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("abcdefg", "g", "");
		test("abcdefg", "c", "defg");
		test("abcdefg", "x", null);
		test("abcdefg", "a", "bcdefg");
		test("xxx", "x", "xx");
	}

	private static void test(String s, String substring, String esperado) {
		String x = StringAfterFirst.get(s, substring);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
