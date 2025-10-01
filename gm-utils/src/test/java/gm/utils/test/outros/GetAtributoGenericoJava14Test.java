package gm.utils.test.outros;

import java.util.Map;

import org.junit.Test;

import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;
import js.support.console;

public class GetAtributoGenericoJava14Test<T> {

	public Map<String, Map<Integer, T>> map;
	public Class<String> s;
	public Class<Integer> i;

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		Atributo a = AtributosBuild.get(GetAtributoGenericoJava14Test.class).get(0);
		console.log(a.getDeclaracao());
	}

}
