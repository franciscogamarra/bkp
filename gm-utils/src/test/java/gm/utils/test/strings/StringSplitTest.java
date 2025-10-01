package gm.utils.test.strings;

import org.junit.Test;

import gm.utils.comum.UAssert;
import junit.framework.Assert;
import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringSplit;

public class StringSplitTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {

		test("ab","b" , "a","");
		test("ab","a" , "","b");

		test("00 francisco de a gamarra", " f", Itens.build("00","rancisco de a gamarra"));

		Itens<String> list = StringSplit.exec("a,b,c",",");
		Assert.assertTrue(IntegerCompare.eq(list.size(), 3));
		Assert.assertTrue(StringCompare.eq(list.get(0), "a"));
		Assert.assertTrue(StringCompare.eq(list.get(1), "b"));
		Assert.assertTrue(StringCompare.eq(list.get(2), "c"));

		list = StringSplit.exec(null,",");
		Assert.assertTrue(list.isEmpty());

		list = StringSplit.exec("1",",");
		Assert.assertTrue(list.size() == 1);

	}

	private static void test(String s, String sub, String... esperado) {
		test(s, sub, Itens.build(esperado));
	}

	private static void test(String s, String sub, Itens<String> esperado) {
		Itens<String> list = StringSplit.exec(s, sub);
		UAssert.eq(list.toString(), esperado.toString(), "!=");
	}

}
