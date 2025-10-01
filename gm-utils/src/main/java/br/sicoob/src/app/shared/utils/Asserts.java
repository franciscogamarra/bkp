package br.sicoob.src.app.shared.utils;

import gm.languages.ts.javaToTs.annotacoes.From;
import js.Error;

@From("@shared/utils/Asserts")
public class Asserts {
	public static void notNull(Object o, String msg) {
		if (Utils.isNull(o)) {
			throw new Error(msg);
		}
	}

}
