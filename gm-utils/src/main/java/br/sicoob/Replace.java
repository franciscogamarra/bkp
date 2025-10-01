package br.sicoob;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringTrim;

public class Replace {

	public static void main(String[] args) {
//		GFile file = GFile.get("C:\\dev\\projects\\cre-concessao-bndes-web\\src\\app\\funcionalidades\\proposta-credito-bndes\\components\\aba-operacao\\aba-operacao.component.ts");
		GFile file = GFile.get("C:\\dev\\projects\\cre-concessao-bndes-web\\src\\app\\funcionalidades\\proposta-credito-bndes\\components\\aba-itens-investimento\\aba-itens-investimento.component.ts");
		
		ListString list = file.load();
		list.removeDoubleWhites();
		
		list.replaceEach(s -> {
			if (s.trim().isEmpty()) {
				return s;
			} else {
				return StringTrim.right(s);
			}
		});
		
		ListString keys = ListString.array("valorTotalInvestimento","valorRecursosProprios","percFinanciada","percRecursoProprio","percTotalInvestimento");
		
		int index = 38;
		
		for (String key : keys) {
			
			list.replaceTexto("this.formParent.get('"+key+"').setValue(", "this." + key + ".set(");
			list.replaceTexto("this.formParent.get(\""+key+"\").setValue(", "this." + key + ".set(");
			
			list.replaceTexto("this.formParent.get('"+key+"').valueChanges.subscribe(", "this." + key + ".subscribe(");
			list.replaceTexto("this.formParent.get(\""+key+"\").valueChanges.subscribe(", "this." + key + ".subscribe(");
			
			list.replaceTexto("this.formParent.get('"+key+"').value", "this." + key + ".get()");
			list.replaceTexto("this.formParent.get(\""+key+"\").value", "this." + key + ".get()");

			list.replaceTexto("this.formParent.get('"+key+"').setValidators(", "this." + key + ".setValidators(");
			list.replaceTexto("this.formParent.get(\""+key+"\").setValidators(", "this." + key + ".setValidators(");

			list.replaceTexto("this.formParent.get('"+key+"').setErrors(", "this." + key + ".setErrors(");
			list.replaceTexto("this.formParent.get(\""+key+"\").setErrors(", "this." + key + ".setErrors(");

			String s = "  private "+key+" : any = this.state.build(\""+key+"\");";
			
			if (!list.contains(s)) {
				list.add(index++, s);
			}			
			
		}
		
		list.save();
	}
	
}
