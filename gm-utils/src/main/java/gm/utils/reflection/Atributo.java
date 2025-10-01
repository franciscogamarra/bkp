package gm.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.anotacoes.ArquivoAnnotation;
import gm.utils.anotacoes.Imagem;
import gm.utils.anotacoes.Lookup;
import gm.utils.anotacoes.NomeProprio;
import gm.utils.anotacoes.Obrigatorio;
import gm.utils.anotacoes.Ordem;
import gm.utils.anotacoes.SomenteLeitura;
import gm.utils.anotacoes.Status;
import gm.utils.anotacoes.Telefone;
import gm.utils.anotacoes.Titulo;
import gm.utils.anotacoes.Unique;
import gm.utils.anotacoes.UniqueJoin;
import gm.utils.classes.ClassBox;
import gm.utils.classes.ClassComGenerics;
import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.JSon;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UAssert;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UCompare;
import gm.utils.comum.UGenerics;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.email.UEmail;
import gm.utils.exception.UException;
import gm.utils.javaCreate.JcTipo;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.SqlNative;
import gm.utils.jpa.UIdObject;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.lambda.F1;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.map.MapSoFromObject;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric1;
import gm.utils.number.ULong;
import gm.utils.string.Java2021;
import gm.utils.string.ListString;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
//import javax.validation.constraints.Digits;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.cp.UCpf;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringToCamelCaseSepare;

@Getter @Setter
public class Atributo {

	public Atributo(Class<?> classe){
		this.classe = classe;
	}

	private Field field;
	private Metodo getMethod;
	private Metodo setMethod;
	private Integer scale;
	private String nomeDefault;
	private Integer lengthBanco;
	private Boolean existeNoBanco;
	private Class<?> classe;
	private Object aux;
	private Integer len;
	private List<MapSO> list;
	private Map<String, Object> props;
	public F1<Object, Object> tryCast;

	@SuppressWarnings("unchecked")
	public <T> T getProp(String key) {
		if (props == null) {
			return null;
		}
		return (T) props.get(key);
	}
	public void setProp(String key, Object value) {
		if (props == null) {
			if (value == null) {
				return;
			}
			props = new HashMap<>();
			props.put(key, value);
		} else if (value == null) {
			props.remove(key);
		} else {
			props.put(key, value);
		}
	}

	public Integer getLength(){
		return this.getLength(null);
	}

	public Integer getLength(Integer def){

		if (len != null) {
			return len;
		}

		Atributo a = getReal();

		if (a.is(String.class)) {
			Column c = a.getAnnotation(Column.class);
			if (c == null) {
				if (def == null) {
					throw new RuntimeException("@Column == null");
				}
				len = def;
			} else {
				len = c.length();
			}
		} else if (a.is(Integer.class)) {
			Max c = a.getAnnotation(Max.class);
			int max;
			if (c == null) {
				max = Integer.MAX_VALUE;
			} else {
				max = IntegerParse.toIntDef(c.value(), 0);
			}
			len = StringParse.get(max).length();
		} else if (a.is(BigDecimal.class) || a.is(Calendar.class)) {
			len = 10;//999.999,99
		} else if (a.isPrimitivo()) {
			len = 100;
		} else {
			len = AtributosBuild.get(a.getType()).getNomeAny().getLength();
		}

		return len;
	}

	private Atributo real;

	public Atributo getReal() {
		if (real == null) {
			if (isLookup()) {
				Lookup lookup = this.getAnnotation(Lookup.class);
				try {
					Atributo a = AtributosBuild.get(getClasse()).get(lookup.vinculo()).getReal();
					Atributos as = AtributosBuild.get(a.getType());
					ListString campos = ListString.split(lookup.resultado(), ".");
					for (String campo : campos) {
						a = as.getObrig(campo).getReal();
					}
					real = a;
				} catch (Exception e) {
					throw new RuntimeException("Erro ao tentar ler o atributo real de " + this, e);
				}
			} else {
				real = this;
			}
		}
		return real;
	}

	public boolean isLookup() {
		return this.hasAnnotation(Lookup.class);
	}
	public Integer getInt(Object o, int def){
		return IntegerParse.toIntDef(this.get(o), def);
	}
	public Boolean getBoolean(Object o){
		o = this.get(o);
		if (o == null) {
			return null;
		}
		return UBoolean.isTrue(o);
	}
	public Integer getInt(Object o){
		return IntegerParse.toInt(this.get(o));
	}
	public String getString(Object o){
		return StringParse.get(this.get(o));
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object o){

		if (o == null) {
			throw UException.runtime("o == null");
		}

		if (getMethod == null) {
			try {
				Object value = field.get(o);
				return (T) value;
			} catch (Throwable e) {
				throw UException.runtime(e);
			}
		}

		try {
			return getMethod.invoke(o);
		} catch (Exception e) {
			throw UException.runtime(e);
		}

	}
	
	public <T> T getObrig(Object o){
		
		T value = get(o);
		
		if (value == null) {
			throw new NullPointerException("Retornou nulo: " + nome()); 
		}
		
		return value;
		
	}
	
	public void set(Object o, Object value){

		if (o == null) {
			throw UException.runtime("o == null");
		}

//		if (UObject.isEmpty(value)) {
//			value = null;
//		}

		if (value != null && tryCast != null) {
			value = tryCast.call(value);
		}

		if (value != null) {

			Object x = UType.tryCast(value, getType());
			if (x != null) {

				value = x;

				if ( getType().equals(BigDecimal.class) ) {
					BigDecimal v = (BigDecimal) value;
					Digits digits = this.getAnnotation(Digits.class);

					if (digits != null) {
						int fraction = digits.fraction();
						if (fraction == 1) {
							value = new Numeric1(v).getValor();
						}
					}

				}
			} else if (value instanceof Map) {
				MapSO map = MapSoFromObject.get(value);
				value = UClass.newInstance(getType());
				map.setInto(value, false);
			} else if ( UClass.isAbstract( getType() ) ) {
				value = null;
			} else if (value instanceof String && JSon.isJson((String) value)) {
				MapSO map = MapSoFromJson.get((String) value);
				value = UClass.newInstance(getType());
				map.setInto(value, false);
			} else if ( isTransient() || UConfig.get() == null || !UConfig.get().onLine() ) {
				Object novo = UClass.newInstance(getType());
				Atributo id = AtributosBuild.getId(getType());
				if (id == null) {
					throw UException.runtime("id == null " + getType().getSimpleName());
				}
				id.set(novo, value);
				value = novo;
			} else if ( !isTransient() ) {
				value = Criterio.resolveValue(this, value);
			}

			if (value != null) {
				if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
					value = IntegerParse.toInt(value);
				} else if (field.getType().equals(Long.class)) {
					value = ULong.toLong(value);
				}
			}

		} else if (isPrimitivo() && (getType().equals(int.class) || getType().equals(long.class))) {
			value = 0;
		}

		try {

			if (setMethod == null) {
				field.set(o, value);
			} else {
				setMethod.invoke(o, value);
			}

		} catch (Throwable e) {

			e.printStackTrace();

			String s = "";
			if (value != null) {
				s = value.getClass().getSimpleName();
			}
			s = this + " <set> " + s + " " + value + "\n";
			ULog.debug(s);
			throw UException.runtime(e);
		}
	}

	private String toStringResolved;

	@Override
	public String toString() {
		if (toStringResolved != null) {
			return toStringResolved;
		}
		toStringResolved = getType().getSimpleName() + " ";
		toStringResolved += classe.getSimpleName() + ".";
		toStringResolved += field.getName() + " >> ";
		toStringResolved += getColumnName();
		return toStringResolved;
	}

	public boolean isId(){

		if ( isTransient() || isStatic() ) {
			return false;
		}
		if ( this.hasAnnotation(Id.class) ) {
			return true;
		}

		Field field = getField();
		
		if (field != null) {
			String fn = field.getName();
			if ("id".contentEquals(fn) || ("id" + field.getClass().getSimpleName()).equals(fn)) {
				return true;
			}
		}


		return false;
//		return haveAnnotation(Id.class) || haveAnnotation(PrimaryKeyJoinColumn.class);
	}

	private Boolean isNome;

	public boolean isNome(){

		if (isNome != null) {
			return isNome;
		}

		if ( isStatic() ) {
			isNome = false;
			return false;
		}
		if ( this.hasAnnotation(NomeProprio.class) ) {
			isNome = true;
			return true;
		}

		Field field = getField();

		if (field != null) {
			String name = field.getName().toLowerCase();
			if (name.startsWith("ds")) {
				name = name.substring(2);
			}
			if ( "nome".equalsIgnoreCase(name) || ("nome" + field.getClass().getSimpleName()).equalsIgnoreCase(name) || "text".equalsIgnoreCase(name) ) {
				isNome = true;
				return true;
			}
		}

		isNome = false;
		return false;

	}

	public String getColumnName(){

		String s = null;

		Column column = getColumn();
		if (column != null && !StringEmpty.is( column.name() )) {
			s = column.name();
		} else {
			JoinColumn join = this.getAnnotation(JoinColumn.class);
			if (join != null && !StringEmpty.is( join.name() ) ) {
				s = join.name();
			} else {
				PrimaryKeyJoinColumn keyJoin = this.getAnnotation(PrimaryKeyJoinColumn.class);
				if ((keyJoin == null) || StringEmpty.is( keyJoin.referencedColumnName() )) {
					return field.getName();
				}
				s = keyJoin.referencedColumnName();
			}

		}

		if (s.equalsIgnoreCase( field.getName() )) {
			return field.getName();
		}
		return s;

	}
	public Column getColumn(){
		return this.getAnnotation(Column.class);
	}
	public boolean hasAnnotation(String nome) {
//		if (nome.equals("Setter")) {
//			return lombokSetter();
//		} else if (nome.equals("Getter")) {
//			return lombokGetter();
//		} else {
		return this.getAnnotation(nome) != null;
//		}
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> classe) {
		return getAnnotation(classe) != null;
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotation) {
		return hasAnnotation(annotation, true);
	}
	public boolean hasAnnotation(Class<? extends Annotation> annotation, boolean buscarNaClasse) {
//		if (annotation == Setter.class) {
//			return this.lombokSetter();
//		} else if (annotation == Getter.class) {
//			return this.lombokGetter();
//		} else {
		return this.getAnnotation(annotation, buscarNaClasse) != null;
//		}
	}
	public boolean hasAnnotation(Class<? extends Annotation> a, Class<? extends Annotation> b) {
		return this.hasAnnotation(a) || this.hasAnnotation(b);
	}
	public boolean hasAnnotation(Class<? extends Annotation> a, Class<? extends Annotation> b, Class<? extends Annotation> c) {
		return this.hasAnnotation(a, b) || this.hasAnnotation(c);
	}
	public boolean hasAnnotation(Class<? extends Annotation> a, Class<? extends Annotation> b, Class<? extends Annotation> c, Class<? extends Annotation> d) {
		return this.hasAnnotation(a, b, c) || this.hasAnnotation(d);
	}

//	nao usar pois dah warning
//	@SuppressWarnings("unchecked")
//	public <T extends Annotation> boolean hasAnyAnnotation(Class<T>... annotations) {
//		for (Class<T> annotation : annotations) {
//			if (getAnnotation(annotation) != null) {
//				return true;
//			}
//		}
//		return false;
//	}
	public <T extends Annotation> T getAnnotationObrig(Class<T> annotation) {
		T o = this.getAnnotation(annotation);
		if (o == null) {
			throw UException.runtime("Não foi encontrata a annotation '" + annotation + "' no atributo '" + this + "'");
		}
		return o;
	}
	public <T extends Annotation> T getAnnotation(Class<T> annotation) {
		return this.getAnnotation(annotation, false);
	}

	public F1<String, Annotation> getAnnotation;
	public F1<Class<? extends Annotation>, Annotation> getAnnotationByClass;

	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getAnnotation(String nome) {
		List<Annotation> annotations = getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.getClass().getSimpleName().equalsIgnoreCase(nome) || annotation.annotationType().getSimpleName().equals(nome)) {
				return (T) annotation;
			}
		}
		if (getAnnotation != null) {
			return (T) getAnnotation.call(nome);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getAnnotation(Class<T> annotation, boolean buscarNaClasse) {

		if (field == null) {
			return null;
		}
		T a = field.getAnnotation(annotation);
		if (a != null) {
			return a;
		}
		if ( getMethod != null ) {
			a = getMethod.getAnnotation(annotation);
			if (a != null) {
				return a;
			}
		}
		if ( setMethod != null ) {
			a = setMethod.getAnnotation(annotation);
			if (a != null) {
				return a;
			}
		}

		if (getAnnotationByClass != null) {
			Annotation an = getAnnotationByClass.call(annotation);
			if (an != null) {
				return (T) an;
			}
		}

		if (buscarNaClasse) {
			a = getClasse().getAnnotation(annotation);
			if (a != null) {
				return a;
			}
		}

		return getProp("@" + annotation.getSimpleName());

	}

	public String uniqueJoin() {

		if (this.hasAnnotation(UniqueJoin.class)) {
			this.getAnnotation(UniqueJoin.class).value();
		}

		Annotation annotation = this.getAnnotation("UniqueJoin");

		if (annotation != null) {
			Metodo metodo = ListMetodos.get(annotation.getClass()).get("value");
			return metodo.invoke(annotation);
		}
		return StringParse.get(getProp("UniqueJoin"));

	}

	public boolean isUnique() {
		return this.hasAnnotation(Unique.class) || this.hasAnnotation("Unique") || UBoolean.isTrue(getProp("Unique"));
	}

	public Lst<Annotation> getAnnotations() {
		if (field == null) {
			return null;
		}
		Annotation[] annotations = field.getAnnotations();
		return new Lst<>(Arrays.asList(annotations));
	}

	public boolean isTransient() {
		return this.hasAnnotation(Transient.class);
	}
	public boolean isTransientModifier(){
		if (field == null) {
			return false;
		}
		return java.lang.reflect.Modifier.isTransient(field.getModifiers());
	}
	public boolean isStatic(){
		if (field == null) {
			return false;
		}
		return java.lang.reflect.Modifier.isStatic(field.getModifiers());
	}
	public boolean isFinal(){
		if (field == null) {
			return false;
		}
		return java.lang.reflect.Modifier.isFinal(field.getModifiers());
	}
	public boolean isPrivate(){
		if (field == null) {
			return false;
		}
		return java.lang.reflect.Modifier.isPrivate(field.getModifiers());
	}
	public boolean isProtected(){
		if (field == null) {
			return false;
		}
		return java.lang.reflect.Modifier.isProtected(field.getModifiers());
	}
	public boolean isPublic(){
		if (field == null) {
			return false;
		}
		return java.lang.reflect.Modifier.isPublic(field.getModifiers());
	}
	public boolean isDefault(){
		if (field == null) {
			return false;
		}
		return !isPrivate() && !isPublic() && !isProtected();
	}
	public final String getModificadorDeAcesso() {
		if (isPublic()) {
			return "public";
		}
		if (isPrivate()) {
			return "private";
		}
		if (isProtected()) {
			return "protected";
		}
		return "";
	}
	public boolean isBoolean(){
		return is(boolean.class) || is(Boolean.class);
	}
	public boolean isShort(){
		return is(short.class) || is(Short.class);
	}
	public boolean isInteger(){
		return is(int.class) || is(Integer.class);
	}
	public boolean eq(String name){
		return getNome().equalsIgnoreCase(name);
	}

	public boolean isAtivo(){
		return this.eq("ativo") || this.eq("isAtivo") || this.eq("is_ativo") || this.eq("ativa") || this.eq("isAtiva") || this.eq("is_ativa") || this.eq("idAtivo");
	}
	
	private Boolean primitivo;
	
	public boolean isPrimitivo() {
		if (primitivo == null) {
			primitivo = UType.isPrimitiva( getType() );
		}
		return primitivo;
	}
	
	private Boolean primitivoReal;
	public boolean isPrimitivoReal() {
		if (primitivoReal == null) {
			primitivoReal = UType.isPrimitivaReal( getType() );
		}
		return primitivoReal;
	}
	
	public boolean isEnum() {
		return getType().isEnum();
	}
	public boolean isWrapper() {
		if (!isPrimitivo()) {
			return false;
		}
		Class<?> type = getType();
		if (type.equals(boolean.class) || type.equals(int.class) || type.equals(double.class) || type.equals(float.class)) {
			return false;
		}
		if (type.equals(short.class)) {
			return false;
		}
		return true;
	}
	public boolean isLong() {
		return getType().equals(Long.class);
	}
	public boolean isString() {
		return getType().equals(String.class);
	}
	public void setAleatorio(Object o) {

		if (isDate()) {
			Data data = Aleatorio.getData();
			data.setAno(2016);
			if ( getType().equals(Calendar.class) ) {
				set(o, data.getCalendar() );
			} else if ( getType().equals(Date.class) ) {
				set(o, data.toDate() );
			}
			return;
		}

		Object value = null;
		String n = getColumnName();

		Integer length = this.getLength();

		if (isString()) {

			String s;

			if (length == null || length > 100) {
				length = 100;
			}

			if ( (n.startsWith("cpj") || n.contains("cnpj")) || (n.startsWith("nu_") || n.contains("inscricao")) ) {
				s = Aleatorio.getIntString(length);
			} else if ( n.startsWith("tx_") ) {
				s = Aleatorio.getTexto(length);
			} else if ( n.contains("email") ) {
				s = UEmail.aleatorio();
			} else if ( n.contains("url") ) {
				s = Aleatorio.getUrl();
			} else if ( n.contains("cpf") ) {
				s = UCpf.aleatorio();
			} else if ( nomeDefault != null ) {
				s = nomeDefault;
			} else {
				s = getDeclaringClass().getSimpleName() + " " + Aleatorio.getString(10);
			}

			while (s.length() > length) {
				s = s.substring(1);
			}

			value = s;

		} else if ( getType().equals(Integer.class) ) {

			if ( n.startsWith("qt_") || n.contains("porcent") ) {
				value = Aleatorio.get(0, 100);
			} else {
				value = Aleatorio.get(0, 100000);
			}

		} else
//			if ( type().equals(Integer.class) ) {
//
//			if ( n.startsWith("qt_") || n.contains("porcent") ) {
//				value = Aleatorio.get(0, 100);
//			} else {
//				value = Aleatorio.get(0, 1000);
//			}
//
//		} else
		if ( getType().equals(Double.class) ) {

			if (length == null) {
				value = Aleatorio.getBigDecimal().doubleValue();
			} else {
				value = Aleatorio.getBigDecimal(length, scale).doubleValue();
			}

		} else {
			value = Aleatorio.get(field.getType());
		}

		set(o, value);

	}
	
	public Class<?> getDeclaringClass() {
		return field.getDeclaringClass();
	}

	public boolean isDate() {
		return UType.isData(getType());
	}
	public Table table() {
		return field.getType().getAnnotation(Table.class);
	}
	public String toSql(Object o) {

		Object value = this.get(o);

		if (UObject.isEmpty(value)) {
			return "null";
		}
		if (isString()) {
			return "'" + value.toString() + "'";
		}
		if (isPrimitivo()) {
			return value.toString();
		}
		if (isDate()) {
			return Data.to(value).format_sql(true);
		}

		value = AtributosBuild.get(value.getClass()).getId().get(value);

		UAssert.notEmpty(value, "value == null");

		return value.toString();

	}
	public boolean typeIn(ListClass types){
		for (Class<?> type : types) {
			if (is(type)) {
				return true;
			}
		}
		return false;
	}
	public boolean typeIn(Class<?>... types){
		for (Class<?> type : types) {
			if (is(type)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean iss(Class<?> type) {
		return getType().equals(type) || UClass.a_herda_b(getType(), type);
	}
	
	public boolean is(Class<?> type, Class<?>... types) {
		
		if (iss(type)) {
			return true;
		}
		
		for (Class<?> classe : types) {
			if (iss(classe)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Setter//pode ser sobrescrito em alguns mehtodos
	private Class<?> type;

	public Class<?> getType() {
		if (type == null) {
			type = field.getType();
		}
		return type;
	}

	public JcTipo getTypeJc() {

		if (field == null) {
			return JcTipo.build(getType());
		}

		return JcTipo.descobre(field.getAnnotatedType().getType().getTypeName());
	}

	public static void main(String[] args) {
		ListString list = ListString.separaPalavras("java.lang.String, java.util.Map<java.lang.Integer, T>");
		Lst<Object> os = new Lst<>();
		os.addAll(list);
	}

	private String typeDeclaration;

	public String getTypeDeclaration() {

		if (typeDeclaration != null) {
			return typeDeclaration;
		}

		AnnotatedType x = field.getAnnotatedType();
//		x.getType().getTypeName()
		JcTipo tipo = JcTipo.descobre(x.getType().getTypeName());

//		String s = UGenerics.getClassComGenerics(field).toString();

//		Removido em 2019-05-18 - ainda nao sei se hah impacto negativo
//		String s = getType().getSimpleName();
//		ListClass classes = UGenerics.getGenericClasses(field);
//		if (classes != null) {
//			String x = "";
//			for (Class<?> classe : classes) {
//				x += ", " + classe.getSimpleName();
//			}
//			x = x.substring(2);
//			s += "<" + x + ">";
//		}

		typeDeclaration = tipo.toString();
		return typeDeclaration;

	}

	public boolean isListOf(Class<?> type) {
		if (!isList()) {
			return false;
		}
		return getTypeOfList().equals(type);

	}
	public boolean isList() {
		return UType.isList(getType());
	}
	public boolean isMap() {
		return UType.isMap(getType());
	}

	private Class<?> typeOfList;

	public Class<?> getTypeOfList() {

		if (typeOfList == null) {

			try {

				Java2021 java = new Java2021(UClass.getJavaFileName(getClasse()));
				ListString lst = java.getList();
				lst.trimPlus();
				lst.removeIfNotEndsWith(";");
				lst.removeIfNotContains("> " + nome());
				lst.removeIf(s -> (!s.contains("> " + nome() + ";")) && (!s.contains("> " + nome() + " =")));

				if (lst.size() == 1) {
					String s = lst.remove(0);
					s = StringAfterFirst.get(s, "t<");
					s = StringBeforeFirst.get(s, ">");

					String ss = s;

					typeOfList = UType.PRIMITIVAS_JAVA.unique(i -> i.getSimpleName().contentEquals(ss));

					if (typeOfList != null) {
						return typeOfList;
					}

					String x = java.getImports().unique(i -> i.endsWith("." + ss));

					if (x != null) {

						typeOfList = UClass.getClass(x);

						if (typeOfList != null) {
							return typeOfList;
						}

					}

					ListClass classes = UClass.getClassesDaPackage(getClasse().getPackage());

					typeOfList = classes.unique(i -> i.getSimpleName().contentEquals(ss));

					if (typeOfList != null) {
						return typeOfList;
					}

				}

			} catch (Exception e) {
				// ignore
			}


			typeOfList = UGenerics.getGenericClass(field);

			if (UType.isList(typeOfList)) {
				SystemPrint.ln(nome());
			}


		}
		return typeOfList;
	}

	private ListClass typesOfMap;
	@SuppressWarnings("deprecation")
	public ListClass getTypesOfMap() {
		if (typesOfMap == null) {
			typesOfMap = new ListClass();

			Class<?> a = UGenerics.getGenericClass(field,0);

			if (a == null) {
				ClassComGenerics classComGenerics = UGenerics.getClassComGenerics(field);
				typesOfMap.add(classComGenerics.getGenerics().get(0).getClasse());
				typesOfMap.add(classComGenerics.getGenerics().get(1).getClasse());
			} else {
				typesOfMap.add(a);
				typesOfMap.add(UGenerics.getGenericClass(field,1));
			}

		}
		return typesOfMap;
	}

	public Field getField() {
		return field;
	}

	private Object value;
	public Boolean aceitaNulos;

	@SuppressWarnings("deprecation")
	public void setValue(Object o) {

		value = this.get(o);

		if (value == null) {
			return;
		}

		if (value instanceof Date) {

			java.util.Date d = (java.util.Date) value;

			Data data;

			Temporal a = this.getAnnotation( Temporal.class );
			if (a != null && TemporalType.TIME.equals(a.value())) {
				data = new Data(1900, 1, 1, d.getHours(), d.getMinutes(), d.getSeconds());
			} else {
				data = new Data(d);
			}

			value = data.format_sql(false);
			return;
		}
		if (value instanceof Calendar) {
			Calendar d = (Calendar) value;
			Data data = new Data(d);
			value = data.format_sql(false);
			return;
		}

		if (value instanceof java.sql.Date) {
			java.sql.Date d = (java.sql.Date) value;
			Data data = new Data(d);
			value = data.format_sql(false);
			return;
		}

		if (!UType.isPrimitiva(value)) {
			value = UIdObject.getId(value);
		}

	}
	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}
	public boolean isFk() {
		if (isPrimitivo() || isList() || isWrapper() || isNumeric()) {
			return false;
		}
		return true;
	}

	private String nomeComPrimeiraMaiuscula;
	public String upperNome() {
		if (nomeComPrimeiraMaiuscula == null) {
			nomeComPrimeiraMaiuscula = StringPrimeiraMaiuscula.exec(nome());
		}
		return nomeComPrimeiraMaiuscula;
	}

	@Setter
	private String nome;

	public String getName() {
		if (nome == null) {
			nome = getField().getName();
		}
		return nome;
	}
	
	public String getNome() {
		return getName();
	}

	public String nome() {
		return getNome();
	}

	private List<ConexaoJdbc> bancosEmQueExiste = new ArrayList<>();

	public boolean existeNoBanco(ConexaoJdbc con) {
		if ( bancosEmQueExiste.contains(con) ) {
			return true;
		}
		if ( con.existsColumn(getClasse(), getColumnName()) ) {
			bancosEmQueExiste.add(con);
			return true;
		}
		return false;
	}
	public void setExisteNoBanco() {
		this.setExisteNoBanco(UConfig.con());
	}
	public void setExisteNoBanco(ConexaoJdbc con) {
		bancosEmQueExiste.add(con);
	}

	public boolean existeNoBanco() {
		return this.existeNoBanco(UConfig.con());
	}
	public Class<?> getClasse() {
		return classe;
	}
	public boolean temporalDate() {
		if (!isDate()) {
			return false;
		}
		Temporal temporal = this.getAnnotation(Temporal.class);
		if (temporal == null) {
			throw UException.runtime("O atributo " + getClasse().getSimpleName() + "." + this + " estah sem a anotacao @Temporal");
		}
		return TemporalType.DATE.equals(temporal.value());
	}
	public boolean temporalDateTime() {
		if (!isDate()) {
			return false;
		}
		Temporal temporal = this.getAnnotation(Temporal.class);
		if (temporal == null) {
			throw UException.runtime("O atributo " + getClasse().getSimpleName() + "." + this + " estah sem a anotacao @Temporal");
		}
		return TemporalType.TIMESTAMP.equals(temporal.value());
	}

	private Boolean calculado;
	public boolean isCalculado() {
		if (calculado == null) {
			calculado = this.hasAnnotation(SomenteLeitura.class) || isId();
		}
		return calculado;
	}

	public int getOrdem() {
		Ordem ordem = this.getAnnotation(Ordem.class);
		if (ordem == null) {
			return 999;
		}
		return ordem.value();
	}

	private static boolean isThis(Atributo a) {
		return a instanceof Atributo;
	}
	private static Atributo toThis(Atributo a) {
		return a;
	}

	public boolean eq(Atributo a) {
		if (super.equals(a) || (Atributo.isThis(a) && Atributo.toThis(a).getField().equals(getField()))) {
			return true;
		}
		return false;
	}

	public boolean isNumeric() {
		return is(BigDecimal.class) || is(Double.class) || is(Numeric.class);
	}

	public boolean isNumeric(int size) {
		return isNumeric() && digitsFraction() == size;
	}
	
	public boolean isNumeric1() {
		return isNumeric(1);
	}
	public boolean isNumeric2() {
		return isNumeric(2);
	}
	public boolean isNumeric3() {
		return isNumeric(3);
	}
	public boolean isNumeric4() {
		return isNumeric(4);
	}
	public boolean isNumeric5() {
		return isNumeric(5);
	}
	public boolean isNumeric8() {
		return isNumeric(8);
	}
	public boolean isNumeric15() {
		return isNumeric(15);
	}
	public boolean isNumeric18() {
		return isNumeric(18);
	}

	public int digitsFraction() {

		if (is(Numeric.class)) {
			return IntegerParse.toInt(StringExtraiNumeros.exec(getType().getName()));
		}
		
		Digits digits = getAnnotation(Digits.class);

		if (digits != null) {
			return digits.fraction();
		}

		Column column = getAnnotation(Column.class);

		if (column != null && column.precision() > 0) {
			return column.precision();
		}

		if (is(Double.class) || is(BigDecimal.class)) {
			return 8;
		}

		throw new RuntimeException("Falta a annotation @Digits: " + this);

	}

	public int digitsIntegers() {

		if (is(Numeric.class)) {
			return 8;
		}

		Digits digits = getAnnotation(Digits.class);

		if (digits != null) {
			return digits.integer();
		}

		Column column = getAnnotation(Column.class);

		if (column != null && column.scale() > 0) {
			return column.scale();
		}

		if (is(Double.class) || is(BigDecimal.class)) {
			return 8;
		}

		throw new RuntimeException("Falta a annotation @Digits");

	}

	public String nomeElegante() {

		if (isId()) {
			return "id";
		}

		String s = nome();

		if ( s.startsWith("ds") || s.startsWith("is") || s.startsWith("tx") ) {
			String x = s.substring(2);
			String y = x.substring(0,1);
			if (!y.equals(y.toLowerCase())) {
				return StringPrimeiraMinuscula.exec(x);
			}
		}

		return s;

	}

	public boolean equals(Object a, Object b) {
		return UCompare.compare(a, b, this) == 0;
	}
	public boolean ne(Object o, Object old) {
		return !this.equals(o, old);
	}

	private String sqlCount;

	public int getCount(Integer id) {
		if (sqlCount == null) {
			sqlCount = "select count(*) from " + getClasse().getSimpleName() + " where " + nome() + " = ";
		}
		return SqlNative.getInt(sqlCount + id);
	}

	private Boolean obrigatorio;

	public boolean isObrigatorio() {

		if (obrigatorio == null) {
			if (hasAnnotation("NotNull") || this.hasAnnotation(Obrigatorio.class)) {
				obrigatorio = true;
			} else if (this.hasAnnotation(Column.class)) {
				Column column = this.getAnnotation(Column.class);
				obrigatorio = !column.nullable();
			} else if (this.hasAnnotation(JoinColumn.class)) {
				JoinColumn column = this.getAnnotation(JoinColumn.class);
				obrigatorio = !column.nullable();
			} else {
				obrigatorio = false;
			}
		}

		return obrigatorio;

	}

	public long getMaxLong() {
		Max max = this.getAnnotation(Max.class);
		if (max == null) {
			return Long.MAX_VALUE;
		}
		return max.value();
	}

	public int getMaxInt() {
		Max max = this.getAnnotation(Max.class);
		if (max == null) {
			return 99999;
		}
		return IntegerParse.toIntDef(max.value(), 99999);
	}
	public int getMinInt() {
		Min min = this.getAnnotation(Min.class);
		if (min == null) {
			return 0;
		}
		return IntegerParse.toIntDef(min.value(), 0);
	}

	public boolean isPersistent() {
		return false;
	}

	public boolean isArray() {
		return getType().getSimpleName().endsWith("[]");
	}

	public Atributos getAtributos() {
		return AtributosBuild.get(getType());
	}
	public Atributos getAtributosPersistentes() {
		return AtributosBuild.getPersists(getType(), false);
	}

	private String nomeValue;
	private Atributos nomeValueCadeia;

	public String getNomeValue() {
		if (nomeValue != null) {
			return nomeValue;
		}
		if (isLookup()) {
			Lookup lookup = this.getAnnotation(Lookup.class);
			nomeValue = lookup.vinculo() + "." + getReal().getNomeValue();
		} else {
			nomeValue = nome();
		}
		return nomeValue;
	}
	public Atributos getNomeValueCadeia() {
		if (nomeValueCadeia == null) {
			getNomeValue();
		}
		return nomeValueCadeia.copy();
	}
	public boolean isStatus() {
		return getType().getAnnotation(Status.class) != null;
	}
	public boolean isEmail() {
		return this.hasAnnotation("Email") || (isString() && nome().startsWith("email"));
	}
	public boolean isTelefone() {
		return this.hasAnnotation(Telefone.class) || (isString() && nome().startsWith("telefone"));
	}
	public boolean isImagem() {
		return this.hasAnnotation(Imagem.class);
	}
	public boolean isArquivo() {
		return this.hasAnnotation(ArquivoAnnotation.class) || isImagem();
	}

	private String stringGet;
	public String stringGet() {

		if (stringGet == null) {

			Metodo m = getGetMethod();
			
			if (m == null) {
				if (is(boolean.class)) {
					stringGet = "is" + upperNome() + "()";
				}
			} else {
				stringGet = m.getName() + "()";
			}


		}

		return stringGet;
	}

	public String stringSet(String value) {
		return "set" + upperNome() + "("+value+");";
	}

	private String titulo;

	public String getTitulo() {
		if (titulo == null) {
			Titulo o = this.getAnnotation(Titulo.class, false);
			if (o == null) {
				String s = nome();
				s = StringToCamelCaseSepare.exec(s);
				s = s.replace("Percentual", "%");
				s = s.replace("Porcentagem", "%");
				titulo = s;
			} else {
				titulo = o.value();
			}
		}
		return titulo;
	}
	public String getDeclaracao() {
		StringBuilder s = new StringBuilder().append(getModificadorDeAcesso()).append(" ");
		if (isStatic()) {
			s.append("static ");
		}
		if (isFinal()) {
			s.append("final ");
		}
		s.append(getTypeDeclaration()).append(" ");
		s.append(nome());
		return s.toString();
	}

	private Boolean lombokSetter;

	public boolean lombokSetter() {
		if (lombokSetter == null) {

			ClassBox box = ClassBox.get(getClasse());

			if (setMethod == null) {
				setMethod = box.getMetodos().get("set" + upperNome());
				if (setMethod == null) {
					lombokSetter = false;
					return false;
				}
			}

			if (!box.getImports().contains(Setter.class)) {
				lombokSetter = false;
				return false;
			}

			String s = box.getTripa();
			s = s.replace(";", " ;");

			String busca = "@Setter " + getModificadorDeAcesso() + " " + getType().getSimpleName() + " " + nome() + " ";
			lombokSetter = StringContains.is(s, busca);

		}
		return lombokSetter;
	}

	private Boolean lombokGetter;
	public F1<MapSO,?> fromMap;

	public boolean lombokGetter() {
		if (lombokGetter == null) {

			ClassBox box = ClassBox.get(getClasse());

			Metodo gm = getGetMethod();

			if ((gm == null) || !box.getImports().contains(Getter.class)) {
				lombokGetter = false;
				return false;
			}

			String s = box.getTripa();
			s = s.replace(";", " ;");

			String busca = "@Getter " + getModificadorDeAcesso() + " " + getType().getSimpleName() + " " + nome() + " ";
			lombokGetter = StringContains.is(s, busca);

		}
		return lombokGetter;
	}

	public boolean isFunction() {
		return UType.isFunction(getType());
	}

	private boolean getMethodVerificado;

	public Metodo getGetMethod() {

		if (!getMethodVerificado) {

			getMethodVerificado = true;

			if (getMethod == null) {

				ClassBox box = ClassBox.get(getClasse());

				getMethod = box.getMetodos().get("get" + upperNome());

				if (getMethod == null && isBoolean()) {
					getMethod = box.getMetodos().get("is" + upperNome());
				}

			}

		}

		return getMethod;

	}

	private Object futureValue;

	public void setFutureValue(Object o) {

		futureValue = o;

		if (o != null) {

			if (isPrimitivo()) {

				Object value = UType.tryCast(o, getType());
				if (value == null) {
					UType.tryCast(o, getType());
					throw new RuntimeException();
				}

				futureValue = value;
				return;

			}

			if (getType().isAnnotationPresent(Table.class) && (UType.isPrimitiva(o))) {
				return;
			}

			throw new RuntimeException();

		}

	}

	public boolean isCdi() {
		return hasAnnotation("EJB") || hasAnnotation("Autowired");
	}
	
	public boolean isNotNull() {
		
		if (getAnnotation(NotNull.class) != null) {
			return true;
		}
		
		Column column = getAnnotation(Column.class);
		
		if (column != null && !column.nullable()) {
			return true;
		}
		
		JoinColumn joinColumn = getAnnotation(JoinColumn.class);
		
		if (joinColumn != null && !joinColumn.nullable()) {
			return true;
		}
		
		ManyToOne manyToOne = getAnnotation(ManyToOne.class);
		
		if (manyToOne != null && !manyToOne.optional()) {
			return true;
		}
		
		if (getAnnotation(Id.class) != null) {
			return true;
		}
		
		return false;
		
	}

}
