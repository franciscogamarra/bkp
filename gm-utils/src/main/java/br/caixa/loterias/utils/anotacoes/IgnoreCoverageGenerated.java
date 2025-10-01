package br.caixa.loterias.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*

De acordo com este artigo: https://www.baeldung.com/jacoco-report-exclude

Para excluir um trecho de código do jacoco, basta que:

The name of the annotation should include Generated.
The retention policy of annotation should be runtime or class.

*/
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreCoverageGenerated {
	
}
