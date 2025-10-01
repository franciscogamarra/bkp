package gm.languages.ts.javaToTs.exemplo.services;

import gm.languages.ts.angular.core.Injectable;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;

@ImportStatic @Injectable @From("@sicoob/ui")
public class AlertService {
	public void open(Mensagem o) {}
}