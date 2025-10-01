package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* Atenção, um campo anotado como VisivelSe somente será obrigatório se estiver visivel */
@Retention(RetentionPolicy.RUNTIME)
public @interface SomenteLeituraSe {
	String value();
}
