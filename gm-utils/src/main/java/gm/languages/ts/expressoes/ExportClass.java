package gm.languages.ts.expressoes;

import gm.languages.palavras.PalavraReservada;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExportClass extends PalavraReservada {

	private boolean isDefault = true;
	private boolean isAbstract;
	private boolean isInterface;
	private boolean isEnum;
	
	public ExportClass(boolean isAbstract) {
		super("");
		this.isAbstract = isAbstract;
	}
	
	@Override
	public String getS() {
		
		String s = "export ";
		
		if (isDefault) {
			s += "default ";
		}

		if (isAbstract) {
			s += "abstract ";
		}

		if (isInterface) {
			s += "interface";
		} else if (isEnum) {
//			s += "enum";
		} else {
			s += "class";
		}
		
		return s;
		
	}
	
}
