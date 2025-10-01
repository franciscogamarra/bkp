package gm.languages.sql.expressoes.dataSet;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class DataSetReference extends Palavra {

	public DataSetReference(DataSet o) {
		super(o);
	}

}
