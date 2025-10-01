package gm.languages.sql.expressoes.dataSet;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.NaoClassificada;
import lombok.Setter;

@Setter
public class Coluna extends Palavra {

	private final DataSet dataSet;
	private boolean comAliasDoDataSet;

	public Coluna(DataSet dataSet, NaoClassificada nome) {
		super(nome.getS());
		this.dataSet = dataSet;
	}
	
	@Override
	public String toString() {
		
		String s = getS();
		
		if (comAliasDoDataSet) {
			s = dataSet.getAliasOuNome() + "." + s;
		}
		
		return toStringFinal(s);
		
	}
	
}