package src.commom.utils.ourtypes;

import gm.languages.ts.javaToTs.annotacoes.FromClass;
import js.annotations.NaoConverter;
import js.html.Element;
import src.commom.utils.comum.OurTypes;

@NaoConverter
public class HElement extends Element implements FromClass {

	@Override
	public Class<?> getFromClass() {
		return OurTypes.class;
	}

	@Override
	public void focus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollIntoView(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}


}
