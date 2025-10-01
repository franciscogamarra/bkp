package gm.utils.javaCreate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import jakarta.persistence.Id;

import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.javaCreate.annotations.JcAnotacaoGetSet;
import js.support.console;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringTrim;

@Getter
public class JcParametro {

	protected final JcAnotacoes anotacoes = new JcAnotacoes();
	private final String nome;
	private boolean array;
	
	@Setter
	private JcTipo tipo;
	protected boolean final_;

	private final Lst<JcTipo> outrosTipos = new Lst<>();
	private static final List<JcTipo> GENERICS_NULL = null;

	public JcParametro(String nome, JcTipo tipo, List<JcTipo> generics) {
		this.nome = StringTrim.plus(nome);
		this.tipo = tipo;
		if (generics != null) {
			for (JcTipo o : generics) {
				tipo.addGenerics(o);
			}
		}
	}

	private static List<JcTipo> toGenerics(JcTipo... generics) {
		return UList.asList(generics);
	}
	private static List<JcTipo> toGenerics(Type... generics) {
		return UList.map(generics, o -> new JcTipo(o));
	}
	private static List<JcTipo> toGenerics(String... generics) {
		return UList.map(generics, o -> new JcTipo(o));
	}
	private static List<JcTipo> toGenerics(JcClasse... generics) {
		return UList.map(generics, o -> o.getTipo());
	}

	/* JcTipo */
	public JcParametro(String nome, JcTipo tipo) {
		this(nome, tipo, GENERICS_NULL);
	}
	public JcParametro(String nome, JcTipo tipo, JcTipo... generics) {
		this(nome, tipo, toGenerics(generics));
	}
	public JcParametro(String nome, JcTipo tipo, Type... generics) {
		this(nome, tipo, toGenerics(generics));
	}
	public JcParametro(String nome, JcTipo tipo, String... generics) {
		this(nome, tipo, toGenerics(generics));
	}
	public JcParametro(String nome, JcTipo tipo, JcClasse... generics) {
		this(nome, tipo, toGenerics(generics));
	}

	/* Class<?> */
	public JcParametro(String nome, Type tipo) {
		this(nome, new JcTipo(tipo));
	}
	public JcParametro(String nome, Type tipo, JcTipo... generics) {
		this(nome, new JcTipo(tipo), generics);
	}
	public JcParametro(String nome, Type tipo, Type... generics) {
		this(nome, new JcTipo(tipo), generics);
	}
	public JcParametro(String nome, Type tipo, String... generics) {
		this(nome, new JcTipo(tipo), generics);
	}
	public JcParametro(String nome, Type tipo, JcClasse... generics) {
		this(nome, new JcTipo(tipo), generics);
	}

	/* String */
	public JcParametro(String nome, String tipo) {
		this(nome, new JcTipo(tipo));
	}
	public JcParametro(String nome, String tipo, JcTipo... generics) {
		this(nome, new JcTipo(tipo), generics);
	}
	public JcParametro(String nome, String tipo, Type... generics) {
		this(nome, new JcTipo(tipo), generics);
	}
	public JcParametro(String nome, String tipo, String... generics) {
		this(nome, new JcTipo(tipo), generics);
	}
	public JcParametro(String nome, String tipo, JcClasse... generics) {
		this(nome, new JcTipo(tipo), generics);
	}

	/* JcClasse */
	public JcParametro(String nome, JcClasse tipo) {
		this(nome, tipo.getTipo());
	}
	public JcParametro(String nome, JcClasse tipo, JcTipo... generics) {
		this(nome, tipo.getTipo(), generics);
	}
	public JcParametro(String nome, JcClasse tipo, Type... generics) {
		this(nome, tipo.getTipo(), generics);
	}
	public JcParametro(String nome, JcClasse tipo, String... generics) {
		this(nome, tipo.getTipo(), generics);
	}
	public JcParametro(String nome, JcClasse tipo, JcClasse... generics) {
		this(nome, tipo.getTipo(), generics);
	}

	public JcParametro array() {
		array = true;
		return this;
	}

	public final void setGenerics(JcClasse tipo) {
		this.setGenerics(tipo.getTipo());
	}
	public final void setGenerics(JcTipo tipo) {
		this.tipo.addGenerics(tipo);
	}
	public final void setGenerics(String tipo) {
		this.tipo.addGenerics(tipo);
	}
	public final void setGenericsNivel2(String tipo) {
		this.tipo.getGenerics().get(0).addGenerics(tipo);
	}
	public final void setGenerics(Type tipo) {
		this.setGenerics(new JcTipo(tipo));
	}
	public final JcParametro addAnotacao(JcTipo tipo) {
		anotacoes.add(tipo);
		return this;
	}
	public final JcParametro addAnotacao(String tipo) {
		anotacoes.add(tipo);
		return this;
	}
	public final JcParametro addAnotacao(Class<? extends Annotation> classe) {
		anotacoes.add(classe);
		return this;
	}
	public final JcAnotacao newAnotacao(Class<? extends Annotation> classe) {
		return anotacoes.add(classe);
	}
	public final JcAnotacao newAnotacao(JcTipo tipo) {
		return anotacoes.add(tipo);
	}
	public JcParametro addAnotacao(JcAnotacao value) {
		anotacoes.add(value);
		return this;
	}
	public JcParametro addAnotacao(JcAnotacaoGetSet value) {
		return addAnotacao(value.get());
	}

	public JcParametro generics(String tipo) {
		this.setGenerics(tipo);
		return this;
	}

	public JcParametro generics(Class<?> tipo) {
		this.setGenerics(tipo);
		return this;
	}

	public JcParametro generics(JcClasse tipo) {
		this.setGenerics(tipo);
		return this;
	}

	public JcParametro generics(JcTipo tipo) {
		this.setGenerics(tipo);
		return this;
	}

	@Override
	public String toString() {

		String s;

		if (anotacoes.has()) {
			s = anotacoes.toString() + " ";
		} else {
			s = "";
		}

		if (final_) {
			s += "final ";
		}

		s += tipo.toString();
		if (array) {
			s += "...";
		}
		return s + " " + nome;
	}

	public final JcTipos getTipos() {
		JcTipos tipos = tipo.getTipos();
		outrosTipos.forEach(i -> tipos.add(i));
		return tipos;
	}

	public static void main(String[] args) {
		console.log( new JcParametro("classe", Class.class).generics("? extends Entidade<?>") );
	}

	public JcParametro setFinal_(boolean value) {
		final_ = value;
		return this;
	}

	public JcParametro fullyQualifiedName() {
		tipo.setFullyQualifiedName(true);
		return this;
	}

	public boolean hasAnnotation(Class<? extends Annotation> classe) {
		return anotacoes.has(classe);
	}

	public boolean hasAnnotation(String simpleName) {
		return anotacoes.has(simpleName);
	}

	public boolean hasAnnotation(JcTipo anotacao) {
		return anotacoes.has(anotacao);
	}

	public boolean hasAnnotation(JcAnotacao anotacao) {
		return anotacoes.has(anotacao);
	}

	public final boolean isNumeric() {
		return tipo.isBigDecimal() || tipo.isDouble() || tipo.isNumeric();
	}

	public boolean isList() {
		return getTipo().isList();
	}

	public boolean isPrimitivo() {
		return getTipo().isPrimitivo();
	}

	public boolean isDate() {
		return getTipo().isDate();
	}
	public boolean isId() {

		if (this.hasAnnotation(Id.class) || getNome().contentEquals("id")) {
			return true;
		}

		return false;

	}

	public JcParametro pathVariable() {
		addAnotacao(JcTipoProjeto.pathVariable(getNome()));
		return this;
	}
	
	public JcParametro requestBody() {
		addAnotacao(JcTipoProjeto.requestBody());
		return this;
	}
	
	public JcParametro requestHeader() {
		addAnotacao(JcTipoProjeto.requestHeader());
		return this;
	}

}
