package gm.languages.ts.javaToTs.exemplo.services;

import gm.languages.ts.angular.core.Injectable;
import gm.languages.ts.angular.rxjs.Observable;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.exception.NaoImplementadoException;

@ImportStatic @Injectable @From("@ngx-translate/core")
public class TranslateService {

	public Observable<String> get(String s) {
		throw new NaoImplementadoException();
	}
	
}
