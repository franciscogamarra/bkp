package gm.utils.jpa.criarViews;

import jakarta.persistence.Table;

import gm.languages.java.JavaLoad;
import gm.languages.java.expressoes.AnnotationJava;
import gm.languages.java.outros.AtributoJL;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.GFile;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.dbs.CooperDBs;
import gm.utils.lambda.F1;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;

public class CriarViewsJpa {
	
	public static void main(String[] args) {
		GFile file = GFile.get("C:\\opt\\desen\\commons\\sicop\\cooperforte-corporativo\\src\\main\\java\\br\\coop\\cooperforte\\corporativo\\persistencia\\entidade\\emprestimo\\engineregras");
		execSicop(file.join("ConfiguracaoRegra.java"));
		execSicop(file.join("Criterio.java"));
		execSicop(file.join("Contexto.java"));
	}
	
	public static void execSicop(GFile file) {

		ConexaoJdbc con = CooperDBs.dsnHomol();
		
		exec(file, con, "sicop", s -> {
			
			if (s.contains(".")) {
				s = StringAfterFirst.get(s, ".");
				if (s.contentEquals("SCH_DBO")) {
					s = "dbo";
				} else {
					throw new NaoImplementadoException(s);
				}
			}
			
			return s;
			
		});
		
	}
	
	public static void exec(GFile file, ConexaoJdbc con, String dataBaseOrigem, F1<String, String> trataSchema) {
		
		JavaLoad java = JavaLoad.exec(file);
		Lst<AtributoJL> atributos = java.getAtributos();
		
		ListString list = new ListString();
		
		for (AtributoJL a : atributos) {
			
			String nome = a.getNome();
			
			String s = ", " + nome;
			
			String nomeBanco = a.getNomeBanco();
			
			if (!nome.equalsIgnoreCase(nomeBanco)) {
				s += " = " + nomeBanco;
			}
			
			list.add(s);
			
		}
		
		String s = list.remove(0);
		s = " " + s.substring(1);
		
		list.add(0, s);
		list.add(0, "create or alter view " + java.getSimpleName() + " as select");
		
		AnnotationJava table = java.getAnnotation(Table.class);
		String tableName = table.getValue("name");
		String schema = table.getValue("schema");
		
		if (trataSchema != null) {
			schema = trataSchema.call(schema);
		}
		
		if (dataBaseOrigem == null) {
			dataBaseOrigem = "";
		} else {
			dataBaseOrigem += ".";
		}
		
		list.add("from " + dataBaseOrigem + schema + "." + tableName);

		con.exec(list);
		
	}
	
}