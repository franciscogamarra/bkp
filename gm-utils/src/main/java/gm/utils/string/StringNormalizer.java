package gm.utils.string;

import java.text.Normalizer;

import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;

public class StringNormalizer {

	public static String get(String s) {

		if (Null.is(s)) {
			return null;
		}
		if (StringEmpty.is(s)) {
			return s;
		}

		try {
			return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
