package gm.utils.jpa.constructor;

import java.math.BigDecimal;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import gm.utils.comum.SystemPrint;
import gm.utils.comum.ULog;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBC;
import gm.utils.jpa.USchema;
import gm.utils.jpa.UTable;
import gm.utils.jpa.TableSchema;
import gm.utils.jpa.converters.JpaConverterDate;
import gm.utils.jpa.converters.JpaConverterDateTime;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import jakarta.validation.constraints.Digits;
import js.support.console;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringEmpty;

@Getter @Setter
public class CreateTable {

	private ConexaoJdbc con;
	private DriverJDBC driver;
	private String owner;
	private boolean veriricarNullEmUniques = true;

	public CreateTable(DriverJDBC driver, String owner) {
		this.driver = driver;
		this.owner = owner;
	}

	public CreateTable(ConexaoJdbc con) {
		this(con.getDriver(), con.getOwner());
		this.con = con;
	}

	private boolean guardarReferencias = true;
	private boolean aceitarSomenteItensNoPadrao = true;
	private ListString referencias = new ListString();
	private ListString constraints = new ListString();

	private static final Class<byte[]> byteClass = byte[].class;

	public String tratarColuna(Atributo a){
		return this.tratarColuna(TableSchema.get(a.getClasse()), a);
	}

	public void tratarUnique(String ts, Atributo a, Atributos as) {

		if ("public.usuario".equalsIgnoreCase(ts)) {
			console.log(a);
		}

		String campo = a.getColumnName();

		String where = "";

		String s;

		if (a.isUnique()) {
			s = "create unique index "+ts.replace(".", "_")+"_"+campo+" on "+ts+"("+campo+")";
		} else {

			String unicoPor = a.uniqueJoin();
			if (unicoPor == null) {
				return;
			}

			ListString palavras = ListString.byDelimiter(unicoPor, ",");
			palavras.trimPlus();
			s = "create unique index "+ts.replace(".", "_")+"_"+campo;
			for (String string : palavras) {
				s += "_"+string;
			}

			s += " on "+ts+"("+campo;
			for (String string : palavras) {
				s += ","+string;
			}
			s += ")";

			if (veriricarNullEmUniques) {
				for (String string : palavras) {
					if (!isNotNull(as.getObrig(string))) {
						where += " and "+string+" is not null";
					}
				}
			}

		}

		if (veriricarNullEmUniques && !isNotNull(a)) {
			where += " and "+campo+" is not null";
		}

		String uniqueWhere = a.getProp("UniqueWhere");
		if (!StringEmpty.is(uniqueWhere)) {
			where += " and "+uniqueWhere;
		}

		if (!StringEmpty.is(where)) {
			s += " where " + where.substring(5);
		}

		if (driver == DriverJDBC.MSSQLServer) {
			s = s.replace("create unique index", "create unique nonclustered index");
		}

		s = s.toLowerCase();

		constraints.add(s + ";");

	}

	public String tratarColuna(String ts, Atributo a) {

		Class<?> type = a.getType();
		String campo = a.getColumnName();

		if (a.getAnnotation(java.beans.Transient.class) != null) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " estah com a anotacao 'java.beans.Transient', mas a anotacao correta eh 'javax.persistence.Transient'!");
		}
		if (a.isList()) {
			throw new MessageException ("O campo " + ts + "." + campo + " é uma List, isso vai contra a arquitetura adotada!");
		}
		if (a.getAnnotation(OneToOne.class) != null) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " estah com a anotacao 'OneToOne', isso vai contra a arquitetura adotada!");
		}
		if (type.equals(CreateTable.byteClass)) {
			javax.persistence.Lob lob = a.getAnnotation(javax.persistence.Lob.class);
			if (lob == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " precisa ter a anotacao @Lob (javax.persistence.Lob)");
			}
			return campo + " bytea";
		}

//		if (type.equals(Long.class)) {
//			throw new MessageException ("O campo " + ts + "." + campo + " estah com o tipo Long. Utilize o tipo Integer");
//		}
		Column column = a.getAnnotation(Column.class);

		if (type.equals(String.class)) {

			column = a.getAnnotation(Column.class);

			int length;

			if (column == null) {
				length = 255;
//				throw new MessageException ("O campo " + ts + "." + campo + " precisa ter a anotacao @Column(length=?)");
			} else if (column.length() < 1) {
				throw new MessageException (campo + " column.length() < 1");
			} else {
				length = column.length();
			}

			return campo + " varchar(" + length + ")";

		}
		if (type.equals(Double.class) || type.equals(double.class)) {
			throw new MessageException ("O campo " + ts + "." + campo + " é do tipo Double! Use o tipo BigDecimal");
		} else if (type.equals(Float.class) || type.equals(float.class)) {
			throw new MessageException ("O campo " + ts + "." + campo + " é do tipo Float! Use o tipo BigDecimal");
		} else if (type.equals(BigDecimal.class)) {

			Digits digits = a.getAnnotation(Digits.class);

			if (digits == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " precisa ter a anotacao @Digits(integer=15, fraction=2)");
			}
			if (digits.fraction() == 0) {
				throw new MessageException ("O campo " + ts + "." + campo + " estah com a anotacao @Digits fraction = 0");
			}
			if (digits.fraction() >= digits.integer()) {
				throw new MessageException ("O campo " + ts + "." + campo + " digits.fraction() >= digits.integer()");
			}
//			if (digits.integer() > 15) {
//				throw new MessageException ("O campo " + ts + "." + campo + " digits.integer() > 15");
//			}
			return campo + " numeric(" + digits.integer() + "," + digits.fraction() + ")";

		} else if ("int".equals(type.getName())) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " tem o tipo int. A utilizacao deste tipo irah causar varios tipos de problemas na infraestrutura adotada. Utilize Integer!");
		} else if ("boolean".equals(type.getName())) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " tem o tipo boolean. A utilizacao deste tipo irah causar varios tipos de problemas na infraestrutura adotada. Utilize Boolean!");
		} else if ( UType.isList(type) ) {
			throw new MessageException ("O campo " + ts + "." + campo + " deve ser anotado com @Transient");
		} else if (type.equals(Integer.class)) {
			return campo + " int";
		} else if (type.equals(Long.class)) {
			return campo + " bigint";
		}
		if (type.equals(Boolean.class)) {
			if (driver == DriverJDBC.PostgreSQL) {
				String s = campo + " boolean";
				if (a.eq("excluido") || a.eq("somenteLeitura")) {
					s += " default false";
				}
				return s;
			}
			if (driver == DriverJDBC.MSSQLServer) {
				String s = campo + " bit";
				if (a.eq("excluido") || a.eq("somenteLeitura")) {
					s += " default 0";
				}
				return s;
			} else {
				throw UException.runtime("driver nao tratado: " + driver);
			}

		}

		if (type == Calendar.class) {

			Temporal temporal = a.getAnnotation(Temporal.class);

			if (temporal == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " deve ser anotado com @Temporal(TemporalType.DATE) ou @Temporal(TemporalType.TIMESTAMP)");
			}
			if (TemporalType.DATE.equals(temporal.value())) {
				return campo + " date";
			}
			if (TemporalType.TIMESTAMP.equals(temporal.value())) {
				return campo + " " + dateTimeDoBanco();
			}
			throw new MessageException (", " + campo + " anotacao deve ser @Temporal(TemporalType.DATE) ou @Temporal(TemporalType.TIMESTAMP)");

		}

		if (type == Data.class) {

			Temporal temporal = a.getAnnotation(Temporal.class);

			if (temporal != null) {
				throw new MessageException ("O campo " + ts + "." + campo + " nao deve ser anotado com @Temporal");
			}

			Convert convert = a.getAnnotation(Convert.class);

			if (convert == null) {
				throw new MessageException ("Tipo do campo " + campo + " (Data) invalido! Mude para java.util.Calendar ou anote @Convert(converter = JpaConverterDate ou JpaConverterDateTime)");
			}

			Class<?> cs = convert.converter();

			if (cs == JpaConverterDate.class) {
				return campo + " date";
			}

			if (cs == JpaConverterDateTime.class) {
				return campo + " " + dateTimeDoBanco();
			}

			throw new MessageException ("Converter do campo " + campo + " (Data) invalido! Esperado JpaConverterDate ou JpaConverterDateTime");

		}

		if (UType.isData(type)) {
			throw new MessageException ("Tipo do campo " + campo + " invalido! Mude para java.util.Calendar ou Date com converter");
		}

		if (a.getAnnotation(ManyToOne.class) == null) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " deve ser anotado com @ManyToOne(fetch = FetchType.LAZY ) @JoinColumn(name = \""
					+ a.nome() + "\")");
		}
		
		if (a.getAnnotation(JoinColumn.class) != null) {
			String s = a.getAnnotation(JoinColumn.class).name();
			if (StringEmpty.is(s)) {
				throw new MessageException ("A anotacao @JoinColumn do campo " + campo + " deve possuir o atributo 'name'");
			}
			if (!s.equalsIgnoreCase(a.nome()) && aceitarSomenteItensNoPadrao) {
				throw new MessageException ("no campo " +  ts + "." + campo + " a anotacao @JoinColumn.name eh diferente do nome do campo");
			}
		}
		
		if (a.isPublic()) {
			throw new MessageException ("O atributo " + campo + " nao pode ser publico");
		}

		TableAnnotation table = new TableAnnotation(con, a.getType());

		String name = table.schema + "." + table.name;

		String referencesOptions;
		if (driver == DriverJDBC.PostgreSQL) {
			referencesOptions = " on update cascade on delete restrict";
		} else if (driver == DriverJDBC.MSSQLServer) {
			/* a porcaria do SQLServer nao permite que hajam duas foreign keys para a mesma table com update cascade
			 * entao, se houverem na mesma table duas fks para outra mesma table ele ira reclamar
			 * */
//			if (name.equals(ts)) {
				referencesOptions = "";
//			} else {
//
//				referencesOptions = " on update cascade on delete no action";
//			}
		} else {
			throw UException.runtime("driver nao tratado: " + driver);
		}

		if (!guardarReferencias) {
			return campo + " int references " + name + referencesOptions;
		}
		String s;
		
		if (driver == DriverJDBC.PostgreSQL) {
			s = "alter table " + ts + " add foreign key (" + campo + ") references " + name + referencesOptions + ";";
		} else if (driver == DriverJDBC.MSSQLServer) {
			s = "alter table " + ts + " add constraint \""+ts+"."+campo+" > "+name+"\" foreign key (" + campo + ") references " + name + referencesOptions + ";";
		} else {
			throw UException.runtime("driver nao tratado: " + driver);
		}

		referencias.addIfNotContains(s);
		return campo + " int";

	}

	private String dateTimeDoBanco() {

		if (driver == DriverJDBC.PostgreSQL) {
			return "timestamp";
		}
		if (driver == DriverJDBC.MSSQLServer) {
			return "datetime";
		} else {
			throw UException.runtime("driver nao tratado: " + driver);
		}

	}

	public ListString valida(Class<?> classe) {
		Table table = classe.getAnnotation(Table.class);
		return valida(table.schema(), classe);

	}
	public ListString valida(String schema, Class<?> c) {

		ListString erros = new ListString();

		String ts = schema + "." + getTableName(c);

		Atributos atributos = AtributosBuild.get(c);
		atributos.removeTransients();
		atributos.removeStatics();

		ListString script = new ListString();

		script.add("create table " + ts + " (");

		GeneratedValue generatedValue = atributos.getId().getAnnotation(GeneratedValue.class);
		boolean sequencial = generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;

		if (!sequencial) {
			script.add("  id int not null primary key");
		} else if (driver == DriverJDBC.PostgreSQL) {
			script.add("  id serial not null primary key");
		} else if (driver == DriverJDBC.MSSQLServer) {
			script.add("  id int identity(1,1) not null primary key");
		} else {
			throw UException.runtime("???");
		}

		for (Atributo a : atributos) {
			try {
				String t = this.tratarColuna(ts, a);
				if (t.endsWith(" ")) {
					this.tratarColuna(ts, a);
				}
				script.add( ", " + t + (isNotNull(a) ? " not null" : "") );
				tratarUnique(ts, a, atributos);
			} catch (Exception e) {
				e.printStackTrace();
				erros.add(e.getMessage());
			}
		}

		script.add("); ");

		if (driver == DriverJDBC.PostgreSQL) {
			script.add("alter table " + ts + " owner to " + owner + ";");
		} else if (driver == DriverJDBC.MSSQLServer) {
			script.add("grant select, insert, update on " + ts + " to " + owner + ";");
		} else {
			script.add("--implementar grant");
		}

		script.add(referencias);
		script.add(constraints);

		if (!erros.isEmpty()) {
			erros.print();
			String s = "Não foi possivel validar a entidade: " + c.getName();
			s += "\n" + erros.toString("\n");
			throw UException.runtime(s);
		}

		return script;
	}

	private String getTableName(Class<?> c) {
		Table table = c.getAnnotation(Table.class);

		if (table == null || StringEmpty.is(table.name())) {
			return c.getSimpleName();
		}
		return table.name();

	}

	private boolean isNotNull(Atributo a) {
		return a.isObrigatorio() && !a.hasAnnotation("VisivelSe");
	}
	public String exec(Class<?> classe) {
		Table table = classe.getAnnotation(Table.class);
		return exec(table.schema(), classe);
	}
	public String exec(String schema, Class<?> classe) {
		ListString valida = valida(schema, classe);
		return valida.toString("\n");
	}
	public void reCreate(Class<?> classe) {
		UTable table = con.table(classe);
		table.drop();
		this.create(table);
	}

	public void create(String schema, Class<?> classe) {
		String sql = exec(schema, classe);
		new USchema(con).create(schema);
		try {
			con.exec(sql);
		} catch (Exception e) {
			SystemPrint.err("Erro ao criar a classe " + classe.getName());
			throw e;
		}
	}

	public void create(Class<?> classe) {

		Table table = classe.getAnnotation(Table.class);

		String schema;

		if (table != null && StringEmpty.notIs(table.schema())) {
			schema = table.schema();
		} else {
			schema = con.getDefaultSchema();
		}

		create(schema, classe);

	}

	public void print(Class<?> classe) {
		ULog.debug(exec(classe));
	}

	public void create(UTable table) {
		if (table.exists()) {
			throw UException.runtime(table.getTs() + " ja existe!");
		}
		Class<?> classe = table.getClasse();
		this.create(classe);
		table.setExists(true);
		Atributos as = AtributosBuild.getPersists(classe, false);
		for (Atributo a : as) {
			a.setExisteNoBanco(con);
		}
	}
	public boolean createIfNotExists(UTable table) {
		if (!table.exists()) {
			this.create(table.getClasse());
			table.setExists(true);
			return true;
		}
		return false;
	}

	public void createIfNotExists(Class<?> classe) {
		this.createIfNotExists(con.table(classe));
	}

}
