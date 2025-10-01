package br.sicoob.src.app.shared.utils;

import gm.languages.ts.angular.core.Injectable;
import gm.languages.ts.angular.rxjs.take;
import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.languages.ts.javaToTs.exemplo.services.AlertService;
import gm.languages.ts.javaToTs.exemplo.services.TranslateService;
import gm.languages.ts.javaToTs.exemplo.services.Utils;
import gm.languages.ts.javaToTs.exemplo.xx.Any;
import gm.utils.anotacoes.Ignorar;
import js.Js;
import js.array.Array;

@Injectable
@ImportStatic
public class SicoobAlertas extends JS {

	private AlertService alertService;
	private TranslateService translateService;

	@Ignorar
	public void sucesso(String o) {
		sucesso(new Any(o));
	}

	public void sucesso(Any o) {
		alertService.open(Utils.getSucesso(getMessage(o)));
	}

	public void erro(Any o) {
		alertService.open(Utils.getError(getMessage(o)));
	}

	private String getMessage(Any o) {

		String s = getMsg(o);

		if (isNull_(s)) {
			s = "Ocorreu um erro inesperado";
		}

		Str msg = new Str();
		translateService.get(s).pipe(take.call(1)).subscribe(res -> msg.set(res));
		return msg.get();

	}

	private String getMsg(Any o) {

		if (isNull_(o)) {
			return "";
		}
		
		if (Js.typeof(o) == "string") {
			String s = o.as();
			if (s.length() == 0) {
				return "";
			}
			return s;
		}
		
		String s = getMsg(o.error);
		if (isNotNull_(s)) {
			return s;
		}

		s = getMsg(o.erro);
		if (isNotNull_(s)) {
			return s;
		}

		s = getMsg(o.mensagem);
		if (isNotNull_(s)) {
			return s;
		}

		if (Array.isArray(o.mensagens)) {
			Array<Any> array = o.mensagens.as();
			while (array.length) {
				s = getMsg(array.shift());
				if (isNotNull_(s)) {
					return s;
				}
			}
		}
		
		s = getMsg(o.message);
		if (isNotNull_(s)) {
			return s;
		}
		
		return "";

	}

}
