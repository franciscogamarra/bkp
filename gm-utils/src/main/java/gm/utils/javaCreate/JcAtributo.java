package gm.utils.javaCreate;

import jakarta.persistence.Convert;

import gm.utils.anotacoes.Lookup;
import gm.utils.javaCreate.annotations.JcDigits;
import gm.utils.number.Numeric;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Obrig;
import src.commom.utils.object.Safe;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;

@Getter @Setter
public class JcAtributo extends JcParametro {

	private JcAtributos atributos;

	private String inicializacao;
	private boolean inicializacaoNew = false;
	private JcClasse inicializacaoOverride;

	private String acesso = "private";
	private boolean statico = false;
	private boolean getter = false;
	private boolean setter = false;
	private boolean setterThis = false;
	private boolean getterLombok = false;
	private boolean setterLombok = false;
	private boolean transient_ = false;
	private boolean builder = false;
	private boolean enum_ = false;

	private ListString comentarios;
	
	private String builderNome;
	private String paramsNew;

	public JcAtributo(String nome, Class<?> tipo) {
		super(nome, tipo.getName());
	}
	public JcAtributo(String nome, String tipo) {
		super(nome, tipo);
	}
	public JcAtributo(String nome, JcClasse tipo) {
		super(nome, tipo);
	}
	public JcAtributo(String nome, JcTipo tipo) {
		super(nome, tipo);
	}

	@Override
	public String toString() {

		String s = getAnotacoes().toString();

		if (!StringEmpty.is(s)) {
			s += " ";
		}
		return s + getAssinatura();
	}

	private String getAssinatura() {
		String s = acesso + " "
		+ (statico?"static ":"")
		+ (final_?"final ":"")
		+ (transient_?"transient ":"")
		+ getTipo()
		+ " " + getNome();

		boolean inicializou = false;

		if (inicializacaoNew) {
			s += " = new " + getTipo().syntaxDiamond() + "(";
			if (paramsNew != null) {
				s += paramsNew;
			}
			s += ")";
			inicializou = true;
		} else if (inicializacao != null) {
			s += " = " + inicializacao;
			inicializou = true;
		}

		if (inicializacaoOverride == null) {
			s += ";";
			s = s.trim();

			if (!inicializou && getClasse().jsClass) {
				s += "//@Auto";
			}

			return s;
		}

		ListString list = inicializacaoOverride.get();

		while (!list.get(0).startsWith("public class")) {
			list.remove(0);
		}

		list.remove(0);

		if (list.isEmpty()) {
			s += " {}";
		} else {
			list.removeLast();
			list.add();
			list.add("}");
			s += " {\n" + list.toString("\n");
		}

		s += ";";
		return s;

	}

	public ListString toListString() {
		ListString list = new ListString();
		
		if (comentarios != null) {
			if (comentarios.size() == 1) {
				list.add("/* "+comentarios.get(0)+" */");
			} else {
				list.add("/*");
				list.add(comentarios);
				list.add("*/");
			}
		}
		
		list.add(getAnotacoes().toListString());

		list.sort((a, b) -> {
			if (a.length() < b.length()) {
				return -1;
			}
			if (b.length() < a.length()) {
				return 1;
			}
			return a.compareTo(b);
		});

		list.add(getAssinatura());

		if (inicializacaoOverride == null) {
			list.removeEmptys();
		} else {
			list.add();
		}

		return list;

	}

	public JcAtributo inicializacao(String s) {
		setInicializacao(s);
		return this;
	}

	public JcAtributo public_() {
		setAcesso("public");
		return this;
	}
	public JcAtributo protected_() {
		setAcesso("protected");
		return this;
	}
	public JcAtributo default_() {
		setAcesso("");
		return this;
	}
	public JcAtributo static_() {
		statico = true;
		return this;
	}
	public JcAtributo transient_() {
		transient_ = true;
		return this;
	}
	public JcAtributo final_() {
		final_ = true;
		return this;
	}

	@Override
	public final JcAtributo generics(Class<?> tipo) {
		this.setGenerics(tipo);
		return this;
	}

	@Override
	public final JcAtributo generics(JcClasse tipo) {
		this.setGenerics(tipo);
		return this;
	}

	@Override
	public JcAtributo generics(String tipo) {
		return (JcAtributo) super.generics(tipo);
	}

	@Override
	public JcAtributo generics(JcTipo tipo) {
		return (JcAtributo) super.generics(tipo);
	}

	public JcAtributo getterLombok() {
		this.addAnotacao(Getter.class);
		getterLombok = true;
		return this;
	}
	public JcAtributo setterLombok() {
		this.addAnotacao(Setter.class);
		setterLombok = true;
		return this;
	}
	public JcAtributo getter() {
		getter = true;
		return this;
	}
	public JcAtributo setter() {
		if (setterThis) {
			throw new RuntimeException("setterThis ja chamado");
		}
		setter = true;
		return this;
	}
	public JcAtributo setterThis() {
		setter = false;
		setterThis = true;
		return this;
	}
	public JcAtributo builder() {
		builder  = true;
		return this;
	}
	public JcAtributo builder(String nome) {
		builderNome = nome;
		return this;
	}

	public JcAtributo inicializacaoNew() {
		setInicializacaoNew(true);
		return this;
	}

	@Override
	public JcAtributo fullyQualifiedName() {
		super.fullyQualifiedName();
		return this;
	}

	public JcAtributo setInicializacao(String value) {
		if (value != null) {
			if (inicializacaoNew) {
				throw new RuntimeException("inicializacaoNew == true");
			}
			inicializacao = value;
		} else {
			inicializacao = null;
		}
		return this;
	}

	public JcAtributo setInicializacaoNew(boolean value) {
		if (value) {
			if (inicializacao != null) {
				throw new RuntimeException("inicializacao != null");
			}
			inicializacaoNew = true;
		} else {
			inicializacaoNew = false;
		}
		return this;
	}

	public JcAtributo setInicializacaoNew(String params) {
		paramsNew = params;
		return setInicializacaoNew(true);
	}

	public boolean typeIs(JcClasse o) {
		return getTipo().eq(o);
	}

	public boolean typeIs(Class<?> o) {
		return getTipo().eq(o);
	}

//	@Deprecated
	public boolean is(Class<?> o) {
		return typeIs(o);
	}

	public String getNomeValue() {
		if (isLookup()) {
			JcAnotacao lookup = anotacoes.get(Lookup.class);
			String vinculo = lookup.getParam("vinculo");
			return vinculo + "." + getReal().getNomeValue();
		}
		return getNome();
	}

	public boolean isLookup() {
		return anotacoes.has(Lookup.class);
	}

	public JcAtributo getReal() {
		if (!isLookup()) {
			return this;
		}

		try {
			JcAnotacao lookup = anotacoes.get(Lookup.class);
			String vinculo = lookup.getParamObrig("vinculo");
			String resultado = lookup.getParamObrig("resultado");
			JcAtributo a = atributos.get(vinculo).getReal();
			ListString campos = ListString.split(resultado, ".");

			JcClasse tipoApontado = JcClasse.getInstancia(a.getTipo());

			if (tipoApontado == null) {
				throw new RuntimeException("A classe " + a.getTipo().getName() + " deve ser construida antes desta, pois esta precisa ler os atributos daquela");
			}

			for (String campo : campos) {
				a = tipoApontado.getAtributos().getObrig(campo).getReal();
			}
			return a;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar ler o atributo real de " + this, e);
		}
	}

	public boolean isString() {
		return typeIs(String.class);
	}

	public boolean isFk() {
		if (isPrimitivo() || isList() || isDate() || isNumeric()) {
			return false;
		}
		return true;
	}
	private boolean isStatic() {
		return statico;
	}

	private boolean isTransient() {
		return transient_;
	}

	public JcAtributo constant(ListString init) {
		return constant(JcClasse.toInicializacao(init));
	}

	private void validaNomeConstant() {
		
		if (getNome().contentEquals("serialVersionUID")) {
			return;
		}
		
		if (!StringCompare.eq(getNome(), getNome().toUpperCase())) {
			StringCompare.eq(getNome(), getNome().toUpperCase());
			throw new RuntimeException("Nome de constante não obedece os padrões java: " + getNome());
		}
	}
	
	public JcAtributo constant(String init) {
		validaNomeConstant();
		if (StringEmpty.is(init)) {
			throw new RuntimeException("Constante deve ter um valor de inicialização: " + getNome());
		}
		static_().final_();
		setInicializacao(init);
		return this;
	}

	@Override
	public boolean isId() {

		if (super.isId()) {
			return true;
		}

		if (isTransient() || isStatic() || (atributos.getId() != null && atributos.getId() != this)) {
			return false;
		}

		if (atributos.getClasse() != null) {
			String sn = atributos.getClasse().getSimpleName();
			if (getNome().contentEquals("id" + sn)) {
				return true;
			}
		}

		return false;
	}

	public JcClasse getClasse() {
		if (atributos == null) {
			return null;
		}
		return atributos.getClasse();
	}

	public String getClassName() {
		return Safe.get(getClasse(), o -> o.getSimpleName(), () -> "{nullClass}");
	}

	public String getNomeFull() {
		return getClassName() + "." + getNome();
	}

	public final int digitsFraction() {

		if (getTipo().getName().startsWith(Numeric.class.getSimpleName())) {
			return IntegerParse.toInt(StringAfterLast.get(getTipo().getSimpleName(), "Numeric"));
		}
		return (
			Obrig.checkWithDynamicMessage(
				JcDigits.from(anotacoes),
				() -> "O atributo " + getNomeFull() + " deve possuir a annotation @Digits"
			).getFraction()
		);

	}
	public void addConverter(JcTipo converter) {
		JcAnotacao a = new JcAnotacao(Convert.class);
		a.addParametro("converter", converter.getSimpleName() + ".class");
		addAnotacao(a);
		getOutrosTipos().add(converter);
	}
	
	public void addComentario(String s) {
		if (comentarios == null) {
			comentarios = new ListString();
		}
		comentarios.add(s);
	}

}
