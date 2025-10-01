package gm.utils.comum;

import java.util.List;
import java.util.Map;

import gm.utils.number.Numeric18;
import gm.utils.reflection.AtributoX;
import gm.utils.string.ListString;
import src.commom.utils.comum.SeparaMilhares;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringRight;

public class PrintObj {
	
	public static boolean printNulls = false;
	
	private final ListString lst = new ListString();
	private static final Lst<Object> objects = new Lst<>();

	private PrintObj(Object o) {
		exec(o, "");
	}
	
	public static ListString exec(Object o) {
		return new PrintObj(o).lst;
	}
	
	private void exec(Object o, String key) {
		
		if (o == null) {
			if (printNulls) {
				lst.add(key + " null");
			}
			return;
		}
		
		if (IntegerIs.is(key)) {
			key += " :";
		} else {
			key = o.getClass().getSimpleName() + " " + key;
		}
		
		if (o instanceof Double) {
			Double d = (Double) o;
			String s = new Numeric18(d).toString();
			if (s.contains(",")) {
				while (s.endsWith("0") && StringAfterFirst.get(s, ",").length() > 2) {
					s = StringRight.ignore1(s);
				}
			}
			lst.add(key + " " + s);
			return;
		}
		
		if (UType.isPrimitiva(o)) {
			lst.add(key + " " + o);
			return;
		}

		if (objects.contains(o)) {
			lst.add(key + " obj("+objects.indexOf(o)+")");
			return;
		}
		
		int index = objects.size();
		
		objects.add(o);
		
		lst.add("(" + index + ") " + key + " {");
		lst.margemInc();
		
		Lst<AtributoX> as = AtributoX.getFrom(o);
		
		for (AtributoX a : as) {
			
			String typeName = a.getType().getSimpleName();
			
			Object v = a.get(o);
			
			if (v == null) {
				if (printNulls) {
					lst.add(typeName + " " + a.getName() + " : null ,");
				}
				continue;
			}

			if (a.isFunction()) {
				lst.add(typeName + " " + a.getName() + " : function ,");
				continue;
			}
			
			if (a.isPrimitivo()) {

				if (v instanceof String) {
					v = "\"" + v + "\"";
				} else if (v instanceof Integer) {
					v = SeparaMilhares.exec(v.toString());
				} else if (v instanceof Double) {
					Double d = (Double) v;
					String s = new Numeric18(d).toString();
					while (s.endsWith("0")) {
						s = StringRight.ignore1(s);
					}
					v = s;
				}
				
				lst.add(typeName + " " + a.getName() + " : " + v + " ,");
				
				continue;
				
			}
			
			if (v instanceof List) {
				List<?> list = (List<?>) v;
				list(list, key, typeName, a.getName());
				continue;
			}

			if (v instanceof Map) {
				Map<?,?> map = (Map<?,?>) v;
				map(map, key, typeName, a.getName());
				continue;
			}
			
			exec(v, a.getName());
			
		}
		
		lst.margemDec();
		lst.add("},");
		
	}		
	
	private void list(List<?> list, String key, String columnName, String typeName) {
		
		if (list.isEmpty()) {
			lst.add(typeName + " " + columnName + " : [] ,");
			return;
		}
		
		typeName += "<"+list.get(0).getClass().getSimpleName()+">";
		
		if (UType.isPrimitiva(list.get(0))) {
			lst.add(typeName + " " + columnName + " : "+list+" ,");
			return;
		}
		
		lst.add(typeName + " " + columnName + " : [");
		lst.margemInc();
		
		int index = 0;
		
		for (Object o : list) {
			exec(o, "" + index);
			index++;
		}
		
		lst.margemDec();
		lst.add("],");
		
	}
	
	private void map(Map<?,?> map, String key, String columnName, String typeName) {
		
		if (map.isEmpty()) {
			lst.add(typeName + " " + columnName + " : {} ,");
			return;
		}
		
		lst.add(typeName + " " + columnName + " : {");
		lst.margemInc();
		
		map.forEach((k,o) -> {
			exec(o, k+"");
		});
		
		lst.margemDec();
		lst.add("},");
		
	}
	
	
}
