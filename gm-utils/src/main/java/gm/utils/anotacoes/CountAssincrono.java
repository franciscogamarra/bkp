package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CountAssincrono {
	/*nome da classe que será contada*/
	String classe();

	/*
	 * caso a tabela filha possua mais de um campo
	 * apontando para a tabela pai, será necessário
	 * informar o campo
	 * */
	String referencia() default "";

	/*
	 * caso a contagem seja condicional
	 * */
	String condicao() default "";

}
