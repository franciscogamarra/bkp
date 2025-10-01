package gm.utils.jpa;

import gm.utils.abstrato.SimpleIdObject;
import gm.utils.comum.Lst;
import gm.utils.string.ListString;

public class UResultSet {

	public ListString titulos = new ListString();
	public Lst<Object[]> dados = new Lst<>();
	public boolean edit;
	public String table;

	public UResultSet() {}

	public UResultSet(ListString list) {
		this();
		titulos.add("Texto");
		int i = 1;
		for (String s : list) {
			Object[] a = new Object[1];
			SimpleIdObject o = new SimpleIdObject(i, s);
			i++;
			a[0] = o;
			dados.add(a);
		}
	}

	public void vertical() {

		UResultSet rs = new UResultSet();

		rs.titulos.add( "Colunas" );

		int size = dados.size();

		if (size > 100) {
			size = 100;
		}

		for (int i = 0; i < size; i++) {
			rs.titulos.add( "R"+i );
		}

		int id = 0;
		for (String s : titulos) {
			Object[] array = new Object[size+1];
			array[0] = new SimpleIdObject(id, s);
			id++;
			rs.dados.add(array);
		}

		for (int i = 0; i < size; i++) {
			Object[] o = dados.get(i);
			for (int j = 0; j < o.length; j++) {
				rs.dados.get(j)[i+1] = o[j];
			}
		}

		titulos = rs.titulos;
		dados = rs.dados;

	}

}
