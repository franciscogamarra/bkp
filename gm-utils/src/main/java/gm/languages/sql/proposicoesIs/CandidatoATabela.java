package gm.languages.sql.proposicoesIs;

import gm.languages.palavras.Is0;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.sql.expressoes.cte.CteReference;
import gm.languages.sql.expressoes.variaveis.VarReference;

public class CandidatoATabela extends Is0 {

	@Override
	public boolean exec(Palavra o) {
		return o.is(NaoClassificada.class, VarReference.class, CteReference.class);
	}

}
