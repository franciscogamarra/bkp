package gm.utils.map;

import java.util.ArrayList;
import java.util.List;

import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

public class MapSoFromJson {

	private static final String $STRING = "$STRING";
	private static final String $OBJ = "$OBJ";
	private static final String $ARRAY = "$ARRAY";
	private static final String ASPA = "\"";
	private static final String $QUEBRA = "$QUEBRA";

	private final ListString strings = new ListString();
	private final ListString objetos = new ListString();
	private final ListString arrays = new ListString();
	private final MapSO result;

	private MapSoFromJson(String s) {
		if (s.startsWith("[")) {
			s = "{\"array\":" + s + "}";
		}
		
//		s = StringSplit.exec(s, "\n").join(" ");
		result = exec(s);
	}
	
//	@SuppressWarnings("unchecked")
//	private HashMap<String, Object> fromGson(String s) {
//		
//		try {
//			Gson gson = new Gson();
//			return gson.fromJson(s, HashMap.class);
//		} catch (Exception e) {
//			return null;
//		}
//		
//	}

	private MapSO exec(String s) {

		MapSO o = new MapSO();
		if (StringEmpty.is(s) || s.contentEquals("{}")) {
			return o;
		}
		
		s = s.trim();

		if (!s.startsWith("{") || !s.endsWith("}")) {
			throw new RuntimeException("deve iniciar com '{' e terminar com '}': " + s);
		}
//		
//		HashMap<String, Object> map = fromGson(s);
//		
//		if (map != null) {
//			return new MapSO(map);
//		}

		s = StringRight.ignore1(s.substring(1)).trim();

		s = s.replace("\\\"", "$ASPA$");

		while (StringContains.is(s, MapSoFromJson.ASPA)) {
			String before = StringBeforeFirst.get(s, MapSoFromJson.ASPA);
			s = StringAfterFirst.get(s, MapSoFromJson.ASPA);
			String conteudo = StringBeforeFirst.get(s, MapSoFromJson.ASPA);
			s = StringAfterFirst.get(s, MapSoFromJson.ASPA);
			s = before + MapSoFromJson.$STRING + IntegerFormat.zerosEsquerda(strings.size(),4)+ s;
			conteudo = conteudo.replace("\n", MapSoFromJson.$QUEBRA);
			conteudo = conteudo.replace("$ASPA$", MapSoFromJson.ASPA);
			strings.add(conteudo);
		}
		
		s = s.replace("$ASPA$", "'");
		s = StringTrim.plus(s);
		s = s.replace("", "");
		
		for (int i = 0; i < strings.size(); i++) {
			
			while (true) {
				int len = s.length();
				String ss = $STRING + IntegerFormat.xxxx(i);
				s = s.replace(" " + ss, ss);
				s = s.replace(ss + " ", ss);
				if (len == s.length()) {
					break;
				}
			}
			
		}
		
		s = s.replace(" ", "");

		while (StringContains.is(s, "{") || StringContains.is(s, "[")) {

			boolean array;

			if (StringContains.is(s, "[")) {
				if (StringContains.is(s, "{")) {
					array = s.lastIndexOf("[") > s.lastIndexOf("{");
				} else {
					array = true;
				}
			} else {
				array = false;
			}

			if (array) {
				String before = StringBeforeLast.obrig(s, "[");
				s = StringAfterLast.get(s, "[");
				String conteudo = StringBeforeFirst.get(s, "]");
				s = StringAfterFirst.get(s, "]");
				s = before + MapSoFromJson.$ARRAY + IntegerFormat.zerosEsquerda(arrays.size(),4) + s;
				arrays.add(conteudo);
			} else {
//				String x = s;
				String before = StringBeforeLast.obrig(s, "{");
				s = StringAfterLast.obrig(s, "{");
				String conteudo = StringBeforeFirst.get(s, "}");
				if (conteudo == null) {
					conteudo = "";
				}
				s = StringAfterFirst.get(s, "}");
				s = before + MapSoFromJson.$OBJ + IntegerFormat.zerosEsquerda(objetos.size(),4) + s;
				objetos.add(conteudo);
			}

		}

		s += ",";

		s = s.replace(",  ,", ",");

		while (!StringEmpty.is(s)) {
			String key = getReal(StringBeforeFirst.get(s, ":"));
			s = StringAfterFirst.get(s, ":").trim();
			String value = StringBeforeFirst.get(s, ",").trim();
			
			while (value.endsWith($QUEBRA)) {
				value = StringRight.ignore(value, $QUEBRA.length());
			}
			
			if (StringEmpty.is(getReal(value))) {
				o.add(key, null);
			} else if ("[]".equals(value)) {
				o.add(key, new ArrayList<>());
			} else if (value.startsWith("[")) {
				throw new RuntimeException("nao tratado");
			} else if (value.startsWith(MapSoFromJson.$ARRAY)) {
				String x = StringAfterFirst.get(value, MapSoFromJson.$ARRAY);
				value = arrays.get(IntegerParse.toInt(x));
				if (StringEmpty.is(value)) {
					o.add(key, new ArrayList<>());
				} else {
					ListString itens = ListString.split(value, ",");
					if (itens.get(0).startsWith(MapSoFromJson.$OBJ)) {
						List<MapSO> lst = new ArrayList<>();
						for (String ss : itens) {
							ss = getRealObj(ss);
							lst.add(exec("{"+ss+"}"));
						}
						o.add(key, lst);
					} else if (itens.get(0).startsWith(MapSoFromJson.$STRING)) {
						ListString lst = new ListString();
						for (String ss : itens) {
							lst.add(getReal(ss));
						}
						o.add(key, lst);
					} else {
						o.add(key, itens);
					}
				}
			} else if (value.startsWith(MapSoFromJson.$OBJ)) {
				String real = getRealObj(value);
				o.add(key, exec("{"+real+"}"));
			} else {
				if (value.startsWith(MapSoFromJson.$STRING)) {
					value = getReal(value);
				}
				o.add(key, value);
			}
			s = StringAfterFirst.get(s, ",").trim();
		}

		return o;
	}

	private String getRealObj(String s) {
		s = StringAfterFirst.get(s, MapSoFromJson.$OBJ);
		return objetos.get(IntegerParse.toInt(s));
	}
	private String getReal(String s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		s = s.trim();
		if (s.contentEquals("null")) {
			return null;
		}
		if (s.startsWith(MapSoFromJson.$STRING)) {
			s = StringAfterFirst.get(s, MapSoFromJson.$STRING);
			return strings.get(IntegerParse.toInt(s)).replace(MapSoFromJson.$QUEBRA, "\n");
		}
		return s;
	}

	public static MapSO get(String s) {
		try {
			return new MapSoFromJson(s).result;
		} catch (Exception e) {
			console.log(s);
			new MapSoFromJson(s);
			throw e;
		}
	}

	public static void main(String[] args) {
		
		get("{ \"react_react\": \"C:\\opt\\desen\\gm\\cs2019\\reacts\\react\" }");
		
//		MapSO dados = null;
//		Map<String, String> headers = new HashMap<>();
//		URest.get("https://conciliacao-b3.cloud.homol.cooperforte.coop/cpf-para-teste-associacao", headers, dados ).print();

		
//		MapSoFromJson.get("{\"propostas\":{\"numProposta\":3762991,\"contratoNegocio\":\"000096.01.00.0167\",\"prazo\":60,\"inicioAplicacao\":\"18/08/2014\",\"carencia\":\"18/09/2014\",\"tipoInvestimento\":\"RDC\",\"descricao\":null,\"produtoInicio\":{\"id\":null,\"fimAplicacao\":\"18/08/2019\",\"valorAplicado\":30000.0000,\"valorRendimentosCreditados\":2735.4200,\"valorIRsRecolhidos\":410.3100,\"valorResgatesRealizados\":7765.0000,\"valorSaldo\":24560.1100,\"valorRendimentosBrutoACreditar\":15505.6700,\"valorPrevisaoIr\":2325.8500,\"valorSaldoComRendimentoLiquido\":37739.9300,\"aliquota\":0.15,\"finalizado\":false,\"bloqueioJudicialTO\":{\"valorBloqueio\":0.0000,\"dataBloqueio\":null,\"valorDispBloqueio\":37739.9300,\"valorBloqueioFormatado\":\"0,00\",\"valorDispBloqueioFormatado\":\"37.739,93\"},\"valorBloqueadoCreditoGarantido\":0.0000},\"produtoFim\":{\"id\":null,\"fimAplicacao\":\"18/08/2019\",\"valorAplicado\":30000.0000,\"valorRendimentosCreditados\":2735.4200,\"valorIRsRecolhidos\":410.3100,\"valorResgatesRealizados\":7765.0000,\"valorSaldo\":24560.1100,\"valorRendimentosBrutoACreditar\":15721.8200,\"valorPrevisaoIr\":2358.2700,\"valorSaldoComRendimentoLiquido\":37923.6600,\"aliquota\":0.15,\"finalizado\":false,\"bloqueioJudicialTO\":{\"valorBloqueio\":0.0000,\"dataBloqueio\":null,\"valorDispBloqueio\":37923.6600,\"valorBloqueioFormatado\":\"0,00\",\"valorDispBloqueioFormatado\":\"37.923,66\"},\"valorBloqueadoCreditoGarantido\":0.0000},\"periodos\":[],\"dap\":false},\"saldoTotalInvestimentos\":0}").print();
	}

}
