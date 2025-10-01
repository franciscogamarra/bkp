package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringCompare;

public class StringAfterLastTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("abgdefg", "g", "");
		test("abcdefg", "c", "defg");
		test("abcdefg", "x", null);
		test("abcdefg", "a", "bcdefg");
		test("abcaefg", "a", "efg");
		test("xxx", "x", "");
	}

	private static void test(String s, String substring, String esperado) {
		String x = StringAfterLast.get(s, substring);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
