package gm.languages.ts.angular.forms;

import java.util.HashMap;
import java.util.Map;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;

@ImportStatic
@From("@angular/forms")
public class FormGroup {

	public boolean valid;
	
	private Map<String, FormControl> controls = new HashMap<>();

	public FormGroup(Object object) {
		// TODO Auto-generated constructor stub
	}

	public void addControl(String name, FormControl control) {
		controls.put(name, control);
	}

	public FormControl get(String field) {
		return controls.get(field);
	}

}
