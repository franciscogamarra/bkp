package gm.utils.javaCreate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.exception.UException;
import gm.utils.lambda.F1;
import gm.utils.lambda.P2;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodos;
import gm.utils.reflection.Parametro;
import gm.utils.string.CorretorOrtografico;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringEmptyPlus;
import src.commom.utils.string.StringPrimeiraMinuscula;

@Getter @Setter
public class JcMetodo {

	private JcClasse jc;
	private JcAnotacoes anotacoes = new JcAnotacoes();
	private String afterComment;
	private String nome;
	private JcTipo genericsReverso;
	private JcTipo tipoRetorno;
	private String acesso = "private";
	private final ListString comentarios = new ListString();

	private boolean hasThrow;
	private boolean override;
	private boolean abstrato;
	private boolean estatico;
	private boolean quebrarNosParametros;

	private boolean synchronized_;
	private boolean final_;
	private boolean construtor;
	private boolean pularLinhaAntes = true;
	private boolean naoQuebrarLinhas;
	private int ordem = 0;
	private F1<String,String> tratarToString;

	private JcTipos tipos = new JcTipos();

	private Lst<JcParametro> parametros = new Lst<>();
	private Lst<JcTipo> throwsList = new Lst<>();
	private ListString body = new ListString();
	private ListString bodyEnd = new ListString();

//	30032764 skynet vitoria
//	ligacao fixo ilimitada
//	72316011

	public JcMetodo(String nome) {
		this.nome = nome;
		body.setAutoIdentacao(true);
	}
	public JcMetodo add() {
		if (abstrato) {
			throw UException.runtime("Método abstrato");
		}
		body.add();
		return this;
	}
	public JcMetodo returnNull() {
		return this.return_("null");
	}
	public JcMetodo returnThis() {
		if (tipoRetorno == null) {
			type(jc);
		} else if (tipoRetorno != jc.getTipo()) {
			throw new RuntimeException();
		}
		return this.return_("this");
	}
	public JcMetodo returnSuper() {
		return this.return_("super."+nome+"()");
	}
	public JcMetodo while_(String s) {
		return this.add("while ("+s+") {");
	}
	public JcMetodo if_(String s) {
		return this.add("if ("+s+") {");
	}
	public JcMetodo ifIsNull(String s) {
		tipos.add(Null.class);
		return this.add("if (Null.is("+s+")) {");
	}
	public JcMetodo ifIsNotNull(String s) {
		tipos.add(Null.class);
		return this.add("if (!Null.is("+s+")) {");
	}
	public JcMetodo ifIsNotStringEmpty(String s) {
		tipos.add(StringEmptyPlus.class);
		return this.add("if (!StringEmptyPlus.is("+s+")) {");
	}
	public JcMetodo throwBusiness(String message) {
		return throw_(message, true);
	}
	public JcMetodo throw_(String s, boolean colocarAspas) {
		if (colocarAspas) {
			s = "\"" + CorretorOrtografico.exec(s) + "\"";
		}
		hasThrow = true;
		return this.add("throw new [exceptionClass]("+s+");");
	}
	public JcMetodo ifReturn(String condicao, String resultado) {
		return this.if_(condicao, "return " + resultado + ";");
	}
	public JcMetodo ifReturn(String condicao, String resultado, String elseReturn) {
		return if_(condicao).return_(resultado).else_().return_(elseReturn).close();
	}
	public JcMetodo ifReturnVoid(String condicao) {
		return this.if_(condicao, "return;");
	}
	public JcMetodo if_(String condicao, String... add) {
		this.if_(condicao);
		for (String string : add) {
			this.add(string);
		}
		return close();
	}
	public JcMetodo ifElse(String s, String ifTrue, String ifFalse) {
		return this.if_(s).add(ifTrue + ";").else_().add(ifFalse + ";").close();
	}
	public JcMetodo else_() {
		return this.add("} else {");
	}
	public JcMetodo elseif_(String s) {
		return this.add("} else if ("+s+") {");
	}
	public JcMetodo close() {
		return this.add("}");
	}
	public JcMetodo add(String s) {
		if (abstrato) {
			throw UException.runtime("Método abstrato");
		}
		body.add(s);
		return this;
	}
	public JcMetodo add(Object... os) {

		StringBuilder s = new StringBuilder();

		for (Object o : os) {

			if (o == null) {
				s.append("null");
			} else if (o instanceof JcTipo) {
				tipos.add((JcTipo) o);
				s.append(o);
			} else if (o.getClass().getSimpleName().contentEquals("Class")) {
				Class<?> classe = (Class<?>) o;
				tipos.add(classe);
				s.append(classe.getSimpleName());
			} else {
				s.append(o);
			}
		}

		return add(s.toString());
	}
	public JcMetodo addEnd(String s) {
		if (abstrato) {
			throw UException.runtime("Método abstrato");
		}
		bodyEnd.add(s);
		return this;
	}

	public String getAssinatura() {

		String s;

		if (jc.isInterface()) {

			if (isEmpty()) {
				s = "";
			} else {
				s = "default ";
			}

			if (abstrato || construtor || estatico || synchronized_) {
				throw new RuntimeException();
			}

			if (final_) {
				throw new RuntimeException();
			}

		} else {
			s = acesso + " ";
		}

		if (abstrato) {
			s += "abstract ";
		}

		if (!construtor) {
			if (estatico) {
				s += "static ";
			}
			if (synchronized_) {
				s += "synchronized ";
			}
			if (final_) {
				s += "final ";
			}

			if (genericsReverso != null) {
				s += "<"+genericsReverso+"> ";
			}

			if (tipoRetorno == null) {
				s += "void";
			} else {
				tipoRetorno.setExtendsLigado(false);
				s += tipoRetorno;
				tipoRetorno.setExtendsLigado(true);
				if (estatico && tipoRetorno.hasGenerics() && tipoRetorno.getGenerics().anyMatch(i -> i.isGenerics())) {
					s = s.replace(" static ", " static <"+tipoRetorno.getGenerics().joinString(",")+"> ");
				}
				
			}
			s += " ";
		}

		s += nome + "(";
		if (!parametros.isEmpty()) {
			if (quebrarNosParametros) {
				s += "\n\t" + parametros.toString(o -> o.toString(), ",\n\t") + "\n";
			} else {
				s += parametros.toString(o -> o.toString(), ", ");
			}
		}
		
		s += ")";
		
		if (!throwsList.isEmpty()) {
			s += " throws " + throwsList.joinString(", ");
		}

		if (abstrato) {
			s += ";";
		} else if (isEmpty()) {
			if (jc.isInterface()) {
				s += ";";
			} else {
				s += " {}";
			}
		} else {
			s += " {";
		}

		return s;

	}

	public ListString get() {

		ListString list = new ListString();

		if (pularLinhaAntes) {
			list.add();
		}

		if (!comentarios.isEmpty()) {

			if (comentarios.size() == 1) {
				list.add("/* "+comentarios.get(0)+" */");
			} else {
				list.add("/*");
				for(String s : comentarios) {
					list.add("* " + s);
				}
				list.add(" */");
			}

		}

		if (override) {
			anotacoes.add(Override.class);
		}

		if (anotacoes.has()) {
			list.add(anotacoes.toString());
		}

		list.add(getAssinatura());

		if (abstrato || isEmpty()) {
			return list;
		}

		list.margemInc();

		if (bodyEnd.isEmpty()) {
			list.add(body);
		} else {
			list.add(body.copy().add(bodyEnd));
		}

		list.margemDec();
		list.add("}");

		if (afterComment != null) {
			list.add("\\" + afterComment);
		}

		if (naoQuebrarLinhas) {
			list.trim();
			String s = list.toString(" ");
			s = s.replace("( ","(");
			s = s.replace(" )",")");
			s = s.replace("{ ","{");
			s = s.replace(" }","}");
			list.clear();
			list.add(s.trim());
		}
		
		if (parametros.isNotEmpty()) {
			String s = parametros.joinString(i -> i.getNome(), ", ");
			list.replaceTexto("super."+nome+"()", "super."+nome+"("+s+")");
		}

		return list;

	}

	private boolean isEmpty() {
		return body.isEmpty() && bodyEnd.isEmpty();
	}

	@Override
	public String toString() {
		String s = get().toString("\n");
		if (tratarToString != null) {
			s = tratarToString.call(s);
		}
		return s;
	}

	public JcMetodo param(Parametro param) {
		newParam(param.getNome(), param.getParameter());
		return this;
	}
	public JcMetodo param(String nome, Parameter parameter) {
		newParam(nome, parameter.getParameterizedType());
		return this;
	}
	public JcParametro newParam(Parametro param) {
		return newParam(param.getNome(), param.getParameter());
	}
	public JcParametro newParam(String nome, Parameter parameter) {
		return newParam(nome, parameter.getParameterizedType());
	}
	public JcParametro newParam(String nome, Type type) {
		JcParametro param = new JcParametro(nome, type);
		paramFim(param);
		return param;
	}
	public JcParametro newParam(String nome, JcTipo tipo) {
		JcParametro param = new JcParametro(nome, tipo);
		paramFim(param);
		return param;
	}

	public JcParametro newParam(String nome, JcClasse tipo) {
		return newParam(nome, tipo.getTipo());
	}
	
	public JcMetodo param(JcParametro param) {
		return paramFim(param);
	}
	public JcMetodo param(String nome, Type tipo, ToJcTipo generics) {
		return paramFim(nome, new JcTipo(tipo), generics.toJcTipo());
	}
	public JcMetodo param(String nome, Type tipo, JcClasse generics) {
		return paramFim(nome, new JcTipo(tipo), generics.getTipo());
	}
	public JcMetodo param(String nome, Type tipo, Type generics) {
		return paramFim(nome, new JcTipo(tipo), new JcTipo(generics));
	}
	public JcMetodo param(String nome, String tipo, String generics) {
		return paramFim(nome, new JcTipo(tipo), new JcTipo(generics));
	}
	public JcMetodo param(String nome, Type tipo, String generics) {
		return paramFim(nome, new JcTipo(tipo), new JcTipo(generics));
	}
	public JcMetodo param(String nome, Type tipo, JcTipo generics) {
		return paramFim(nome, new JcTipo(tipo), generics);
	}
	public JcMetodo param(String nome, JcTipo tipo) {
		return paramFim(nome, tipo);
	}
	public JcMetodo param(String nome, ToJcTipo tipo) {
		return param(nome, tipo.toJcTipo());
	}
	public JcMetodo paramAndSet(String nome, JcTipo tipo) {
		return param(nome, tipo).add("this."+nome+" = "+nome+";");
	}
	public JcMetodo param(Atributo a) {
		return param(a.nome(), a.getTypeJc());
	}
	public JcMetodo paramId() {
		return paramFim("id", new JcTipo(int.class));
	}
	public JcMetodo param(String nome, JcClasse tipo) {
		return paramFim(nome, tipo.getTipo());
	}
	public JcMetodo sparams(String... nomes) {
		for (String nome : nomes) {
			paramFim(nome, new JcTipo(String.class));
		}
		return this;
	}
	public JcMetodo param(String nome, Type tipo) {
		return paramFim(nome, new JcTipo(tipo));
	}
	public JcMetodo param(String nome, String tipo) {
		return paramFim(nome, new JcTipo(tipo));
	}
	public JcMetodo param(Type classe) {
		JcTipo tipo = new JcTipo(classe);
		return paramFim(StringPrimeiraMinuscula.exec(tipo.getSimpleName()), tipo);
	}

	public JcMetodo paramArray(String nome, String tipo) {
		return paramArrayFim(nome, new JcTipo(tipo));
	}
	public JcMetodo paramArray(String nome, Type tipo) {
		return paramArrayFim(nome, new JcTipo(tipo));
	}
	public JcMetodo paramArray(String nome, JcTipo tipo) {
		return paramArrayFim(nome, tipo);
	}

	/*finais*/

	private JcMetodo paramFim(String nome, JcTipo tipo, JcTipo... generics) {
		return paramFim(new JcParametro(nome, tipo, generics));
	}

	private JcMetodo paramArrayFim(String nome, JcTipo tipo) {
		return paramFim(new JcParametro(nome, tipo).array());
	}

	private JcMetodo paramFim(JcParametro param) {
		parametros.add(param);
		return this;
	}

	/*finais*/

	public JcMetodo setTipoRetorno(String tipo) {
		return this.setTipoRetorno(new JcTipo(tipo));
	}
	public JcMetodo setTipoRetorno(JcTipo tipo) {
		tipoRetorno = tipo;
		return this;
	}
	public JcMetodo setTipoRetorno(Type tipo) {
		this.setTipoRetorno(new JcTipo(tipo));
		return this;
	}
	public JcMetodo clearGenericsRetorno() {
		tipoRetorno.clearGenerics();
		return this;
	}
	public JcMetodo addGenericsRetorno(JcClasse tipo) {
		tipoRetorno.addGenerics(tipo.getName());
		return this;
	}
	public JcMetodo addGenericsRetorno(Class<?> tipo) {
		tipoRetorno.addGenerics(tipo);
		return this;
	}
	public JcMetodo addGenericsRetorno(JcTipo tipo) {
		tipoRetorno.addGenerics(tipo);
		return this;
	}
	public JcMetodo addGenericsRetorno(ToJcTipo tipo) {
		tipoRetorno.addGenerics(tipo);
		return this;
	}
	public JcMetodo addGenericsRetorno(String tipo) {
		if (tipo == null || tipo.contentEquals(".null")) {
			return this;
		}
		this.addGenericsRetorno(new JcTipo(tipo));
		return this;
	}
	public JcMetodo setGenericsRetornoNivel2(String tipo) {
		if (tipo == null || tipo.contentEquals(".null")) {
			return this;
		}
		return setGenericsRetornoNivel2(tipo);
	}
	public JcMetodo setGenericsRetornoNivel2(JcTipo tipo) {
		tipoRetorno.getGenerics().get(0).addGenerics(tipo);
		return this;
	}
	public JcMetodo setGenericsRetorno(JcTipo tipo) {
		return addGenericsRetorno(tipo);
	}
	public JcMetodo setGenericsRetorno(Type type) {
		addGenericsRetorno(new JcTipo(type));
		return this;
	}
	public JcMetodo setGenericsRetornoNivel2(Type type) {
		if (type != null) {
			this.setGenericsRetornoNivel2(new JcTipo(type));
		}
		return this;
	}
	public JcMetodo margemInc() {
		body.margemInc();
		return this;
	}
	public JcMetodo margemDec() {
		body.margemDec();
		return this;
	}
	public String removeLast() {
		return body.removeLast();
	}
	public JcMetodo final_() {
		setFinal_(true);
		return this;
	}
	public JcMetodo public_() {
		setAcesso("public");
		return this;
	}
	public JcMetodo protected_() {
		setAcesso("protected");
		return this;
	}
	public JcMetodo synchronized_() {
		setSynchronized_(true);
		return this;
	}
	public JcMetodo abstract_() {
		if (!body.isEmpty()) {
			throw UException.runtime("Método contém body");
		}
		if (!"public".equals(acesso)) {
			protected_();
		}
		setAbstrato(true);
		return this;
	}
	public JcMetodo override() {
		if (!"public".equals(acesso)) {
			protected_();
		}
		setOverride(true);

		if (jc != null && jc.getExtends_() != null && tipoRetorno == null) {
			JcTipo extends_ = jc.getExtends_();
			Class<?> classe = UClass.getClass(extends_.getName());
			if (classe != null) {
				Metodos metodos = ListMetodos.get(classe);
				metodos = metodos.filter(o -> !o.returnVoid());
				metodos = metodos.find(getNome());
				if (metodos.size() == 1) {
					Class<?> retorno = metodos.get(0).retorno().getClasse();
					if (retorno != null) {
						setTipoRetorno(retorno);
					}
				}
			}
		}

		return this;
	}
	public JcMetodo static_() {
		setEstatico(true);
		return this;
	}
	public JcMetodo typeSemValidacao(String tipo) {
		tipoRetorno = new JcTipo(tipo);
		return this;
	}
	public JcMetodo type(JcClasse tipo) {
		return this.type(tipo.getTipo());
	}
	public JcMetodo type(JcTipo tipo) {
		this.setTipoRetorno(tipo);
		return this;
	}
	public JcMetodo type(ToJcTipo tipo) {
		return type(tipo.toJcTipo());
	}
	public JcMetodo type(String tipo) {
		return this.type(new JcTipo(tipo));
	}
	public JcMetodo type(Class<?> tipo) {
		this.setTipoRetorno(tipo);
		return this;
	}
	public JcMetodo typeP2(Class<?> a, Class<?> b) {
		this.setTipoRetorno(new JcTipo(P2.class, a, b));
		return this;
	}
	public JcMetodo typeP2(Class<?> a, JcTipo b) {
		JcTipo tipo = new JcTipo(P2.class).addGenerics(a).addGenerics(b);
		this.setTipoRetorno(tipo);
		return this;
	}
	public JcMetodo type(Class<?> tipo, Class<?> generics) {
		this.setTipoRetorno(tipo);
		this.setGenericsRetorno(generics);
		return this;
	}
	public JcMetodo type(Class<?> tipo, String generics) {
		this.setTipoRetorno(tipo);
		this.addGenericsRetorno(generics);
		return this;
	}
	public JcMetodo type(Class<?> tipo, JcClasse generics) {
		this.setTipoRetorno(tipo);
		this.addGenericsRetorno(generics);
		return this;
	}

	public JcMetodo type(JcTipo tipo, JcTipo... generics) {
		setTipoRetorno(tipo);
		for (JcTipo gen : generics) {
			addGenericsRetorno(gen);
		}
		return this;
	}
	public JcMetodo type(JcTipo tipo, ToJcTipo... generics) {
		setTipoRetorno(tipo);
		if (generics != null) {
			for (ToJcTipo gen : generics) {
				addGenericsRetorno(gen);
			}
		}
		return this;
	}
	
	public JcMetodo type(Class<?> tipo, JcTipo... generics) {
		return type(new JcTipo(tipo), generics);
	}

	public JcMetodo type(Class<?> tipo, ToJcTipo... generics) {
		return type(new JcTipo(tipo), generics);
	}
	
	public JcTipos getTipos() {

		JcTipos list = new JcTipos();

		if (tipoRetorno != null) {
			tipoRetorno.addTipos(list);
		}

		parametros.forEach(o -> o.getTipo().addTipos(list));
		parametros.forEach(o -> o.getAnotacoes().getList().forEach(i -> list.add(i.getTipos())));

		if (!anotacoes.getList().isEmpty()) {
			anotacoes.getList().forEach(o -> {
				if (o.getTipo().getName().contains(".")) {
					list.add(o.getTipo());
				}
			});
		}

		for (JcTipo tipo : tipos.getList()) {
			list.add(tipo);
		}

		for (JcTipo tipo : throwsList) {
			list.add(tipo);
		}
		
		return list;
	}
	public final JcMetodo addAnotacao(JcAnotacao anotacao) {
		anotacoes.add(anotacao);
		return this;
	}
	public final JcMetodo addAnotacao(String tipo) {
		anotacoes.add(tipo);
		return this;
	}
	public final JcMetodo addAnotacao(JcTipo tipo) {
		anotacoes.add(tipo);
		return this;
	}
	public final JcMetodo addAnotacao(Class<? extends Annotation> classe) {
		anotacoes.add(classe);
		return this;
	}
	public final JcMetodo addAnotacao(Class<? extends Annotation> classe, String parametro) {
		anotacoes.add(classe, parametro);
		return this;
	}
//	(Class<? extends Annotation> classe, String parametros)

	public JcMetodo return_(String s) {
		return this.add("return " + s + ";");
	}

	public JcMetodo return_(int i) {
		if (getTipoRetorno() == null) {
			type(int.class);
		}
		return this.add("return " + i + ";");
	}

	public JcMetodo return_(boolean b) {
		if (getTipoRetorno() == null) {
			type(boolean.class);
		}
		return this.add("return " + b + ";");
	}

	public JcMetodo return_() {
		return this.add("return;");
	}
	public String getPenultimo() {
		return body.getPenultimo();
	}
	public String getLast() {
		return body.getLast();
	}
	public JcMetodo suppressWarningsUnchecked() {
		this.addAnotacao(SuppressWarnings.class, "\"unchecked\"");
		return this;
	}

	public JcMetodo ifStringNotEmpty(String s) {
		tipos.add(StringEmpty.class);
		if_("StringEmpty.notIs("+s+")");
		return this;
	}

	public JcMetodo ifStringEquals(String a, String b) {
		tipos.add(StringCompare.class);
		if_("StringCompare.eq("+a+", "+b+")");
		return this;
	}

	public JcMetodo elseifStringEquals(String a, String b) {
		tipos.add(StringCompare.class);
		elseif_("StringCompare.eq("+a+", "+b+")");
		return this;
	}

	public JcMetodo transactional() {
		addAnotacao("javax.transaction.Transactional");
		return this;
	}

	public void call(JcMetodo m) {
		call(m, m.parametros.toListString(o -> o.getNome()));
	}

	public void call(JcMetodo m, String... params) {
		call(m, ListString.array(params));
	}

	public void call(JcMetodo m, ListString params) {

		if (params.size() != m.parametros.size()) {
			throw new RuntimeException("qtd de parametros invalido");
		}

		add(m.getNome() + "(" + params.toString(", ") + ");");

	}

	public JcMetodo paramClass() {
		return paramClass(JcTipo.INTERROGACAO());
	}

	public JcMetodo paramClass(JcTipo generics) {
		param("classe", Class.class, generics);
		return this;
	}

	public JcMetodo forAs() {
		return forAs("as");
	}

	public JcMetodo forAs(String as) {
		tipos.add(Atributos.class);
		add("for(", Atributo.class, " a : " + as + ") {");
		return this;
	}

	public JcTipo setGenericsReverso(String nome) {
		genericsReverso = JcTipo.GENERICS(nome);
		return genericsReverso;
	}

	public void addComentario(String s) {
		comentarios.add(s);
	}

	public JcMetodo postConstruct() {
		addAnotacao(JcTipoProjeto.selected.postConstruct());
		return this;
	}

	public JcMetodo getMapping(String s) {
		addAnotacao(JcTipoProjeto.getMapping(s));
		return this;
	}

	public JcMetodo postMapping(String s) {
		addAnotacao(JcTipoProjeto.postMapping(s));
		return this;
	}
	
	public JcMetodo addThrows(JcTipo tipo) {
		throwsList.add(tipo);
		return this;
	}
	
}
