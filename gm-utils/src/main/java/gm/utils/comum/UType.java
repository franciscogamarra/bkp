package gm.utils.comum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import jakarta.persistence.Table;

import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.date.MyCalendar;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import gm.utils.lambda.F0;
import gm.utils.lambda.F2;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.number.Numeric3;
import gm.utils.number.Numeric4;
import gm.utils.number.Numeric5;
import gm.utils.number.Numeric6;
import gm.utils.number.Numeric8;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UBigInteger;
import gm.utils.number.UDouble;
import gm.utils.number.ULong;
import gm.utils.number.UShort;
import gm.utils.reflection.ListMetodos;
import gm.utils.string.ListString;
import js.array.Array;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringParse;

public class UType {

	public static Object[] asAutoParameters(Class<?>[] parameterTypes) {

		Object[] args = new Object[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> c = parameterTypes[i];
			if ( UType.isPrimitivaBox(c) || !UType.isPrimitiva(c) || c == String.class ) {
				args[i] = null;
			} else if (c == int.class) {
				args[i] = 0;
			} else if (c == double.class) {
				args[i] = 0.0;
			} else if (c == boolean.class) {
				args[i] = false;
			} else if (c == long.class) {
				args[i] = 0L;
			} else if (c == char.class) {
				args[i] = ' ';
			} else if (c == float.class) {
				args[i] = 0;
			} else if (c.isEnum()) {
				Object[] values = ListMetodos.get(c).get("values").invoke(null);
				args[i] = values[0];
			} else {
				throw UException.runtime("nao tratado: " + c);
			}
		}

		return args;

	}

	public static final ListClass PRIMITIVAS_JAVA_REAL = new ListClass();
	static {
		PRIMITIVAS_JAVA_REAL.add(boolean.class);
		PRIMITIVAS_JAVA_REAL.add(byte.class);
		PRIMITIVAS_JAVA_REAL.add(char.class);
		PRIMITIVAS_JAVA_REAL.add(double.class);
		PRIMITIVAS_JAVA_REAL.add(float.class);
		PRIMITIVAS_JAVA_REAL.add(int.class);
		PRIMITIVAS_JAVA_REAL.add(short.class);
		PRIMITIVAS_JAVA_REAL.add(long.class);
		PRIMITIVAS_JAVA_REAL.add(void.class);
	}

	public static final ListClass PRIMITIVAS_JAVA = PRIMITIVAS_JAVA_REAL.copy();
	static {
		PRIMITIVAS_JAVA.add(Boolean.class);
		PRIMITIVAS_JAVA.add(BigDecimal.class);
		PRIMITIVAS_JAVA.add(Byte.class);
		PRIMITIVAS_JAVA.add(Character.class);
		PRIMITIVAS_JAVA.add(Double.class);
		PRIMITIVAS_JAVA.add(Float.class);
		PRIMITIVAS_JAVA.add(Integer.class);
		PRIMITIVAS_JAVA.add(BigInteger.class);
		PRIMITIVAS_JAVA.add(Long.class);
		PRIMITIVAS_JAVA.add(Short.class);
		PRIMITIVAS_JAVA.add(Void.class);
		PRIMITIVAS_JAVA.add(String.class);
	}

	public static final ListClass CLASSES_DATA = new ListClass();
	static {
		CLASSES_DATA.add(java.util.Calendar.class);
		CLASSES_DATA.add(java.util.GregorianCalendar.class);
		CLASSES_DATA.add(MyCalendar.class);
		CLASSES_DATA.add(java.util.Date.class);
		CLASSES_DATA.add(java.sql.Date.class);
		CLASSES_DATA.add(java.sql.Timestamp.class);
		CLASSES_DATA.add(Data.class);
		CLASSES_DATA.add(java.time.LocalDate.class);
		CLASSES_DATA.add(js.outros.Date.class);
	}
	
	public static boolean isLang(Class<?> classe) {
		return isPrimitivaReal(classe) || classe.getName().contentEquals("java.lang." + classe.getSimpleName());
	}

	public static boolean isArray(Object o) {
		if (o == null) {
			return false;
		}
		Class<?> classe = UClass.getClass(o);
		return classe.isArray();
	}

	public static boolean isPrimitiva(Class<?> c) {
		
		// if (c.getName().startsWith("java.lang")) return true;
		// int.class
		// if (in(c.getSimpleName(), "int", "double", "boolean", "long")) return
		// true;
		if (PRIMITIVAS_JAVA.contains(c) || UType.isData(c) || c.isArray() || c.isEnum()) {
			return true;
		}

		return false;
	}
	
	public static boolean isPrimitivaReal(Class<?> c) {
		return PRIMITIVAS_JAVA_REAL.contains(c);
	}
	
	public static boolean isPrimitiva(Object o) {
		if (o == null) {
			return false;
		}
		Class<?> c = o.getClass();
		return UType.isPrimitiva(c);
	}
	public static boolean isPrimitivaBox(Class<?> classe) {
		return UClass.instanceOf(classe, Numeric.class, Data.class);
	}
	public static boolean isMap(Object o) {
		return UType.isMap(UClass.getClass(o));
	}
	public static boolean isMap(Class<?> classe) {
		if (UClass.a_herda_b(classe, Map.class) || classe.getName().contains("LinkedTreeMap")) {
			return true;
		}
		return false;
	}

	private static final ListClass TIPOS_LIST = new ListClass(List.class, Set.class, Collection.class, Lst.class);

	public static boolean isList(Class<?> classe) {

		String name = classe.getName();

		for (Class<?> tipo : TIPOS_LIST) {

			if (UClass.a_herda_b(classe, tipo) || name.contentEquals(tipo.getName())) {
				return true;
			}

		}

		return false;

	}

	public static boolean isList(Object o) {
		if (o == null) {
			return false;
		}
		if ((o instanceof List) || (o instanceof Set)) {
			return true;
		}
		return UType.isList(o.getClass());
	}

	public static boolean isData(Object o) {
		return UType.isData(o.getClass());
	}

	public static boolean isData(Class<?> c) {
		return CLASSES_DATA.contains(c);
	}

	public static F2<Object, Class<?>, Object> tryCast;

	public static void main(String[] args) {
		SystemPrint.ln(tryCast("S", boolean.class));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T tryCast(Object o, Class<T> classe) {

		try {

			if (o == null) {
				return null;
			}
			
			if ((o.getClass() == classe) || UClass.isInstanceOf(o, classe)) {
				return (T) o;
			}
			
			if (classe.equals(String.class)) {

				if (o.getClass().equals(java.sql.Timestamp.class)) {
					java.sql.Timestamp x = (Timestamp) o;
					return (T) new Data(x).format("[ddd] - [dd]/[mm]/[yyyy] [hh]:[nn]");
				}

				// return o.toString() + o.getClass();
				return (T) o.toString();

			}
			
			if (classe.equals(Long.class) || classe.equals(long.class)) {
				return (T) ULong.toLong(o);
			}
			
			if (classe.equals(Double.class) || classe.equals(double.class)) {
				return (T) UDouble.toDouble(o);
			}
			
			if (classe.equals(Integer.class) || classe.equals(int.class)) {
				return (T) IntegerParse.toInt(o);
			}
			
			if (classe.equals(BigInteger.class)) {
				return (T) UBigInteger.toBigInteger(o);
			}
			
			if (classe.equals(Boolean.class) || classe.equals(boolean.class)) {
				return (T) UBoolean.toBoolean(o);
			}
			
			if (classe.equals(Short.class) || classe.equals(short.class)) {
				return (T) UShort.toShort(o);
			}
			
			if (classe.equals(Data.class)) {
				Data data = Data.to(o);
				return (T) data;
			}
			
			if (classe.equals(Date.class)) {
				Data data = Data.to(o);
				return (T) data.toDate();
			}
			
			if (classe.equals(java.sql.Date.class)) {
				return (T) Data.to(o).toSqlDate();
			}
			
			if (classe.equals(Calendar.class)) {
				Data data = Data.to(o);
				return (T) data.getCalendar();
			}
			
			if (classe.equals(LocalDate.class)) {
				Data data = Data.to(o);
				return (T) data.toLocalDate();
			}
			
			if (classe.equals(Numeric1.class)) {
				BigDecimal money = UBigDecimal.toMoney(o);
				Numeric1 d = new Numeric1(money);
				return (T) d;
			}
			
			if (classe.equals(Numeric2.class)) {
				BigDecimal money = UBigDecimal.toMoney(o);
				Numeric2 d = new Numeric2(money);
				return (T) d;
			}
			
			if (classe.equals(Numeric3.class)) {
				BigDecimal money = UBigDecimal.toBigDecimal(o, 3);
				Numeric3 d = new Numeric3(money);
				return (T) d;
			}
			
			if (classe.equals(Numeric4.class)) {
				BigDecimal money = UBigDecimal.toBigDecimal(o, 4);
				Numeric4 d = new Numeric4(money);
				return (T) d;
			}
			
			if (classe.equals(Numeric5.class)) {
				BigDecimal money = UBigDecimal.toBigDecimal(o, 5);
				Numeric5 d = new Numeric5(money);
				return (T) d;
			}
			
			if (classe.equals(Numeric6.class)) {
				BigDecimal money = UBigDecimal.toBigDecimal(o, 6);
				Numeric6 d = new Numeric6(money);
				return (T) d;
			}
			
			if (classe.equals(Numeric8.class)) {
				BigDecimal money = UBigDecimal.toBigDecimal(o, 8);
				Numeric8 d = new Numeric8(money);
				return (T) d;
			}
			
			if (classe.equals(BigDecimal.class)) {
				return (T) UBigDecimal.toMoney(o);
			}

			if (classe.equals(Character.class) || classe.equals(char.class)) {
				
				String s = StringParse.get(o);
				
				if (StringEmpty.is(s)) {
					return null;
				}
				
				if (StringLength.get(s) == 1) {
					Character c = s.charAt(0);
					return (T) c;
				}
				
				throw new NaoImplementadoException();
				
			}
			
			if (classe.equals(Byte.class)) {
				
				Byte x;
				
				if (o instanceof Boolean) {
					Boolean b = (Boolean) o;
					if (b) {
						x = 1;
					} else {
						x = 0;
					}
				} else if (o instanceof Integer) {
					Integer i = (Integer) o;
					x = i.byteValue();
				} else if (o instanceof String && IntegerIs.is(o)) {
					Integer i = IntegerParse.toInt(o);
					x = i.byteValue();
				} else {
					throw new NaoImplementadoException(o.getClass());
				}
				
				return (T) x;
				
			}
			
			if (classe.equals(Lst.class) && (o instanceof List)) {
				List<?> list = (List<?>) o;
				Lst<?> lst = new Lst<>(list);
				return (T) lst;
			}

			if (classe.equals(Array.class) && (o instanceof List)) {
				List<?> list = (List<?>) o;
				Array<?> array = new Array<>();
				for (Object obj : list) {
					array.java_addCast(obj);
				}
				return (T) array;
			}

			if (classe.getAnnotation(Table.class) != null) {
				if (o instanceof Map) {
					Map<String, Object> map = (Map<String, Object>) o;
					o = map.get("id");
				}
				return UConfig.jpa().findById(classe, o);
			}
		} catch (Exception e) {
		}
		
		if (classe.isEnum()) {
			
			if (o instanceof Integer) {
				int i = (int) o;
				return classe.getEnumConstants()[i];
			}

			if (o instanceof String) {
				String s = (String) o;
				T[] enumConstants = classe.getEnumConstants();
				for (T t : enumConstants) {
					if (t.toString().equalsIgnoreCase(s)) {
						return t;
					}
				}
			}
			
		}
		
		if (classe == ListString.class) {
			
			if (o instanceof List) {
				ListString listString = new Lst<>((List<?>) o).toListString();
				return (T) listString;
			}
			
		}

		if (UType.tryCast != null) {
			try {
				return (T) UType.tryCast.call(o, classe);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (UType.parseCustom != null) {
			try {
				return (T) UType.parseCustom.call(o, classe);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return null;
	}
	
	public static F2<Object, Class<?>, ?> parseCustom;

	@SuppressWarnings("unchecked")
	public static <T> T parse(Object o, Class<T> classe) {
		if (o == null) {
			return null;
		}
		if (UClass.isInstanceOf(o, classe)) {
			return (T) o;
		}
		if (classe.equals(Integer.class) || classe.equals(int.class)) {
			o = IntegerParse.toInt(o);
			return (T) o;
		}
		if (classe.equals(Short.class) || classe.equals(short.class)) {
			Integer i = IntegerParse.toInt(o);
			o = i.shortValue();
			return (T) o;
		}
		if (classe.equals(Long.class) || classe.equals(long.class)) {
			o = ULong.toLong(o);
			return (T) o;
		}
		if (classe.equals(String.class)) {
			o = StringParse.get(o);
			return (T) o;
		}
		if (classe.equals(Data.class)) {
			if (o instanceof Calendar) {
				Calendar c = (Calendar) o;
				o = new Data(c);
				return (T) o;
			}
			o = new Data(o.toString());
			return (T) o;
		}

		if (classe.equals(Boolean.class)) {
			return (T) UBoolean.toBoolean(o);
		}

		if (classe.equals(boolean.class)) {

			Boolean v = UBoolean.toBoolean(o);

			if (v == null) {
				v = false;
			}

			return (T) v;

		}
		
		if (classe.equals(Calendar.class)) {
			return (T) Data.to(o).getCalendar();
		}

		if (classe.equals(Character.class)) {
			String s = StringParse.get(o);
			if (StringEmpty.is(s)) {
				return null;
			}
			Character c = s.charAt(0);
			return (T) c;
		}
		
		if (classe.isEnum()) {
			
			if (o instanceof Integer) {
				int i = (int) o;
				return classe.getEnumConstants()[i];
			}
			
			if (o instanceof String) {
				String s = (String) o;
				for (T t : classe.getEnumConstants()) {
					if (t.toString().contentEquals(s)) {
						return t;
					}
				}
			}
			
		}
		
		if (parseCustom != null) {
			
			Object result = parseCustom.call(o, classe);
			
			if (result == null) {
				result = parseCustom.call(o, classe);
			}
			
			if (result != null) {
				return (T) result;
			}
		}

		throw UException.runtime("Não é possível parse ( " + o + " " + o.getClass().getSimpleName() + " ) to " + classe.getSimpleName());

	}

	public static <T> T cast(Object o, Class<T> classe) {
		if (o == null) {
			return null;
		}
		T t = UType.tryCast(o, classe);
		if (t == null) {
			throw UException.runtime("Utils.cast(" + o + ") - Não implementado: " + classe);
		}
		return t;
	}

	public static boolean isFunction(Object o) {
		if (o == null) {
			return false;
		} else {
			return isFunction(o.getClass());
		}
	}
	
	public static boolean isFunction(Class<?> type) {
		if ((type.getPackage() == F0.class.getPackage()) || type == Predicate.class || type == Consumer.class) {
			return true;
		}
		return false;
	}

}
