package gm.utils.jpa.constructor;

import jakarta.persistence.Table;

import gm.utils.jpa.ConexaoJdbc;
import src.commom.utils.string.StringEmpty;

public class TableAnnotation {

	public final String name;
	public final String schema;

	public TableAnnotation(ConexaoJdbc con, Class<?> classe) {

		Table table = classe.getAnnotation(Table.class);

		if (table == null) {
			schema = con.getDefaultSchema();
			name = classe.getSimpleName();
		} else {

			if (StringEmpty.is(table.name())) {
				name = classe.getSimpleName();
			} else {
				name = table.name();
			}

			if (StringEmpty.is(table.schema())) {
				schema = con.getDefaultSchema();
			} else {
				schema = table.schema();
			}

		}

	}

}
