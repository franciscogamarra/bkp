package gm.utils.jpa.constructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.hibernate.validator.constraints.br.CPF;

import gm.utils.abstrato.ExtraidId;
import gm.utils.classes.UClass;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UExpression;
import gm.utils.comum.UObject;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.jpa.NativeSelectMap;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.criterions.MontarQueryNativa;
import gm.utils.jpa.select.SelectBase;
import gm.utils.lambda.F1;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import lombok.Setter;
import src.commom.utils.cp.UCpf;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringPrimeiraMinuscula;
//@Getter @Setter
public abstract class Pojo {

	private List<?> itens;
	private SelectBase<?,?,?> selectBase;
	String prefixo;
	List<PojoCampo> campos;

	@Setter
	private boolean formatarValores;

	@Setter
	private boolean formatarPercentuais;

	@SuppressWarnings("unused")
	private Pojo pai;
//	private boolean booleanFormatado;

	public Pojo(Pojo pai, String prefixo){
		this.pai = pai;
		itens = pai.itens;
		campos = pai.campos;
		this.prefixo = pai.prefixo + prefixo + ".";
	}

	public Pojo(List<?> itens) {
		this.itens = itens;
		prefixo = "";
		pai = this;
		campos = new ArrayList<>();
	}

	public Pojo(SelectBase<?,?,?> selectBase) {
		this.selectBase = selectBase;
		prefixo = "";
		pai = this;
		campos = new ArrayList<>();
	}

	public Pojo(Object o) {
		prefixo = "";
		pai = this;
		campos = new ArrayList<>();
		List<Object> list = new ArrayList<>();
		list.add(o);
		itens = list;
	}

//	public Pojo sequencial(){
//		campos.add("sequencial");
//		return this;
//	}
//	public Pojo aux(String alias){
//		campos.add("aux");
//		getAliases().put("aux", alias);
//		return this;
//	}

	public Pojo add(String campo) {
		return add(campo, null, null);
	}
	public Pojo add(Atributo a, F1<Object, String> function) {
		return add(a.nome(), function);
	}
	public Pojo add(String campo, F1<Object, String> function) {
		return add(campo, null, function);
	}
	public Pojo add(String campo, String alias) {
		return add(campo, alias, null);
	}
	public void add(Atributo a) {
		add(a.nome());
	}
//	public Pojo asStatus(String alias) {
//		return add(campo, alias, null);
//	}

	public void remove(String campo) {
		Predicate<PojoCampo> filter = o -> o.getNome().equalsIgnoreCase(campo);
		campos.removeIf(filter);
	}

	public Pojo add(String campo, String alias, F1<Object, String> function) {
		PojoCampo o = new PojoCampo();
		o.setNome(prefixo+campo);
		campos.removeIf(new PojoRemoveCampo(o.getNome()));
		o.setFunction(function);

		if (StringEmpty.is(alias)) {

			if (o.getNome().contains(".")) {

				ListString split = ListString.split(o.getNome(), ".");

				split.primeirasMaiusculas();

				alias = "";

				for (String s : split) {

					if (s.endsWith("()")) {

						s = StringBeforeLast.get(s, "()");

						String before;

						if (StringContains.is(s, ".")) {
							before = StringBeforeLast.get(s, ".") + ".";
							s = StringAfterLast.get(s, ".");
						} else {
							before = "";
						}

						if (s.startsWith("Get")) {
							s = StringAfterFirst.get(s, "Get");
						}

						s = before + s;

					}

//					s = S.primeiraMaiuscula(s);
					alias += s;

				}

				alias = StringPrimeiraMinuscula.exec(alias);

			} else {
				alias = o.getNome();
			}

		}

		o.setAlias(alias);

		campos.add(o);
		return this;
	}

	private static Atributo getAtributo(String campo, Class<?> classe){
		Atributos as = AtributosBuild.get(classe);
		if (!campo.contains(".")) {
			return as.get(campo);
		}
		String before = StringBeforeFirst.get(campo, ".");
		Atributo atributo = as.get(before);
		campo = StringAfterFirst.get(campo, ".");
		return getAtributo(campo, atributo.getType());
	}

	public MapSO unique() {
		List<MapSO> list = list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	protected static final String NAO_ENCONTRADO = "([!>NAO_ENCONTRADO<!])";

	public List<MapSO> list() {

		List<MapSO> result = new ArrayList<>();

		if (itens == null) {
			return montaItens();
		}

		if (itens.isEmpty()) {
			return result;
		}

		Class<?> classe = UClass.getClass(itens.get(0));

		int sequencial = 0;
		for (Object o : itens) {
			MapSO map = new MapSO();
			for (PojoCampo campo : campos) {

				String nome = campo.getNome();

				if (campo.getFunction() != null) {
					String value = campo.getFunction().call(o);
					map.put(campo.getAlias(), value);
					continue;
				}

				String gerado = getGerado(o, nome);

				if (!NAO_ENCONTRADO.equals(gerado)) {
					map.put(campo.getAlias(), gerado);
					continue;
				}

				if ("sequencial".equals(nome)) {
					sequencial++;
					map.put("sequencial", sequencial);
					continue;
				}

				Object value;

				if (nome.contains("!avatar")) {
					nome = StringBeforeFirst.get(nome, "!");
					value = UExpression.getValue(nome, o);
					if (value != null) {
//							Entidade<?> entity = (Entidade<?>) value;
						throw UException.runtime("TODO");
//							value = FwBaseConfig.get().getUrlAvatar(entity, null);
					}
				} else {

					value = UExpression.getValue(nome, o);

					if ( nome.endsWith("()") ) {
//						TODO desenvolver
						throw UException.runtime("implementar: " + nome);
					}
					Atributo a = getAtributo(nome, classe);
					if (a != null) {
						value = format(a, value);
					}

				}

				if (!UObject.isEmpty(value)) {
					map.put(campo.getAlias(), value);
				}

			}
			result.add(map);
		}

		return result;

	}

	protected abstract String getGerado(Object o, String nome);

	private List<MapSO> montaItens() {

		ListString colNames = new ListString();

		Map<String, String> select = new HashMap<>();
		for (PojoCampo campo : campos) {
			select.put(campo.getNome(), campo.getAlias());
			colNames.add(campo.getAlias());
		}
		Criterio<?> c = selectBase.getC();
		MontarQueryNativa qn = c.getQueryNativa();
		qn.setSelect(select);
		qn.getResult().saveTemp();
		String sql = qn.getResult().join("\n");
		NativeSelectMap selectMap = new NativeSelectMap(sql);
		return selectMap.map();

	}

	private Object format(Atributo a, Object x) {

		if ( UObject.isEmpty(x) ) {
			return null;
		}

		if ( ("aux".equals(a.nome()) && (x.getClass() == boolean.class || x.getClass() == Boolean.class)) || a.isBoolean() ) {
//			if (booleanFormatado) {
//				if (U.isTrue(x)) {
//					return "Sim";
//				} else {
//					return "Não";
//				}
//			} else {
			return UBoolean.isTrue(x);
//			}
		}

		if (a.isDate()) {

			Data data = Data.to(x);

			Class<?> fwDia = UClass.getClass("gm.fw.model.Dia");

			if (fwDia != null && a.is(fwDia)) {
				return data.format("[ddd] [dd]/[mm]/[yyyy]");
			}

			Temporal temporal = a.getAnnotation(Temporal.class);
			if (TemporalType.TIMESTAMP.equals(temporal.value())) {
				return data.format("[ddd] [dd]/[mm]/[yyyy] [hh]:[nn]");
			}
			return data.format("[ddd] [dd]/[mm]/[yyyy]");

		}

		if ( a.isString() ) {
			CPF cpf = a.getAnnotation(CPF.class);
			if (cpf != null) {
				return UCpf.format(StringParse.get(x));
			}
			return StringParse.get(x);
		}

		if (a.isFk()) {
			if (x instanceof Integer || x instanceof Long) {
				return x;
			}
			return ExtraidId.exec(x);
		}

		Class<?> type;

		if ("aux".equals(a.nome())) {
			type = x.getClass();
		} else {
			type = a.getType();
		}

		if ( type.equals(Integer.class) ) {
			return x;
//			return U.toString(x);
		}

		if ( type.equals(BigDecimal.class) ) {
			BigDecimal b = (BigDecimal) x;
			int fraction = a.getAnnotation(Digits.class).fraction();

			Numeric<?> n;

			if (fraction == 1) {
				n = new Numeric1(b);
			} else if (fraction == 2) {
				n = new Numeric2(b);
			} else {
				n = Numeric.toNumeric(b, fraction);
			}

			if (!formatarValores) {
				return Numeric.toNumeric(b, fraction).toDouble();
			}
			if (n.isZero()) {
				return "";
			}
			return "R$ " + n.toString();

		}

		return StringParse.get(x);

	}

	public static Pojo get(Class<?> classe, List<?> list) {
		String s = "auto."+classe.getSimpleName() + "Pojo";
		Class<? extends Pojo> classePojo = UClass.getClass(s);
		if (classePojo == null) {
			throw UException.runtime("classePojo == null: " + s);
		}
		return UClass.newInstance(classePojo, list);
	}

	public abstract Pojo all();

	public Pojo subPojo(Atributo a){
		throw UException.runtime("Nenhuma FK na classe");
	}

}
