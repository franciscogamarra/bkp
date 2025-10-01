package br.sicoob.compare;

import java.math.BigDecimal;
import java.util.Date;

import gm.utils.comum.Lst;
import gm.utils.date.Data;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.UResultSet;
import gm.utils.jpa.dbs.SicoobDBs;
import gm.utils.number.Numeric4;
import gm.utils.string.ListString;
import lombok.AllArgsConstructor;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringFormat;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringRepete;

public class Compare {

	/*

	select 'select distinct ' || colname || ' from ' || trim(tabschema) || '.' || tabname || ' order by 1;'
	from syscat.columns
	where lower(colname) like lower('%fgi%')
	  and tabschema = 'bnd'
	order by tabname;
	
	create table bnd.PessoaTemp (
	  idPessoa int, codTipoPessoa int, numCpfCnpj varchar(14)
	)
	
	select * from cli.viw_pessoa where idPessoa = 522258
	
	drop table PessoaTemp
	
	insert into bnd.PessoaTemp
	select distinct idPessoa, codTipoPessoa, numCpfCnpj from cli.viw_pessoa where idPessoa = 522258	

	*/
	
	private static Lst<Integer> ids = new Lst<>(0
		, 24777787
		, 24778036
		, 24777816
		, 24777790
		, 24777788
		, 24777787
		, 24777786
		, 21720065
		, 25778037
		, 22025661
		, 24815114
		, 22025680//f
//		, 58420
	);
	
	private static final String LISTAR_PROPOSTA_LEGADO_FINANCIAMENTO_FINAME = "a";
	private static final String LISTAR_EQUIPAMENTO_POR_PROPOSTA_FINAME_FINANCIAMENTO = "b";
	private static final String LISTAR_PROPOSTA_LEGADO_CONTRATACAO = "c";
	private static final String RECUPERAR_DADOS_CAR_EMPREENDIMENTO_LEGADO = "d";
	private static final String LISTAR_PROPOSTA_LEGADO_LIBERACAO_FINAME = "e";
	private static final String LISTAR_PROPOSTA_LEGADO_LIBERACAO = "f";
	private static final String LISTAR_CONTRATO_LEGADO_LIBERACAO_SUBSEQUENTE = "g";
	private static final String LISTAR_TMP_FONTES_DE_RECURSO_ADICIONAIS = "h";
	private static final String LISTAR_TMP_INFO_COMPLEMENTAR_PROJETO_INVESTIMENTO = "i";
	private static final String LISTAR_TMP_INFO_RESULTADO_ESPERADO = "j";
	
	private static final ConexaoJdbc db2dev = SicoobDBs.db2_dev();

	private static class Item {
		String s;
		String a;
		String b;
		String res;
	}

	public static void main(String[] args) {
//		exec(LISTAR_PROPOSTA_LEGADO_FINANCIAMENTO_FINAME);//a
//		prt(LISTAR_EQUIPAMENTO_POR_PROPOSTA_FINAME_FINANCIAMENTO);//b
//		prt(LISTAR_PROPOSTA_LEGADO_CONTRATACAO);//c
//		exec(RECUPERAR_DADOS_CAR_EMPREENDIMENTO_LEGADO);//d
//		prt(LISTAR_PROPOSTA_LEGADO_LIBERACAO_FINAME);//e
//		exec(LISTAR_PROPOSTA_LEGADO_LIBERACAO);//f
		prt(LISTAR_CONTRATO_LEGADO_LIBERACAO_SUBSEQUENTE);//g
//		exec(LISTAR_TMP_FONTES_DE_RECURSO_ADICIONAIS);//h
//		exec(LISTAR_TMP_INFO_COMPLEMENTAR_PROJETO_INVESTIMENTO);//i
//		exec(LISTAR_TMP_INFO_RESULTADO_ESPERADO);//j
	}
	
	private static void prt(String pack) {
		ListString list = load(pack, "db2");
		list.addLeft("+ \" ");
		list.addRight(" \"");
		list.print();
	}

	public static void exec(String pack) {
		ids.remove(0);
		for (int id : ids) {
			if (encontrouDiferencas(pack, id)) {
				return;
			}
		}
	}
	
	public static boolean encontrouDiferencas(String pack, int id) {
		
		UResultSet sql = get(pack, "sqlserver", SicoobDBs.sqlserver_dev(), id);
		UResultSet db2 = get(pack, "db2", db2dev, id);
		
		Object[] vas = sql.dados.isEmpty() ? null : sql.dados.get(0);
		Object[] vbs = db2.dados.isEmpty() ? null : db2.dados.get(0);
		
		Lst<Item> lst = new Lst<>();
		
		for (int i = 0; i < sql.titulos.size(); i++) {
			
			Item o = new Item();
			
			o.s = sql.titulos.get(i);
			String b = db2.titulos.get(i);
			
			if (!o.s.equalsIgnoreCase(b)) {
				throw new RuntimeException("a.titulo != b.titulo : " + o.s + " / " + b);
			}
			
			o.a = vas == null ? null : toString(vas[i]);
			o.b = vbs == null ? null : toString(vbs[i]);
			
			lst.add(o);
			
		}
		
		if (lst.isEmpty()) {
			return false;
		}

		int lenTitulo = lst.mapIntSorted(i -> i.s.length()).getLast();
		int len = IntegerCompare.getMaior(lst.mapIntSorted(i -> StringLength.get(i.a)).getLast(), lst.mapIntSorted(i -> StringLength.get(i.b)).getLast());
		len = IntegerCompare.getMaior(len, "SqlServer".length());
		
		for (Item o : lst) {
			
			o.s = StringFormat.rightPad(o.s, " ", lenTitulo);
			o.a = StringFormat.leftPad(o.a, " ", len);
			o.b = StringFormat.leftPad(o.b, " ", len);
			
			String s = "| " + o.s + " | " + o.a + " | " + o.b + " |";
			
			if (!StringCompare.eqIgnoreCase(o.a, o.b)) {
				s += " *";
			}
			
			o.res = s;
			
		}
		
		Lst<Item> comDiferencas = lst.filter(i -> i.res.endsWith("*"));
		
		if (comDiferencas.isEmpty()) {
			return false;
		}

		String tt = "| " + StringFormat.rightPad("Campo", " ", lenTitulo) +
				" | " + StringFormat.leftPad("SqlServer", " ", len) +
				" | " + StringFormat.leftPad("DB2", " ", len) + " |";
		
		System.out.println();
		
		int rowSize = 100;
		
		String quebra = StringRepete.exec("=", rowSize);
		System.out.println(quebra);
		String sProposta = " PropostaCredito : " + id + " ";
		System.out.println(StringFormat.rightPad(StringFormat.leftPad(sProposta, ">", (rowSize / 2) + (sProposta.length() / 2)), "<", rowSize));
		System.out.println(quebra);
		System.out.println(tt);
		
		comDiferencas.forEach(i -> System.out.println(i.res));
		lst.filter(i -> !i.res.endsWith("*")).forEach(i -> System.out.println(i.res));
//		
		print("select * from bnd.prePropostaBndes where numPropostaCredito = " + id).forEach(propostaBndes -> {
			
			print("select * from bnd.ProgramaBndes where codProgramaBndes = " + propostaBndes.get("CODPROGRAMABNDES")).forEach(programaBndes -> {
				print("select * from bnd.CondicaoOperacional where idCondicaoOperacional = " + programaBndes.get("IDCONDICAOOPERACIONAL"));
			});
			
			print("select * from bnd.EnquadramentoCliente where idEnquadramentoCliente = " + propostaBndes.get("IDENQUADRAMENTOCLIENTE"));
			print("select * from bnd.InvestimentoBndes where idInvestimentoBndes = " + propostaBndes.get("IDINVESTIMENTOBNDES"));
			print("select * from bnd.pedidoLiberacaoBndes where idPrePropostaBndes = " + propostaBndes.get("IDPREPROPOSTABNDES"));
			print("select * from bnd.LicencaAmbiental where idLicencaAmbiental = " + propostaBndes.get("idLicencaAmbiental"));
			
		});
		
		
		return true;
		
//		| Campo                                  |           SqlServer |                 DB2 |
//		| TipoDocumento                          |                   2 |                     | *
//		| NroDocumento                           |     12369875223/236 |                     | *
//		| DataEmissao                            | 01/05/2021 00:00:00 |                     | *
//		| DataValidade                           | 31/12/2025 00:00:00 |                     | *
//		| OrgaoConcedente                        |                 SMA |                     | *
//		| UFOrgaoConcedente                      |                  BA |                     | *		
		
	}

	@AllArgsConstructor
	private static class Dado {
		String key;
		String value;
		String row;
	}
	
	private static class Dados {
		Lst<Dado> dados = new Lst<>();
		public String get(String key) {
			Dado o = dados.unique(i -> i.key.equalsIgnoreCase(key));
			return o == null ? null : o.value;
		}
	}
	
//	ProgramaBndes.CODPRODUTOSISBR as IDProdutoBNDES
	private static Lst<Dados> print(String sql) {
		
		System.out.println();
		String row = StringRepete.exec("-", sql.length() + 2);
		System.out.println(row);
		System.out.println(" " + sql);
		System.out.println(row);
		
		UResultSet rs = db2dev.rs(sql);
		Lst<String> titulos = new Lst<>(rs.titulos);
		int len = titulos.mapIntSorted(i -> i.length()).getLast();
		
		Lst<Dados> resultado = new Lst<>();

		for (Object[] vas : rs.dados) {
			
			Dados dados = new Dados();
			resultado.add(dados);

			for (int i = 0; i < rs.titulos.size(); i++) {
				String campo = rs.titulos.get(i);
				String valor = toString(vas[i]);
				String s = StringFormat.rightPad(campo, " ", len) + " | " + valor + " ";
				System.out.println(s);
				dados.dados.add(new Dado(campo, valor, s));
			}
			
		}
		
//		Object[] vas = rs.dados.get(0);
//		
//		Map<String, String> result = new HashMap<>();
//		
//		for (int i = 0; i < rs.titulos.size(); i++) {
//			String campo = rs.titulos.get(i);
//			String valor = toString(vas[i]);
//			String s = StringFormat.rightPad(campo, " ", len) + " | " + valor + " ";
//			System.out.println(s);
//			result.put(campo, valor);
//		}
		
		return resultado;
		
	}

	private static String toString(Object o) {
		
		if (o == null) {
			return "";
		}

		if (o instanceof String s) {
			return s.trim();
		}
		
		if (o instanceof BigDecimal b) {
			return new Numeric4(b).toStringPonto();
		}
		
		if (o instanceof Date d) {
			Data data = Data.newData(d);
			return data.format_dd_mm_yyyy_hh_mm_ss();
		}

		if (o instanceof Integer i) {
			return i.toString();
		}

		if (o instanceof Short i) {
			return i.toString();
		}
		
		if (o instanceof Long i) {
			return i.toString();
		}

		if (o instanceof Boolean i) {
			return i.toString();
		}
		
		return o.toString().trim();
	}
	
	private static ListString load(String pack, String file) {

		ListString list = new ListString();
		list.load("C:\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\java\\br\\sicoob\\compare\\"+pack+"\\"+file+".sql");
		list.rtrim();
		list.removeIf(i -> i.trim().startsWith("--"));
		list.removeIf(i -> i.trim().startsWith("/*") && i.trim().endsWith("*/"));
		list.replaceEach(i -> {
			if (i.contains("--")) {
				return StringBeforeFirst.get(i, "--");
			} else {
				return i;
			}
		});
		list.removeFisrtEmptys();
		list.removeLastEmptys();
		
		ListString list2 = new ListString();

		while (!list.isEmpty()) {
			String s = list.remove(0);
			if (s.trim().startsWith("/*")) {
				while (!list.remove(0).trim().startsWith("*/")) {
					
				}
			} else {
				list2.add(s.replace(":numeroPropostaCredito", "?"));
			}
		}
		
		return list2;
		
	}

	public static UResultSet get(String pack, String file, ConexaoJdbc con, int id) {
		
		ListString list = load(pack, file);
		
		list.trimPlus();
		
		String s = list.join(" ");
		
		if (s.endsWith("=")) {
			s += " " + id;
		} else {
			s = s.replace(":numeroPropostaCredito", id +"");
		}
		
		s = s + " order by 1";
		
		System.out.println(s);
		
		return con.rs(s);
		
//		:numeroPropostaCredito
		
	}
	
}


