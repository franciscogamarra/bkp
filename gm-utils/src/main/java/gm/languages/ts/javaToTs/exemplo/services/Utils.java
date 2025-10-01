package gm.languages.ts.javaToTs.exemplo.services;

import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.utils.exception.NaoImplementadoException;
import jakarta.validation.constraints.NotNull;
import js.Js;

@From("@app/shared/utils/utils")
public class Utils {
	
	@NotNull
	public static Mensagem getObjetoMensagemSucesso(@NotNull String msg, int duration) {
		throw new NaoImplementadoException();
	}
	
	@NotNull
	public static Mensagem getObjetoMensagemError(@NotNull String msg, int duration) {
		throw new NaoImplementadoException();
	}

	@NotNull
	public static Mensagem getSucesso(@NotNull String msg) {
		return getObjetoMensagemSucesso(msg, 10000);
	}
	
	@NotNull
	public static Mensagem getError(@NotNull String msg) {
		return getObjetoMensagemError(msg, 10000);
	}
	
	public static boolean isNull(Object o) {
		return o == null || o == Js.undefined;
	}

	public static boolean getInputInvalida(FormControl control) {
		return false;
	}
	
}
