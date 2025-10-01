package gm.utils.javaCreate;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UList;
import gm.utils.comum.UType;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.number.Numeric;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Classe;
import gm.utils.reflection.Parametro;
import gm.utils.string.ListString;
import js.support.console;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.array.Itens;
import src.commom.utils.comum.KeyValue;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringTrim;

@Getter
public class JcTipo {

	private JcTipo extends_;
	private boolean isGenerics;
	private Boolean primitivo;
	private JcTipo reticenciasDe;
	
	private boolean extendsLigado = true;
	
	private Lst<JcTipo> generics;
	
//	@Setter
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Setter private boolean fullyQualifiedName;

	private static final List<JcTipo> GENERICS_NULL = null;


	private JcTipo() {}

	public static JcTipo INTERROGACAO() {
		return GENERICS("?");
	}

	public static JcTipo GENERICS(String name) {
		JcTipo o = new JcTipo();
		o.setName(name);
		o.isGenerics = true;
		return o;
	}
	
	private static final Lst<JcTipo> RETICENCIAS = new Lst<>();
	
	public static JcTipo RETICENCIAS(JcTipo tipo) {
		JcTipo o = RETICENCIAS.unique(i -> tipo.eq(i.getReticenciasDe()));
		if (o == null) {
			o = new JcTipo();
			o.setName("...");
			o.reticenciasDe = tipo;
			RETICENCIAS.add(o);
		}
		return o;
	}
	
	/*--------------------------*/

	public JcTipo(String s, List<JcTipo> generics) {

		s = StringTrim.plus(s);
		
		if (s.contains("<")) {
			
			if (generics == null || generics.isEmpty()) {
				
				String before = StringBeforeFirst.get(s, "<");
				s = StringAfterFirst.get(s, "<");
				s = StringBeforeLast.get(s, ">");
				
				if (s.contains("<")) {
					throw new NaoImplementadoException();
				}
				
				Lst<JcTipo> generics2 = new Lst<>();
				ListString.byDelimiter(s, ",").each(i -> generics2.add(descobre(i)));
				generics = generics2;
				s = before;
				
			} else {
				throw new RuntimeException("use addGenerics " + s);
			}
			
		}
		
		if (StringContains.is(s, " ")) {
			throw new RuntimeException("nome nao pode conter espacos: " + s);
		}

		if ("Calendar".contentEquals(s)) {
			s = "java.util.Calendar";
		}
		
		if (s.startsWith("java.lang.") && !s.contentEquals("java.lang.Class") && !s.contentEquals("java.lang.Object")) {
			s = StringAfterLast.get(s, ".");
			primitivo = true;
		} else if (!isPrimitivo(s)) {
			if (!StringContains.is(s, ".")) {
				throw new RuntimeException("O nome deve ser completo: " + s);
			}
			if (StringContains.is(s, "<")) {
				throw new RuntimeException("use addGenerics " + s);
			}
		}
		
		setName(s);

		if (generics != null) {
			for (JcTipo o : generics) {
				addGenerics(o);
			}
		}

	}

	public JcTipo(Class<?> classe, List<JcTipo> generics) {
		this(classe);
		for (JcTipo o : generics) {
			addGenerics(o);
		}
	}

	private static List<JcTipo> toGenerics(JcTipo... generics) {
		return UList.asList(generics);
	}
	private static List<JcTipo> toGenerics(Class<?>... generics) {
		return UList.map(generics, o -> new JcTipo(o));
	}
	private static List<JcTipo> toGenerics(String... generics) {
		return UList.map(generics, o -> new JcTipo(o));
	}
	private static List<JcTipo> toGenerics(JcClasse... generics) {
		return UList.map(generics, o -> o.getTipo());
	}
	private static List<JcTipo> toGenerics(Classe... generics) {
		return UList.map(generics, o -> new JcTipo(o));
	}

	/* String */
	public JcTipo(String nome) {
		this(nome, GENERICS_NULL);
	}
	public JcTipo(String nome, JcTipo... generics) {
		this(nome, toGenerics(generics));
	}
	public JcTipo(String nome, Class<?>... generics) {
		this(nome, toGenerics(generics));
	}
	public JcTipo(String nome, String... generics) {
		this(nome, toGenerics(generics));
	}
	public JcTipo(String nome, JcClasse... generics) {
		this(nome, toGenerics(generics));
	}
	public JcTipo(String nome, Classe... generics) {
		this(nome, toGenerics(generics));
	}

	/* JcTipo */
	public JcTipo(JcTipo o) {
		this(o.getName(), GENERICS_NULL);
	}
	public JcTipo(JcTipo o, JcTipo... generics) {
		this(o.getName(), toGenerics(generics));
	}
	public JcTipo(JcTipo o, Class<?>... generics) {
		this(o.getName(), toGenerics(generics));
	}
	public JcTipo(JcTipo o, String... generics) {
		this(o.getName(), toGenerics(generics));
	}
	public JcTipo(JcTipo o, JcClasse... generics) {
		this(o.getName(), toGenerics(generics));
	}

	private static String getName(Type type) {

		if (type instanceof Class) {
			Class<?> classe = (Class<?>) type;
			return classe.getName();
		}

		return type.getTypeName();

	}

	/* Class<?> */
	public JcTipo(Type o) {
		this(getName(o), GENERICS_NULL);
	}
	public JcTipo(Type o, JcTipo... generics) {
		this(getName(o), toGenerics(generics));
	}
	public JcTipo(Type o, Class<?>... generics) {
		this(getName(o), toGenerics(generics));
	}
	public JcTipo(Type o, String... generics) {
		this(getName(o), toGenerics(generics));
	}
	public JcTipo(Type o, JcClasse... generics) {
		this(getName(o), toGenerics(generics));
	}

	/* Classe */
	public JcTipo(Classe o) {
		this(o.getSimpleName(), GENERICS_NULL);
	}
	public JcTipo(Classe o, JcTipo... generics) {
		this(o.getSimpleName(), toGenerics(generics));
	}
	public JcTipo(Classe o, Class<?>... generics) {
		this(o.getSimpleName(), toGenerics(generics));
	}
	public JcTipo(Classe o, String... generics) {
		this(o.getSimpleName(), toGenerics(generics));
	}
	public JcTipo(Classe o, JcClasse... generics) {
		this(o.getSimpleName(), toGenerics(generics));
	}

	/* JcClasse */
	public JcTipo(JcClasse o) {
		this(o.getName(), GENERICS_NULL);
	}
	public JcTipo(JcClasse o, JcTipo... generics) {
		this(o.getName(), toGenerics(generics));
	}
	public JcTipo(JcClasse o, Class<?>... generics) {
		this(o.getName(), toGenerics(generics));
	}
	public JcTipo(JcClasse o, String... generics) {
		this(o.getName(), toGenerics(generics));
	}
	public JcTipo(JcClasse o, JcClasse... generics) {
		this(o.getName(), toGenerics(generics));
	}

	private static String getType(File file) {
		String s = file.toString();
		if (!s.endsWith(".java")) {
			throw new RuntimeException();
		}
		s = StringBeforeLast.get(s, ".");
		s = StringAfterFirst.get(s, "/src/main/java/");
		return s.replace("/", ".");
	}

	/* File */
	public JcTipo(File o) {
		this(getType(o), GENERICS_NULL);
	}
	public JcTipo(File o, JcTipo... generics) {
		this(getType(o), toGenerics(generics));
	}
	public JcTipo(File o, Class<?>... generics) {
		this(getType(o), toGenerics(generics));
	}
	public JcTipo(File o, String... generics) {
		this(getType(o), toGenerics(generics));
	}
	public JcTipo(File o, JcClasse... generics) {
		this(getType(o), toGenerics(generics));
	}

	/*--------------------------*/

	public boolean isPrimitivo() {
		if (primitivo == null) {
			primitivo = calculaPrimitivo();
		}
		return primitivo;
	}

	private boolean calculaPrimitivo() {
		return isPrimitivo(getName());
	}

	private boolean isPrimitivoMesmo() {
		return isPrimitivoMesmo(getName());
	}

	private static boolean isPrimitivo(String s) {
		
		if (s == null) {
			throw new NullPointerException("s == null");
		}

		if ("?".equals(s) || isPrimitivoMesmo(s)) {
			return true;
		}

		if (isDate(s) || "java.math.BigDecimal".contentEquals(s) || "java.math.BigInteger".contentEquals(s)) {
			return false;
		}

		if (UType.PRIMITIVAS_JAVA.contains(s)) {

			if (StringContains.is(s, "Calen")) {
				return isDate(s);
			}

			return true;

		}

		return false;

	}

	private static boolean isPrimitivoMesmo(String s) {
		return UType.PRIMITIVAS_JAVA_REAL.contains(s);
	}

	/*
	 * 001 - garantir que não seja mudada uma superClass por acidente
	 * */

	public JcTipo extends_(JcTipo tipo, String... generics) {
		if (isPrimitivo() || (extends_ != null)) {
			/* 001 */
			throw new RuntimeException();
		}
		extends_ = tipo;
		for (String s : generics) {
			addGenerics(s);
		}
		return this;
	}

	/* 001 */
	public JcTipo clearExtends() {
		extends_ = null;
		return this;
	}

	public JcTipo extends_(Class<?> classe) {
		return extends_(new JcTipo(classe));
	}
	public JcTipo addGenerics(Class<?> classe) {
		return this.addGenerics(new JcTipo(classe));
	}
	public JcTipo addGenerics(ToJcTipo classe) {
		return addGenerics(classe.toJcTipo());
	}
	public JcTipo addGenerics(JcClasse classe) {
		return this.addGenerics(classe.getTipo());
	}
	public JcTipo addGenerics(String nome) {
		return this.addGenerics(new JcTipo(nome));
	}
	public JcTipo addGenerics(JcTipo tipo) {
		if (isPrimitivo()) {
			throw new RuntimeException();
		}
		if (tipo.isPrimitivoMesmo() && !tipo.getName().contentEquals("?")) {
			throw new RuntimeException(tipo.getName());
		}
		if (generics == null) {
			generics = new Lst<>();
		}

		if ((tipo == this) || tipo.containsGenerics(this)) {
			throw new RuntimeException();
		}

		generics.add(tipo);
		return this;
	}

	//evitar recursividade
	private boolean containsGenerics(JcTipo o) {
		if (generics == null) {
			return false;
		}
		if (generics.contains(o)) {
			return true;
		}
		for (JcTipo gen : generics) {
			if (gen.containsGenerics(o)) {
				return true;
			}
		}
		return false;
	}

	public String syntaxDiamond() {
		return toString(true);
	}

	@Override
	public String toString() {
		if (reticenciasDe != null) {
			return "..." + reticenciasDe;
		}
		return toString(false);
	}

	private String toString(boolean diamond) {
		if (isPrimitivo()) {
			return getName();
		}

		String s;
		if (fullyQualifiedName || isGenerics) {
			s = getName();
		} else {
			s = StringAfterLast.get(getName(), ".");
		}

		if (generics != null) {
			if (diamond) {
				s += "<>";
			} else {
				String x = "";
				for (JcTipo jcTipo : generics) {
					x += ", " + jcTipo;
				}
				x = x.substring(2);
				s += "<" + x + ">";
			}
		}

		if (extends_ != null && extendsLigado) {
			s += " extends " + extends_;
		}

		return s;
	}
	
	public JcTipo setExtendsLigado(boolean value) {
		
		if (this.extendsLigado == value) {
			return this;
		}
		
		this.extendsLigado = value;
		
		if (extends_ != null) {
			extends_.setExtendsLigado(value);
		}
		
		if (generics != null) {
			generics.forEach(i -> i.setExtendsLigado(value));
		}
		
		if (reticenciasDe != null) {
			reticenciasDe.setExtendsLigado(value);
		}
		
		return this;
		
	}

	public JcTipo clearGenerics() {
		generics = null;
		return this;
	}

	private void addTipos(JcTipos list, ListString pilha) {
		if (isPrimitivo()) {
			return;
		}
		list.add(this);
		if (generics != null) {
			for (JcTipo o : generics) {
				if (!pilha.contains(o.getName())) {
					pilha.add(o.getName());
					o.addTipos(list, pilha);
				}
			}
		}
	}

	public void addTipos(JcTipos list) {
		addTipos(list, new ListString());
	}

	public JcTipos getTipos() {
		JcTipos tipos = new JcTipos();
		addTipos(tipos);
		if (extends_ != null) {
			tipos.add(extends_);
			tipos.add(extends_.getTipos());
		}
		return tipos;
	}

	public String getPkg() {
		return StringBeforeLast.get(getName(), ".");
	}

	public String getSimpleName() {
		if (fullyQualifiedName) {
			return getName();
		}
		return StringAfterLast.get("." + getName(), ".");
	}

	public boolean eq(String nome) {
		if (nome.startsWith("java.lang.")) {
			nome = StringAfterLast.get(nome, ".");
			return StringCompare.eq(getSimpleName(), nome);
		}
		return StringCompare.eq(getName(), nome) || StringCompare.eq(getSimpleName(), nome);
	}

	public boolean eq(Class<?> o) {
		return eq(o.getName());
	}

	public boolean eq(JcTipo o) {
		return eq(o.getName());
	}

	public boolean eq(JcClasse o) {
		return eq(o.getName());
	}

	private static Itens<String> TIPOS_LIST = Itens.build("Array","ArrayLst","List","Lst");

	public boolean isList() {
		Class<?> classe = getClasse();
		if (classe != null && UType.isList(classe)) {
			return true;
		}
		return TIPOS_LIST.contains(getName());
	}

	public boolean isDate() {
		return isDate(getName());
	}

	public static boolean isDate(String nome) {
		return UType.CLASSES_DATA.contains(nome);
	}

	public boolean isBigDecimal() {
		return getName().contentEquals(BigDecimal.class.getName()) || getName().contentEquals("BigDecimal") || getName().contentEquals("BigDecimal.class");
	}

	public boolean isDouble() {
		return getName().contentEquals(Double.class.getName()) || getName().contentEquals("Double") || getName().contentEquals("Double.class");
	}

	public boolean isNumeric() {
		return getName().startsWith(Numeric.class.getName());
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getClasse() {
		if (getType() instanceof Class) {
			return (Class<T>) getType();
		}
		return null;
	}
	
	private Type type;
	
	public Type getType() {
		
		if (type == null) {
			type = UClass.getClass(getName());
		}
		
		return type;
		
	}

	public static JcTipo descobre(final String string) {

		try {

			String str = StringTrim.plus(string);

			if (str.startsWith("class ") || str.startsWith("interface ")) {
				str = StringAfterFirst.get(str, " ");
			}

			if (UType.PRIMITIVAS_JAVA_REAL.contains(str)) {
				return new JcTipo(str);
			}

			if (!str.contains("<")) {
				if (str.contains(".")) {
					return new JcTipo(str);
				}
				return GENERICS(str);
			}

			ListString list = ListString.separaPalavras(str);
			list.trimPlus();
			list.juntarComAProximaSe(s -> s.contentEquals("."), "");
			list.juntarComASuperiorSe(s -> s.startsWith("."), "");
			list.removeIfEquals(",");

			Lst<Object> os = list.map(s -> {
				if (StringContains.is(s, ".")) {
					return new JcTipo(s);
				}
				if (s.contentEquals("<") || s.contentEquals(">")) {
					return new StringBox(s);
				}
				JcTipo tipoConhecido = getTipoConhecidoPeloSimpleName(s);

				if (tipoConhecido == null) {
					return GENERICS(s);
				}
				return tipoConhecido;
			});

			Lst<Object> ends = os.filter(s -> is(s,">"));

			while (os.size() > 1) {
				int i = os.indexOf(ends.remove(0));
				os.remove(i);
				i--;
				Lst<JcTipo> tipos = new Lst<>();
				while (!is(os.get(i),"<")) {
					tipos.add(0, (JcTipo) os.remove(i));
					i--;
				}
				os.remove(i);
				i--;
				JcTipo tipo = (JcTipo) os.get(i);
				for (JcTipo tp : tipos) {
					tipo.addGenerics(tp);
				}
			}

			return (JcTipo) os.get(0);

		} catch (Exception e) {
			SystemPrint.err(string);
			throw e;
		}

	}

	private static boolean is(Object s, String str) {
		return s instanceof StringBox && ((StringBox) s).eq(str);
	}

	public static JcTipo build(Atributo a) {
		return build(a.getField());
	}

	public static JcTipo build(Field field) {
		return JcTipo.descobre(field.getAnnotatedType().getType().getTypeName());
	}

	public static JcTipo build(Parametro param) {
		return JcTipo.descobre(param.getParameter().getParameterizedType().toString());
	}

	public static void main(String[] args) {
		console.log(descobre("java.util.Map<java.lang.String, java.util.Map<java.lang.Integer, T>>"));
	}

	public static JcTipo build(Type type) {
		JcTipo o = JcTipo.descobre(type.toString());
		o.type = type;
		return o;
	}

	/* tipos conhecicos */

	private static final Lst<JcTipo> TIPOS_CONHECIDOS = new Lst<>();
	public static Lst<JcTipo> TIPOS_CONHECIDOS_TEMP;

	public static void addTipoConhecido(JcTipo tipo) {
		if (!TIPOS_CONHECIDOS.anyMatch(o -> o.getName().contentEquals(tipo.getName()))) {
			TIPOS_CONHECIDOS.add(new JcTipo(tipo));
		}
	}

	public static void addTipoConhecido(Class<?> classe) {
		if (!TIPOS_CONHECIDOS.anyMatch(o -> o.getName().contentEquals(classe.getName()))) {
			TIPOS_CONHECIDOS.add(new JcTipo(classe));
		}
	}

	private static JcTipo getTipoConhecidoPeloSimpleName(String s) {

		Class<?> classe = UType.PRIMITIVAS_JAVA.get(s);

		if (classe != null) {
			return new JcTipo(classe);
		}

		JcTipo tipo = TIPOS_CONHECIDOS.unique(i -> i.getSimpleName().contentEquals(s));

		if (tipo != null) {
			return new JcTipo(tipo);
		}

		if (TIPOS_CONHECIDOS_TEMP != null) {

			tipo = TIPOS_CONHECIDOS_TEMP.unique(i -> i.getSimpleName().contentEquals(s));

			if (tipo != null) {
				return new JcTipo(tipo);
			}

		}

		return null;

	}
	
	@Override
	public JcTipo clone() {
		JcTipo o = new JcTipo();
		o.setName(getName());
		o.isGenerics = isGenerics;
		o.primitivo = primitivo;
		o.fullyQualifiedName = fullyQualifiedName;
		if (extends_ != null) {
			o.extends_ = extends_.clone();
		}
		if (reticenciasDe != null) {
			o.reticenciasDe = reticenciasDe.clone();
		}
		if (generics != null) {
			o.generics = generics.map(i -> i.clone());
		}
		o.extendsLigado = this.extendsLigado;
		return o;
	}
	
	public boolean hasGenerics() {
		return generics != null && generics.size() > 0;
	}
	
	static {
		addTipoConhecido(Lst.class);
		addTipoConhecido(KeyValue.class);
	}

	public String getNameVar() {
		return StringPrimeiraMinuscula.exec(getSimpleName());
	}

}
