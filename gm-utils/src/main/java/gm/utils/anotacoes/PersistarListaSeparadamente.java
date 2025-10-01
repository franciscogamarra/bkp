package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * Anotar em campos do tipo List
 * É como o UpdateCascade
 * Caso verdadeiro a tela irá salvar cada item da lista assim que é editado
 * e só permitirá sua edição se o registro pais estiver salvo
 * Caso verdadeiro quando se persistir o registro também serão persistidas
 * as listas filhas
 * */
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistarListaSeparadamente {
	boolean value();
}
