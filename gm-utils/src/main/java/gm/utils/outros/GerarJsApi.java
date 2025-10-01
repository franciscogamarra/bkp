package gm.utils.outros;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.javaCreate.JcParametro;
import gm.utils.javaCreate.JcTipo;
import gm.utils.number.Numeric2;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.Classe;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.reflection.Parametro;
import gm.utils.reflection.classGet.ClassGet;
import gm.utils.reflection.classGet.ClassGetsLst;
import gm.utils.string.ListString;
import js.UNative;
import js.annotations.Support;
import js.array.Array;
import js.promise.Promise;
import src.commom.utils.array.Itens;
import src.commom.utils.date.BaseData;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmptyPlus;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringRight;

public abstract class GerarJsApi {
	
	private ListClass imports;
	
	protected abstract Class<? extends Annotation> getGetClass();
	protected abstract Class<? extends Annotation> getPostClass();
	
	protected abstract String getPostValue(Metodo metodo);
	protected abstract String getGetValue(Metodo metodo);
	protected abstract String getJavaOutput();

	public GerarJsApi(Class<?> service) {
		
		imports = ClassBox.get(service).getImports();
		for (Class<?> classe : imports) {
			JcTipo.addTipoConhecido(classe);
		}
		
		Lst<JcMetodo> assinaturas = getAssinaturasDeMetodos(service);
		
		JcClasse jc = JcClasse.build(getJavaOutput(), "src.notec.apis", service.getSimpleName());
		
		jc.addImport(UNative.class);
		jc.addImport(Null.class);
		jc.addImport("src.react.utils.ApiUtils");
		
		Metodos metodos = ListMetodos.get(service);
		
		metodos.removeIf(i -> !i.hasAnnotation(getGetClass()) && !i.hasAnnotation(getPostClass()));
		
		metodos.sort();
		
		for (Metodo metodo : metodos) {
			
			JcTipo tipo;
			JcTipo tipoGererics;
			
			JcMetodo mm = assinaturas.unique(i -> i.getNome().contentEquals(metodo.getName()));
			
			if (mm == null) {
				throw new RuntimeException("Nenhuma assinatura com o metodo " + metodo.getName());
			}
			
			boolean isList = UType.isList(metodo.retorno());
			
			if (isList) {
				tipoGererics = mm.getTipoRetorno().getGenerics().get(0);
				tipo = new JcTipo(Itens.class, tipoGererics);
			} else {
				if (mm.getTipoRetorno().isPrimitivo()) {
					tipo = mm.getTipoRetorno();
				} else if (mm.getTipoRetorno().eq(Data.class)) {
					tipo = new JcTipo(BaseData.class);
				} else {
					tipo = getTo(metodo.retorno());
				}
				tipoGererics = null;
			}
			
			JcMetodo m = jc.metodo(metodo.getName()).type(Promise.class, tipo).public_().static_();
			
			String path;
			
			boolean get;
			
			if (metodo.hasAnnotation(getGetClass())) {
				path = getGetValue(metodo);
				get = true;
			} else {
				path = getPostValue(metodo);
				get = false;
			}
			
			Lst<Parametro> parametros = metodo.getParametros();
			
			if (parametros.isNotEmpty() && parametros.get(0).getNome().contentEquals("arg0")) {
				throw new RuntimeException("Compilado");
			}
			
			m.addComentario("Retorno: " + tipo);
			
			parametros.removeIf(i -> i.getNome().contentEquals("inscricao"));
			
			String retorno;
			
			if (get) {
				for (Parametro parametro : parametros) {
					String nome = parametro.getNome();
					JcParametro param = new JcParametro(nome, getSafe(parametro.getType()));
					
					String var = nome;
					
					if (parametro.getType() == Data.class) {
						var = "ApiUtils.formatDate(" + var + ")";
					}
					
					path = path.replace("{"+nome+"}", "\" + "+var+" + \"");
					m.param(param);
				}
				retorno = "return ApiUtils.get(\""+path+"\").then(res -> {";
			} else {
				
				String paramName = null;
				
				for (Parametro parametro : parametros) {
					JcParametro param = new JcParametro(parametro.getNome(), getSafe(parametro.getType()));
					if (path.contains("{"+parametro.getNome()+"}")) {
						path = path.replace("{"+parametro.getNome()+"}", "\""+parametro.getNome()+"\"");
					} else if (paramName != null) {
						throw new RuntimeException("Apenas um parametro é permitido");
					} else {
						paramName = parametro.getNome();
					}
					m.param(param);
				}
				retorno = "return ApiUtils.post(\""+path+"\", "+paramName+").then(res -> {";
			}
			
			for (Parametro parametro : parametros) {

				if (UType.PRIMITIVAS_JAVA_REAL.contains(parametro.getType())) {
					String nome = parametro.getNome();
					m.if_("Null.is("+ nome + ")", "throw new Error(\"O parâmetro '"+nome+"' é obrigatório!\");");
				}
				
			}
			
			m.add(retorno);

			m.add("Object body = ApiUtils.getBody(res);");
			m.add("if (Null.is(body)) {");
			m.returnNull();
			m.add("}");
			
			if (isList) {
				jc.addImport(Array.class);
				jc.addImport(Itens.class);
				m.add("Array<" + tipoGererics + "> array = UNative.asArray(body, " + tipoGererics + ".class);");
				m.add(tipo + " o = new ArrayLst<>(array);");
			} else {
				
				if (mm.getTipoRetorno().eq(Data.class)) {
					tipo = new JcTipo(String.class);
				}
				
				m.add(tipo.getSimpleName() + " o = UNative.convert(body, " + tipo.getSimpleName() + ".class);");
			}
			
			ListString codigoTratamento = new ListString();
			if (tratarArrays(codigoTratamento, tipo, 0, jc)) {
				for (String s : codigoTratamento) {
					m.add(s);
				}
			}
			
			if (mm.getTipoRetorno().eq(Data.class)) {
				m.return_("ApiUtils.unformatDate(o)");
			} else {
				m.return_("o");
			}

			
			m.add("});");
			
		}

		jc.save();

	}
	
	private Class<?> getSafe(Class<?> type) {

		if (type == Data.class) {
			return BaseData.class;
		}

		if (type == BigDecimal.class) {
			return Double.class;
		}

		if (type == Numeric2.class) {
			return String.class;
		}

		return type;
		
	}

	private boolean tratarArrays(ListString lst, JcTipo tipo, int index, JcClasse jc) {
		
		String obj;
		
		if (index > 0) {
			obj = "[[o" + index + "]]";
		} else {
			obj = "o";
		}

		if (tipo.getSimpleName().contentEquals("ArrayLst")) {
			
			ListString lst2 = new ListString();
			tipo = tipo.getGenerics().get(0);
			
			tratarArrays(lst2, tipo, index+1, jc);
			
			if (lst2.isEmpty()) {
				return false;
			}
			
			index++;

			lst.add(obj+".forEach(o"+index+" -> {");
			lst2.replaceTexto("[[o"+index+"]]", "o"+index);
			lst.add(lst2);
			lst.add("});");
			
			return true;
			
		}
		
		Class<?> classe = tipo.getClasse();
		
		if (classe == null) {
			classe = imports.get(tipo.getSimpleName());
			if (classe == null) {
				throw new RuntimeException();
			}
		}
		
		if (UType.isPrimitiva(classe) || UType.isData(classe)) {
			return false;
		}
		
		boolean retorno = false;
		
		Lst<ClassGet> membros = ClassGetsLst.get(classe);
		
		Lst<ClassGet> lists = membros.filter(o -> o.isList());
		
		for (ClassGet g : lists) {
			
			String nome = obj+"."+g.getNome();
			lst.add("if (!Null.is("+nome+")) {");
			
			jc.addImport(Itens.class);
			
			lst.add(nome + " = new ArrayLst<>("+nome+");");
			
			JcTipo tipoGenerics = g.getTipo().getGenerics().get(0);
			
			if (!tipoGenerics.isPrimitivo()) {
				
				ListString lst2 = new ListString();
				tratarArrays(lst2, tipoGenerics, index+1, jc);
				
				if (!lst2.isEmpty()) {
					index++;
					lst.add(nome + ".forEach(o" + index + " -> {");
					lst2.replaceTexto("[[o"+index+"]]", "o"+index);
					lst.add(lst2);
					lst.add("});");
				}
				
			}
			
			lst.add("}");
			retorno = true;
		}
		
		Lst<ClassGet> objects = membros.filter(o -> o.isObject());

		for (ClassGet g : objects) {
			
			ListString lst2 = new ListString();
			tratarArrays(lst2, g.getTipo(), index+1, jc);
			
			if (!lst2.isEmpty()) {
				index++;
				String nome = obj+"."+g.getNome();
				lst.add("if (!Null.is("+nome+")) {");
				lst2.replaceTexto("[[o"+index+"]]", nome);
				lst.add(lst2);
				lst.add("}");
			}
			
		}
		
		return retorno;
		
	}

	private Lst<JcMetodo> getAssinaturasDeMetodos(Class<?> classe) {
		
		if (classe == Object.class) {
			throw new RuntimeException();
		}
		
		ListString list = new ListString().load(UClass.getJavaFileName(classe));
		list.trimPlus();
		list.removeIfStartsWith("@");
		list.removeIfStartsWith("return ");
		list.removeIfStartsWith("if ");
		list.removeIfStartsWith("for ");
		list.removeIfStartsWith("while ");
		list.removeIfStartsWith("void ");
		list.removeIfStartsWith("} ");
		list.removeIfStartsWith("private ");
		list.removeIfNotContains("(");
		list.removeIfNotContains(")");
		list.removeIfContains(" new ");
		list.removeIfContains(".");

		list = list.mapString(s -> {
			
			while (s.contains("@PathVariable")) {
				String b = StringBeforeFirst.get(s, "@PathVariable(");
				s = StringAfterFirst.get(s, "@PathVariable(");
				s = StringAfterFirst.get(s, ")");
				s = s.trim();
				s = b + s;
			}
			
			return s;
			
		});
		
		list.removeIfContains("\"");
		
		list = list.mapString(s -> {
			
			if (s.startsWith("public ") || s.startsWith("protected ")) {
				s = StringAfterFirst.get(s, " ");
			}
			
			while (!s.endsWith(")")) {
				s = StringRight.ignore1(s);
			}
			
			return s;
			
		});
		
		list.removeIfStartsWith(classe.getSimpleName()+"(");
		list.removeIfStartsWith("this(");
		list.removeIfNotContains(" ");
		
		JcClasse jc = JcClasse.build("src", classe);

		for (String s : list) {
			
			String before = StringBeforeFirst.get(s, "(");
//			String params = StringAfterFirst.get(s, "(");
			
			String nome = StringAfterLast.get(before, " ");
			String stipo = StringBeforeLast.get(before, " ");
			
			if (StringEmptyPlus.is(stipo)) {
				throw new NullPointerException(before);
			}
			
			JcTipo tipo = getTipo(stipo);
			
			jc.metodo(nome).type(tipo);
			
		}
		
		return jc.getMetodos();
		
	}
	
	private JcTipo getTipo(String s) {
		
		if (s.startsWith("Lst<")) {
			s = StringAfterFirst.get(s, "<");
			s = StringBeforeLast.get(s, ">");
			return new JcTipo(Lst.class, getTipo(s));
		}
		
		Class<?> classeImporta = imports.get(s);
		
		if (classeImporta == null) {
			return JcTipo.descobre(s);
		}
		return getTo(classeImporta);
		
	}

	private static Map<Class<?>, JcTipo> tos = new HashMap<>();

	private JcTipo getTo(Classe classe) {
		return getTo(classe.getClasse());
	}
	
	private JcTipo getTo(Class<?> classe) {
		
		if (classe == Object.class) {
			throw new RuntimeException();
		}
		
		if (UType.isList(classe)) {
			throw new RuntimeException();
		}
		
		JcTipo tipo = tos.get(classe);
		
		if (tipo != null) {
			return tipo;
		}
		
		if (classe.getSimpleName().contentEquals("AlertaTo")) {
			tipo = new JcTipo("src.react.xcomponents.AlertaTo");
			tos.put(classe, tipo);
			return tipo;
		}
		
		if (UType.isPrimitiva(classe) || UType.isData(classe)) {
			tipo = JcTipo.build(classe);
			tos.put(classe, tipo);
			return tipo;
		}
		
		if (classe.getName().startsWith("src.")) {
			tipo = JcTipo.build(classe);
			tos.put(classe, tipo);
			return tipo;
		}

		JcClasse jc = JcClasse.build(getJavaOutput(), "src.notec.apis.tos", classe.getSimpleName());
		
		jc.addAnnotation(Support.class);
		jc.setFormal(false);
		
		Atributos as = AtributosBuild.get(classe);
		
		if (as.getId() != null) {
			as.add(0, as.getId());
		}
		
		as.removeStatics();
		
		for (Atributo a : as) {
			
			if (a.getType() == Object.class) {
				throw new RuntimeException(a.toString());
			}
			
			if (a.isPrimitivo()) {
				jc.atributo(a.nome(), getSafe(a.getType())).public_();
			} else if (a.isList()) {
				
				Class<?> typeOfList = a.getTypeOfList();
				
				if (typeOfList == Object.class) {
					a.setTypeOfList(null);
					a.getTypeOfList();
					throw new RuntimeException();
				}
				
				if (UType.isList(typeOfList)) {
					a.setTypeOfList(null);
					a.getTypeOfList();
					throw new RuntimeException("Não faça um array de array: " + a);
				}
				
				jc.atributo(a.nome(), Itens.class, getTo(typeOfList)).public_();
				
			} else {
				jc.atributo(a.nome(), getTo(a.getType())).public_();
			}
			
		}
		
		Lst<JcMetodo> mts = getAssinaturasDeMetodos(classe);
		
		Metodos metodos = ListMetodos.get(classe);
		metodos.removeIf(i -> i.isStatic());
		metodos.removeIf(i -> i.returnVoid());
		metodos.removeIf(i -> !i.getName().startsWith("get"));
		metodos.removeIf(i -> as.contains(i.getName().substring(3)));
		
		for (Metodo metodo : metodos) {
			
			String nome = StringPrimeiraMinuscula.exec(metodo.getName().substring(3));
			
			if (as.contains(nome)) {
				continue;
			}
			
			Classe retorno = metodo.retorno();
			
			if (UType.isPrimitiva(retorno)) {
				jc.atributo(nome, retorno).public_();
			} else if (UType.isList(retorno)) {
				JcMetodo mm = mts.uniqueObrig(i -> i.getNome().contentEquals(nome));
				JcTipo tipoGererics = mm.getTipoRetorno().getGenerics().get(0);
				jc.atributo(nome, new JcTipo(Itens.class, tipoGererics)).public_();
			} else {
				throw new NaoImplementadoException();
			}
			
		}
		
		jc.save();
		tipo = jc.getTipo();
		tos.put(classe, tipo);
		return tipo;
	}
	
}