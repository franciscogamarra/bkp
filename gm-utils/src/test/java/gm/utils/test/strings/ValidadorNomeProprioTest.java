package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.ValidadorNomeProprio;

public class ValidadorNomeProprioTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("BEBIDA MISTA ADOCADA  ( ACAI E GUARANA )", "Bebida Mista Adocada (Acai e Guarana)");
		test("00    FRANCISCO DE A GAMARRA", "00 Francisco de A Gamarra");
		test("26239 UNIVERSIDADE FEDERAL DO PARA", "26239 Universidade Federal do Para");
		test("AÇAÍ MÉDIO OU REGULAR (ACIDIFICADO PASTEURIZADO)", "Açaí Médio ou Regular (Acidificado Pasteurizado)");
	}

	private static void test(String s, String esperado) {
		String x = ValidadorNomeProprio.instance.formatParcial(s);
		System.out.println(x);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
