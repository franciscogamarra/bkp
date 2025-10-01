package src.commom.utils.comum;

import js.outros.JsMath;
import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringConstants;

public class Randomico {
	
	public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet. Aut quae repellendus sit iusto aliquam et voluptas quia. Est magni quae ut numquam galisum et eaque fugiat et dolore nesciunt. Qui voluptatem ratione At eligendi laborum eos fuga ipsum et dolorem minus. Et laboriosam quisquam 33 vitae itaque in omnis aliquid qui eveniet rerum quo quis consectetur 33 corporis fuga eos asperiores commodi.";
	
	public static int getInt(int a, int b) {
		
		if (a == b) {
			return a;
		}
		
		int maior = a > b ? a : b;
		int menor = a < b ? a : b;
		a = maior - menor + 1;
		a = JsMath.floor(JsMath.random() * a);
		a += menor;
		return a;
		
	}
	
	public static boolean getBoolean() {
		return getInt(0, 1) == 1;
	}
	
	public static <T> T getItem(Itens<T> itens) {
		
		if (Null.is(itens) || itens.isEmpty()) {
			return null;
		}
		
		int i = getInt(0, itens.size()-1);
		
		return itens.get(i);
		
	}
	
	public static <T> Itens<T> getEmbaralhado(Itens<T> itens) {
		
		itens = itens.copy();
		Itens<T> list = new Itens<>();
		
		while (!itens.isEmpty()) {
			T o = getItem(itens);
			list.add(o);
			itens.removeObj(o);
		}
		
		return list;
		
	}
	
	public static <T> Itens<T> getItens(Itens<T> itens, int size) {
		
		itens = itens.copy();
		
		while (itens.size() > size) {
			int i = getInt(0, itens.size()-1);
			itens.remove(i);
		}
		
		return itens;
		
	}
	
	public static <T> Itens<T> getItenss(Itens<T> itens, int sizeInicio, int sizeFim) {
		int size = getInt(sizeInicio, sizeFim);
		return getItens(itens, size);
	}
	
	public static String getString(int casas) {
		return getItens(StringConstants.LETRAS_E_NUMEROS, casas).joinString("");
	}

	public static String getStringg(int min, int max) {
		return getString(min) + getString(max-min);
	}

	public static String getIntString(int casas) {
		return getItens(StringConstants.NUMEROS, casas).joinString("");
	}

	
	public static String removeItem(Itens<String> itens) {
		String item = getItem(itens);
		itens.removeObj(item);
		return item;
	}

}