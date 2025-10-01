package gm.utils.jpa;

import gm.utils.anotacoes.Unique;
import gm.utils.classes.interfaces.IAnnotation;
import gm.utils.classes.interfaces.IClass;
import gm.utils.classes.interfaces.IField;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.jpa.converters.JpaConverterSN;
import gm.utils.number.Numeric2;
import gm.utils.string.ListString;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import src.commom.utils.tempo.Data;
import src.commom.utils.tempo.DataHora;

public class GerarScriptPostgres {

	private static final Lst<String> list = new Lst<>();
	private static final Lst<IClass> jaExecutadas = new Lst<>();
	
	public static Lst<String> exec(Lst<IClass> classes) {
		
		if (USchema.getSchemaDefault() == null) {
			USchema.setSchemaDefault("public");
		}
		
		list.clear();
		for (IClass classe : classes) {
			exec(classe);
		}
		return list;
	}
	
	private static void exec(IClass classe) {
		
		jaExecutadas.ad(classe);

		list.add("/* "+classe.sn()+" */");
		list.add("create table " + classe.ts() + " (");
		
		ListString after = new ListString();

		Lst<IField> as = classe.getFields();
		
		as.removeIf(a -> a.isStatic());
		as.removeIf(a -> a.isFinal());
		as.removeIf(a -> a.isTransient());
		
		for (IField a : as) {

			String s = "\t" + a.getColumnName() + " " + getType(a);
			
			if (a.isNotNull()) {
				s += " not null";
			}
			
			if (a.isId()) {
				s += " primary key";
//				after.add("create sequence "+classe.getVarName()+"_seq start 1 increment 1;");
			}
			
			if (a.isAnnotationPresent(Unique.class)) {
				s += " unique";
			}
			
			IClass type = a.getType();
			if (type.isAnnotationPresent(Entity.class)) {
				
				if (!jaExecutadas.contains(type)) {
					throw new RuntimeException("Coloque a classe " + type.sn() + " antes da classe " + classe.sn());
				}
				
				s += " references " + type.ts();
			}
			
			if (a != as.getLast()) {
				s += ",";
			}
			
			list.add(s);
			
		}
		
		list.add(");");
		list.add("");
		
		if (!after.isEmpty()) {
			list.addAll(after);
			list.add("");
		}
		
	}
	
	private static String getType(IField a) {
		
		if (a.isId()) {
			return "serial";
		}
		
		if (a.is(DataHora.class)) {
			return "datetime";
		}

		if (a.is(Data.class)) {
			return "date";
		}

		if (a.is(Numeric2.class)) {
			return "numeric(9,2)";
		}

		IClass type;
		
		IAnnotation convert = a.getAnnotation(Convert.class);
		
		if (convert != null) {
			
			IClass converter = convert.get("converter");
			
			if (converter.eq(JpaConverterSN.class)) {
				return "char(1)";
			}
			
			type = converter.getMetodoRetorno("convertToDatabaseColumn");
			
		} else {
			type = a.getType();
		}
		
		if (type.isInt()) {
			return "int";
		}

		if (type.isBoolean()) {
			return "boolean";
		}

		if (type.eq(String.class)) {
			return "varchar("+a.getMax()+")";
		}
		
		if (type.isAnnotationPresent(Entity.class)) {
			return "int";
		}
		
		if (type.isEnum()) {
			return "int";
		}
		
		throw new NaoImplementadoException(type.getSimpleName());		
		
	}
	
}
