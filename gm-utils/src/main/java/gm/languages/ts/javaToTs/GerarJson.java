package gm.languages.ts.javaToTs;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.GetMainClass;
import gm.utils.comum.JSon;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import js.annotations.NaoConverter;
import js.annotations.TypeJs;

@NaoConverter
public class GerarJson {

	public static void exec() {
		exec(GetMainClass.get());
	}
	
	public static void exec(Class<?> classe) {
		
		JcClasse jc = JcClasse.build(classe);
		
		jc.construtor().addAnotacao(IgnorarDaquiPraBaixo.class);
		jc.addAnnotation(TypeJs.class);

		Atributos as = Atributos.get(classe, true);
		as.removeStatics();
		as.remove("as");
		as.remove("search");
		as.remove("search");
		as.addId();
		
		SortAs.exec(as, jc);
		
		String sn = classe.getSimpleName();
		
		jc.metodo("json").type(classe).public_().static_().return_("new " + sn + "();");

		for (Atributo a : as) {
			String n = a.nome();
			jc.atributo(a).public_();
			jc.metodo(n).public_().param(a).add("this."+n+" = " + n + ";").returnThis();
		}
		
		JcMetodo buildExemplo = jc.metodo("buildExemplo");
		buildExemplo.addAnotacao(Ignorar.class);
		
		buildExemplo.add(sn + ".json()");
		
		for (Atributo a : as) {

			String value;
			
			if (a.isString()) {
				value = "\"\"";
			} else if (a.isBoolean()) {
				value = "true";
			} else if (a.is(Integer.class, int.class)) {
				value = "0";
			} else {
				value = "null";
			}
			
			buildExemplo.add("." + a.nome() + "("+value+")");
			
		}
		
		buildExemplo.add(";");
		
		JcMetodo main = jc.main();
		
//		jc.addImport(Transpilar.class);
		jc.addImport(GerarJson.class);
		
		main.add("br.Transpilar.exec();");
		
		if (classe.isAnnotationPresent(ImportStatic.class)) {
			jc.newAnnotation(ImportStatic.class);
		}

		if (classe.isAnnotationPresent(NaoConverter.class)) {
			jc.newAnnotation(NaoConverter.class);
		}

		if (classe.isAnnotationPresent(From.class)) {
			String from = classe.getAnnotation(From.class).value();
			jc.newAnnotation(From.class).setValue("\""+from+"\"");
		}

		main.add("GerarJson.exec();");
		
		jc.addImport(JSon.class);
		jc.metodo("toString").public_().override().type(String.class).return_("JSon.toJson(this)");

		try {
			jc.save();
		} catch (Exception e) {
			jc.print();
		}
		
	}
	
}
