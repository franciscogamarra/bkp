package br.sicoob;

import gm.utils.comum.SystemPrint;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringRight;

public class MontarSql {
	
	public static void main(String[] args) {

		ListString list = new ListString();
		
		list.clear();
		list.add("	IDTmpOpCredito  numeric(9) NOT NULL,");
		list.add("	NumCooperativa  smallint NOT NULL,");
		list.add("	NumPac  tinyint NOT NULL,");
		list.add("	DescFonteRecursoAdicional  varchar(100) NOT NULL,");
		list.add("	ValorFonteRecursoAdicional  money NOT NULL");
		exec(list, "TmpFonteRecursoAdicional");
		
		list.clear();
		list.add("	IDTmpOpCredito  numeric(9) NOT NULL, ");
		list.add("	NumCooperativa  smallint NOT NULL,");
		list.add("	NumPac  tinyint NOT NULL,  ");
		list.add("	BolImovelDePropriedadeBeneficiario  bit NOT NULL CONSTRAINT DF_TmpInfoComplementarProjetoInvestimento_BolImovelDePropriedadeBeneficiario DEFAULT 0, ");
		list.add("	BolVigenciaContratoImovel  bit NOT NULL CONSTRAINT DF_TmpInfoComplementarProjetoInvestimento_BolVigenciaContratoImovel DEFAULT 0,  ");
		list.add("	DataInicioProjetoInvestimento  datetime NULL, ");
		list.add("	DataFimProjetoInvestimento  datetime NULL, ");
		list.add("	DescProjetoInvestimento  varchar(5000) NULL");
		exec(list, "TmpInfoComplementarProjetoInvestimento");
		
		list.clear();
		list.add("	IDTmpOpCredito  numeric(9) NOT NULL,  ");
		list.add("	NumCooperativa  smallint NOT NULL, ");
		list.add("	NumPac  tinyint NOT NULL,  ");
		list.add("	QtdUltimoExercicio  int NULL, ");
		list.add("	ValorUltimoExercicio  money NULL, ");
		list.add("	QtdAposProjeto  int NULL, ");
		list.add("	ValorAposProjeto  money NULL, ");
		list.add("	ValorInvestFinancFixo  money NULL, ");
		list.add("	ValorInvestFinancEquipNacional  money NULL, ");
		list.add("	ValorInvestFinancSoftware  money NULL,  ");
		list.add("	ValorCapitalGiroAssociado  money NULL,");
		list.add("	CodTipoResultadoEsperado  int NULL, ");
		list.add("	CodUnidadeMedida  int NULL,  ");
		list.add("	CodTipoUnidadeMedida  int NULL");
		exec(list, "TmpInfoResultadoEsperado");
		
	}

	private static void exec(ListString list, String table) {
		
		list.trimPlus();
		
		SystemPrint.ln("");
		SystemPrint.ln("/* "+table+" */");
		
		for (String s : list) {
			
			if (s.contains(" CONSTRAINT ")) {
				s = StringBeforeFirst.get(s, " CONSTRAINT ");
			}
			
			if (s.endsWith(",")) {
				s = StringRight.ignore1(s);
			}
			s = s.replace(" NOT NULL", "");
			s = s.replace(" NULL", "");
			String nome = StringBeforeFirst.get(s, " ");
			String tipo = StringAfterFirst.get(s, " ");
			
			String get;
//			String complemento = "";
			
			if (tipo.contentEquals("money")) {
				get = "getMoney";
			} else if (tipo.contentEquals("int") || tipo.contentEquals("smallint") || tipo.contentEquals("tinyint")) {
				get = "getInt";
			} else if (tipo.contentEquals("datetime")) {
				get = "getDateTime";
			} else if (tipo.contentEquals("bit")) {
				get = "getBoolean";
			} else if (tipo.startsWith("varchar(")) {
				get = "getString";
			} else if (tipo.startsWith("numeric(")) {
				
				if (tipo.contains(",")) {
					get = "getBigDecimal";
				} else {
					get = "getLong";
				}
				
			} else {
				get = "?<"+nome+"/"+tipo+">";
			}
			
//			rs.getInteger("IDTmpOpCredito");
			
			SystemPrint.ln("rs." + get + "(\"" + nome + "\");");
		}
		
//		list.replaceEach(i -> StringBeforeFirst.get(i, " "));
//		list.replaceEach(i -> ", " + table + "_" + i + " = " + table + "." + i);
//		list.print();
	}
	
}
