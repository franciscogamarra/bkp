package gm.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UAssert;
import gm.utils.comum.UBoolean;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.TableSchema;
import gm.utils.lambda.F1;
import gm.utils.string.ListString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringEmpty;

public class Atributos extends Lst<Atributo> {

	private static final long serialVersionUID = 1;
	private Class<?> classe;
	private Atributo id;
	private Atributo nome;
	
	public void setId(Atributo id) {
		this.id = id;
	}

	public Atributos(Atributo... as) {
		for (Atributo a : as) {
			add(a);
		}
	}

	private static final ListString CAMPOS_PROIBIDOS_NOME = ListString.array(
		"serialVersionUID","backtrace","override","reflectionFactory","securityCheckCache",
		"printStackWhenAccessFails","printStackPropertiesSet","clazz","slot"
	);
	private static final ListString CAMPOS_PROIBIDOS_FULL = ListString.array(
		"java.lang.reflect.Field.name", "java.lang.reflect.Field.type",
		"java.lang.reflect.Field.modifiers",//, "java.lang.reflect.Field.signature"
		"java.lang.Throwable.detailMessage","java.lang.Throwable.UNASSIGNED_STACK",
		"java.lang.Throwable.cause","java.lang.Throwable.stackTrace","java.lang.Throwable.depth",
		"java.lang.Throwable.SUPPRESSED_SENTINEL","java.lang.Throwable.suppressedExceptions","java.lang.Throwable.NULL_CAUSE_MESSAGE",
		"java.lang.Throwable.SELF_SUPPRESSION_MESSAGE","java.lang.Throwable.CAUSE_CAPTION","java.lang.Throwable.SUPPRESSED_CAPTION",
		"java.lang.Throwable.EMPTY_THROWABLE_ARRAY","java.lang.Throwable.$assertionsDisabled",
		"java.sql.SQLException.SQLState","java.sql.SQLException.vendorCode","java.sql.SQLException.next",
		"java.sql.SQLException.nextUpdater",
		""
	);
	private static final ListString CAMPOS_PROIBIDOS_CONTAINS = ListString.array("$jacocoData");

	private static boolean ehProibido(Field field, boolean erro) {
		String nome = field.getName();
		if (CAMPOS_PROIBIDOS_NOME.contains(nome)) {
			return true;
		}
		for (String s : CAMPOS_PROIBIDOS_CONTAINS) {
			if (nome.contains(s)) {
				return true;
			}
		}

		String s = field.toString();
		s = StringAfterLast.get(s, " ");
		
		if (erro) {
			SystemPrint.ln("\""+s+"\",");
			System.exit(0);
		}
		
		if (CAMPOS_PROIBIDOS_FULL.contains(s)) {
			return true;
		}
//		SystemPrint.ln(s);
		return false;
	}

	public Atributos(Class<?> classe){

		if (UType.PRIMITIVAS_JAVA_REAL.contains(classe)) {
			throw new RuntimeException("??? " + classe);
		}
		
//		if (java.lang.reflect.Field.class == classe) {
//			throw new RuntimeException("???");
//		}

//		String sn = classe.getSimpleName();

		this.classe = classe;

		List<Field> fields = getFields(classe);
		Metodos metodos = ListMetodos.get(classe);
		metodos.removeAbstracts();
		metodos.removeIf(o -> !o.getDeclaringClasseImpl().equals(classe));
		for (Field field : fields) {
			String name = field.getName();
			if (ehProibido(field, false)) {
				continue;
			}
			Atributo a = new Atributo(classe);
			a.setField(field);
			a.setSetMethod(metodos.get("set" + name, 1));
			a.setGetMethod(metodos.get("get" + name, 0));
			if (a.getGetMethod() == null) {
				a.setGetMethod(metodos.get("is" + name, 0));
			}
			if (a.isId()) {
				id = a;
			} else {
				add(a);
			}
		}
		getId();
		
//		ordenarFields();
		
	}

	public void ordenarFields() {
		
		try {
			
//			if (classe.getSimpleName().contentEquals("Entidade")) {
//				System.out.println();
//			}
			
			ListString load = UClass.getJavaFile(classe).load();
			load.trimPlus();
			
			sort((a, b) -> {
				
				String rowa = load.filter(i -> i.startsWith(a.getDeclaracao())).getFirst();
				if (rowa == null) {
					return -1;
				}
				
				String rowb = load.filter(i -> i.startsWith(b.getDeclaracao())).getFirst();
				if (rowb == null) {
					return 1;
				}
				
				return IntegerCompare.compare(load.indexOf(rowa), load.indexOf(rowb));
				
			});
			
			
		} catch (Exception e) {
			// ignore
		}
		
	}

	public void loadInfoBanco(ConexaoJdbc con) {
		String s = "";
		s += " select ";
		s += "   column_name as nome ";
		s += " , case ";
		s += "     when data_type = 'character varying' then character_maximum_length ";
		s += "     when data_type = 'numeric' then numeric_precision ";
		s += "     when data_type = 'text' then 250 ";
		s += "   end as length ";
		s += " , case ";
		s += "     when data_type = 'numeric' then numeric_scale ";
		s += "   end as scale ";
		s += " , is_nullable";
		s += " from information_schema.columns ";
		s += " where lower(table_schema||'.'||table_name) = '" + TableSchema.get(classe).toLowerCase() + "' ";
		List<Object[]> list = con.selectArray(s);
		for (Object[] o : list) {
			Atributo a = get((String) o[0]);
			if (a == null) {
				continue;
			}
			a.setExisteNoBanco(con);
			a.setLengthBanco(IntegerParse.toInt(o[1]));
			a.setScale(IntegerParse.toInt(o[2]));
			a.setAceitaNulos(UBoolean.toBoolean(o[3]));
		}
	}

	private static List<Field> getFields(Class<?> c){

		List<Field> result = new ArrayList<>();

		ListClass classes = new ListClass();
		while (c != null && c != java.lang.Enum.class && c != java.lang.Object.class) {
			classes.add(0, c);
			c = c.getSuperclass();
		}

		while (!classes.isEmpty()) {
			c = classes.remove(0);
			Field[] list = c.getDeclaredFields();
			for (Field field : list) {
				if (ehProibido(field, false)) {
					continue;
				}
				try {
					field.setAccessible(true);
				} catch (Exception e) {
					ehProibido(field, true);
					throw e;
				}
				result.add(field);
			}
		}

		return result;

	}
	
	public Atributo getId() {
		if (id == null) {
			for (Atributo a : this) {
				if (a.isId()) {
					id = a;
//					remove(id);
					return id;
				}
			}
		}
		return id;
	}

	public Atributo getNome() {
		if (nome != null) {
			return nome;
		}
		for (Atributo a : this) {
			if (a.isNome()) {
				nome = a;
				return nome;
			}
		}
		nome = get("descricao");
		if (nome != null) {
			return nome;
		}
		nome = get("titulo");
		if (nome != null) {
			return nome;
		}
		nome = get("identificacao");
		if (nome != null) {
			return nome;
		}
		nome = get("codigo");
		if (nome != null) {
			return nome;
		}
		nome = get("numero");
		if (nome != null) {
			return nome;
		}
		nome = get("texto");
		if (nome != null) {
			return nome;
		}
		nome = get("text");
		return nome;
	}

	private Atributo nomeAny;

	public Atributo getNomeAny() {
		if (nomeAny != null) {
			return nomeAny;
		}
		nomeAny = getNome();
		if (nomeAny != null) {
			return nomeAny;
		}
		Atributos asString = getWhereType(String.class);
		if (!asString.isEmpty()) {
			nomeAny = asString.get(0);
			return nomeAny;
		}
		nomeAny = getId();
		if (nomeAny == null) {
			Atributos itens = filter(a -> !a.isList());
			if (itens.isEmpty()) {
				throw new RuntimeException("???");
			}
			nomeAny = itens.get(0);
		}
		return nomeAny;
	}

	public Atributo getNomeObrig() {
		nome = getNome();
		if (nome == null) {
			throw UException.runtime("Nenhum atributo com nome como principal para a classe " + classe.getSimpleName());
		}
		return nome;
	}

	public boolean contains(String fieldName) {
		return get(fieldName) != null;
	}

	public Atributo getObrig(String fieldName) {
		Atributo a = get(fieldName);
		if (a == null) {
			throw UException.runtime( "N\u00e3o encontrado o atributo " + fieldName + " na classe " + classe.getSimpleName() );
		}
		return a;
	}

	public Atributo get(final String fieldName) {
		
		Atributos as = filter(a -> a.eq(fieldName.trim()));
		
		if (!as.isEmpty()) {

			if (as.size() == 1) {
				return as.get(0);
			}
			
			Atributos asEqualsCase = as.filter(a -> a.getNome().contentEquals(fieldName));
			
			if (asEqualsCase.size() == 1) {
				return asEqualsCase.get(0); 
			}
			
		}
		
		String s = fieldName.trim();
		
		
		if (getId() != null && (id.getField().getName().equalsIgnoreCase(s) || "id".equals(s))) {
			return id;
		}
		for (Atributo atributo : this) {
			if (atributo.getField().getName().equalsIgnoreCase(s) || atributo.getColumnName().equalsIgnoreCase(s)) {
				return atributo;
			}
		}
		s = s.replace("_", "");
		for (Atributo atributo : this) {
			if (atributo.getField().getName().equalsIgnoreCase(s) || atributo.getColumnName().equalsIgnoreCase(s)) {
				return atributo;
			}
		}
		return null;
	}

	public boolean isTransient(String fieldName) {
		return get(fieldName).isTransient();
	}

	@Override
	public boolean exists(Predicate<Atributo> predicate) {
		return !filter(predicate).isEmpty();
	}

	@Override
	public Atributos filter(Predicate<Atributo> predicate) {
		List<Atributo> list = stream().filter(predicate).collect(Collectors.toList());
		Atributos atributos = new Atributos();
		atributos.addAll(list);
		atributos.setId(getId());
		return atributos;
	}

	public Atributos getWhereType(Class<?>... types) {

		Atributos list = filter(o -> o.typeIn(types));

		list.id = id;
		for (Atributo a : this) {
			for (Class<?> type : types) {
				if (a.is(type)) {
					list.add(a);
					break;
				}
			}
		}
		return list;
	}
	public Atributos getLists() {
		Atributos list = new Atributos();
		list.id = id;
		for (Atributo a : this) {
			if (a.isList()) {
				list.add(a);
			}
		}
		return list;
	}
	public Atributos getWhereIsListOf(Class<?> type) {
		Atributos list = new Atributos();
		list.id = id;
		for (Atributo a : this) {
			if (a.isListOf(type)) {
				list.add(a);
			}
		}
		return list;
	}

	public Atributos getWhereAnnotation(Class<? extends Annotation> annotation) {
		Atributos list = new Atributos();
		for (Atributo a : this) {
			if (a.hasAnnotation(annotation)) {
				list.add(a);
			}
		}
		return list;
	}

	public Atributos getWhereNomeLike(String s) {
		Atributos list = new Atributos();
		for (Atributo a : this) {
			if (a.getField().getName().contains(s)) {
				list.add(a);
			}
		}
		return list;
	}

	public Atributos getWhereNomeEndsWith(String s) {
		Atributos list = new Atributos();
		for (Atributo a : this) {
			if (a.getField().getName().endsWith(s)) {
				list.add(a);
			}
		}
		return list;
	}

	public Atributos removeByType(Class<?> type) {
		Atributos list = getWhereType(type);
		removeAll(list);
		return list;
	}

	public <T extends Annotation> Atributos filterByAnnotation(Class<T> annotation) {
		Atributos as = new Atributos();
		for (Atributo a : this) {
			if (a.hasAnnotation(annotation)) {
				as.add(a);
			}
		}
		return as;
	}

	public <T extends Annotation> Atributos removeByAnnotation(Class<T> annotation) {
		Atributos list = getWhereAnnotation(annotation);
		removeAll(list);
		return list;
	}

	public void removeTransients() {
		removeIf(AtributoFilters.filterTransients);
	}

	void removeNaoPersistiveis() {
		removeTransients();
		removeStatics();
		removeLists();
		removeOneToOneFake();
		// remove("excluido");
	}

	public Atributos removePrimitivas() {
		Predicate<Atributo> filter = o -> UType.isPrimitiva(o.getType());
		removeIf(filter);
		return this;
	}

	public Atributos removeStatics() {
		Predicate<Atributo> filter = o -> o.isStatic();
		removeIf(filter);
		return this;
	}

	public Atributos getStatics() {
		Atributos list = new Atributos();
		for (Atributo a : this) {
			if (a.isStatic()) {
				list.add(a);
			}
		}
		return list;
	}

	public void removeFinais() {
		Predicate<Atributo> filter = o -> o.isFinal();
		removeIf(filter);
	}

	public Atributo getAtivo() {
		for (Atributo a : this) {
			if (a.isAtivo()) {
				return a;
			}
		}
		return null;
	}

	public void setValues(Object o) {
		for (Atributo a : this) {
			a.setValue(o);
		}
		id.setValue(o);
	}

	public String updateSql() {
		String s = "";
		for (Atributo a : this) {
			if (a.isId() || a.isList()) {
				continue;
			}
			s += ", " + a.getColumnName() + " = ";
			if (a.getValue() == null) {
				s += "null";
			} else {
				s += "'" + a.getValue() + "'";
			}
		}
		s = s.substring(1);
		String where = " where " + id.getColumnName() + " = " + id.getValue();
		return "update " + ts() + " set" + s + where;
	}

	private String ts() {
		return TableSchema.get(classe);
	}

	public String insertSql() {
		String campos = "";
		String values = "";
		for (Atributo a : this) {
			if (a.getValue() == null) {
				continue;
			}
			campos += ", " + a.getColumnName();
			values += ", '" + a.getValue() + "' as " + a.getColumnName();
		}
		return "insert into " + ts() + " (" + campos.substring(2) + ") select " + values.substring(2);
	}

	public String persistSql() {
		removeNaoPersistiveis();
		if (getId().getValue() == null) {
			return insertSql();
		}
		return updateSql();
	}
	
	public static boolean considerarOneToOneCascade = false;

	private static final Predicate<Atributo> FILTER_REMOVE_ONE_TO_ONE_FAKE = o -> {
		OneToOne a = o.getAnnotation(OneToOne.class);
		if (a == null) {
			return false;
		}
		
		if (considerarOneToOneCascade) {
			for(CascadeType c : a.cascade()) {
				if (c == CascadeType.ALL) {
					return false;
				}
			}
		}
		
		if (!UObject.isEmpty(a.mappedBy()) || o.hasAnnotation(PrimaryKeyJoinColumn.class)) {
			return true;
		}
		
		return false;
	};
	
	public void removeOneToOneFake() {
		removeIf(FILTER_REMOVE_ONE_TO_ONE_FAKE);
		if (getId() != null) {
			Atributos as = new Atributos();
			for (Atributo o : this) {
				if (o.getColumnName().equalsIgnoreCase(getId().getColumnName())) {
					as.add(o);
				}
			}
			removeAll(as);
		}
	}
	
	public void removeLists() {
		Atributos as = new Atributos();
		for (Atributo o : this) {
			if (o.isList()) {
				as.add(o);
			}
		}
		removeAll(as);
		// Predicate<Atributo> filter = new Predicate<Atributo>() {
		// @Override
		// public boolean test(Atributo o) {
		//// throw Utils.exception("teste 9999");
		// return o.isList();
		// }
		// };
		//
		// removeIf(filter);
	}

	public void removeValueNull() {
		Predicate<Atributo> filter = o -> o.getValue() == null;
		removeIf(filter);
	}

	@Override
	public void print() {
		for (Atributo a : this) {
			ULog.debug(a);
		}
	}

	public Atributo removeAndGet(String nome) {
		Atributo a = get(nome);
		if (a == null) {
			return null;
		}
		remove(a);
		return a;
	}

	public boolean remove(String nome) {
		Atributo a = get(nome);
		if (a == null) {
			return false;
		}
		return remove(a);
	}

	public void remove(String... nomes) {
		for (String s : nomes) {
			Atributo a = get(s);
			if (a != null) {
				remove(a);
			}
		}
		// Predicate<Atributo> filter = new Predicate<Atributo>() {
		// @Override
		// public boolean test(Atributo o) {
		// for (String nome : nomes) {
		// if ( o.getField().getName().equalsIgnoreCase(nome) ) {
		// return true;
		// }
		// }
		// return false;
		// }
		// };
		//
		// removeIf(filter);
	}

	@Override
	public Atributos copy() {
		Atributos as = new Atributos();
		as.id = id;
		as.classe = classe;
		as.addAll(this);
		return as;
	}
	
	@Override
	public Atributos clone() {
		return copy();
	}

	public Atributo getWhereColunaBanco(String s) {
		for (Atributo a : this) {
			if (a.getColumnName().equalsIgnoreCase(s)) {
				return a;
			}
		}
		if (getId().getColumnName().equalsIgnoreCase(s)) {
			return id;
		}
		return null;
	}

	public Atributos getFks() {
		Atributos as = new Atributos();
		for (Atributo a : this) {
			if (a.isFk()) {
				as.add(a);
			}
		}
		return as;
	}

	@Override
	public Atributos sortString(F1<Atributo,String> func) {
		super.sortString(func);
		return this;
	}

	public void sortByName() {
		sortString(a -> a.nome());
	}

	public void sort() {
		Comparator<Atributo> c = (a, b) -> {
			if (a.isId()) {
				return -1;
			}
			Integer ordemA = a.getOrdem();
			Integer ordemB = b.getOrdem();
			if (IntegerCompare.eq(ordemA, ordemB)) {
				return a.nome().compareTo(b.nome());
			}
			return ordemA.compareTo(ordemB);
		};
		sort(c);
	}

	public Atributo first() {
		if (isEmpty()) {
			return null;
		}
		return get(0);
	}

	public boolean existe(String fieldName) {
		return get(fieldName) != null;
	}

	public static Atributo discover(Class<?> classe, String campo) {
		if (UType.isPrimitiva(classe)) {
			throw UException.runtime("classe is primitiva: " + classe.getName());
		}
		if (StringEmpty.is(campo)) {
			throw UException.runtime("campo == null");
		}
		Atributos atributos = AtributosBuild.get(classe);
		if (campo.contains(".")) {
			String before = StringBeforeFirst.get(campo, ".");
			String after = StringAfterFirst.get(campo, ".");
			Atributo a = atributos.get(before);
			return discover(a.getType(), after);
		}
		return atributos.get(campo);
	}

	public String getNomeExpression() {
		Atributo nome = getNomeAny();
		if (nome == getId()) {
			return "id";
		}
		String x = "";
		while (!nome.isString()) {
			x += nome.nome() + ".";
			Atributos as = AtributosBuild.get(nome.getType());
			nome = as.getNomeAny();
			if (nome == null) {
				nome = as.getId();
				break;
			}
		}
		x += nome.nome();
		return x;
	}

	public void sortByOrdem() {
		sort(AtributoComparatorOrdem.getInstance());
	}

	public Atributos getDates() {
		Atributos as = new Atributos();
		for (Atributo a : this) {
			if (a.isDate()) {
				as.add(a);
			}
		}
		return as;
	}

	@Override
	public void add(int index, Atributo atributo) {
		UAssert.notEmpty(atributo, "atributo == null");
		remove(atributo);
		super.add(index, atributo);
	}

	@Override
	public boolean add(Atributo atributo) {
		UAssert.notEmpty(atributo, "atributo == null");
		if (contains(atributo)) {
			return false;
		}
		return super.add(atributo);
	}
	public void add(Atributos atributos) {
		for (Atributo a : atributos) {
			if (!contains(a)) {
				add(a);
			}
		}
	}
	public boolean has(Class<?> tipo, String name) {
		return getWhereType(tipo).has(name);
	}
	public boolean has(String fieldName) {
		return get(fieldName) != null;
	}

	public ListString getNames() {
		ListString list = new ListString();
		for (Atributo a : this) {
			list.add(a.nome());
		}
		return list;
	}

	public void removeNullValues(Object o) {
		Atributos as = new Atributos();
		for (Atributo a : this) {
			if (a.get(o) == null) {
				as.add(a);
			}
		}
		removeAll(as);
	}

	public boolean moveToStart(String nome) {
		Atributo o = get(nome);
		if (o == null) {
			return false;
		}
		remove(o);
		add(0, o);
		return true;
	}
	
	public boolean moveToEnd(String nome) {
		Atributo o = get(nome);
		if (o == null) {
			return false;
		}
		remove(o);
		add(o);
		return true;
	}

	public String join() {
		return join(", ");
	}

	@Override
	public String join(String separador) {
		return join(separador, a -> a.nome());
	}

	public String join(String separador, F1<Atributo,String> get) {
		String s = "";
		for (Atributo a : this) {
			s += separador + get.call(a);
		}
		return s.substring(separador.length());
	}

	public boolean addId() {
		Atributo a = getId();
		if (a == null) {
			return false;
		} else {
			remove(a);
			add(0, a);
			return true;
		}
	}
	
	@Override
	public boolean anyMatch(Predicate<Atributo> predicate) {
		return stream().anyMatch(predicate);
	}
	
	private static Atributos build(Atributos lst) {
		return lst.copy();
	}
	
	public static Atributos get(Class<?> classe, boolean withId) {
		return build(AtributosBuild.get(classe, withId));
	}
	
	public static Atributos get(Class<?> classe) {
		return build(AtributosBuild.get(classe));
	}
	
	public static Atributos getStatics(Class<?> classe) {
		return build(AtributosBuild.getStatics(classe));
	}
	
	public static Atributos getNoStatics(Class<?> classe) {
		return build(AtributosBuild.getNoStatics(classe));
	}
	
	public static Atributos getPersists(Class<?> classe) {
		return build(AtributosBuild.getPersists(classe));
	}
	
	public static Atributo getId(Class<?> classe) {
		return AtributosBuild.getId(classe);
	}
	
}