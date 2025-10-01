package gm.languages.ts.angular.forms;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.exception.NaoImplementadoException;
import js.array.Array;

@ImportStatic
@From("@angular/forms")
public class FormControl extends AbstractControl {

	public Object value;
	public final Subscribe<Object> valueChanges = new Subscribe<Object>();

	public FormControl(Object value, Object required) {
		
		if (value instanceof Num) {
			throw new NaoImplementadoException();
		}
		
		this.value = value;
	}
	
	public FormControl() {
		
	}
	
	public FormControl(Object value) {
		this.value = value;
	}
	
	public void setValue(Object value) {
		
		if (value instanceof Num) {
			throw new NaoImplementadoException();
		}
		
		this.value = value;
		valueChanges.exec(value);
	}

	public void setValidators(Array<ValidatorFn> array) {
		
	}

	public void setErrors(Object object) {
		
	}

	public void updateValueAndValidity() {
		
	}

}
