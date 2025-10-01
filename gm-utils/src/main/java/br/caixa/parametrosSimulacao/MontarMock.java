package br.caixa.parametrosSimulacao;

import java.util.List;

import br.caixa.loterias.utils.anotacoes.IgnoreCoverageGenerated;
import br.caixa.parametrosSimulacao.dtos.MockParametroSimulacaoMain;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UType;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.tempo.DataHora;

public class MontarMock {
	
	private static JcClasse jc;
	private static JcMetodo metodoAtual;
	private static int linhas = 0;
	private static int metodo = 0;

	public static void main(String[] args) {
		exec(MockParametroSimulacaoMain.class);
//		exec(MockConsultaComboMain.class);
//		exec(MockPartidaLotecaMain.class);
//		exec(MockPgInicialMain.class);
//		exec(PartidaLotecaDto.class, 
		
	}

	private static Class<?> mockMainClass;
	
	public static void exec(Class<?> mockMain) {
		
		Class<?> principal = AtributosBuild.get(mockMain).getObrig("principal").getType();
				
		mockMainClass = mockMain;
		
		ListString list = new ListString();
		list.load("C:\\desenvolvimento\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\java\\br\\caixa\\parametrosSimulacao\\mock.json");
//		list.load("C:\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\java\\br\\caixa\\parametrosSimulacao\\mock.json");
		MapSO map = MapSoFromJson.get(list.toString(""));
		
		DataHora now = DataHora.now();
		
		jc = JcClasse.build(mockMain.getPackage(), StringBeforeLast.get(mockMain.getSimpleName(), "Main") + now.format("[yyyy][mm][dd][hh][nn]"));
		jc.addImport(DataHora.class);
		jc.addAnnotation(IgnoreCoverageGenerated.class);
		jc.atributo("o", mockMain).static_().public_().setInicializacao("new " + mockMain.getSimpleName() + "(DataHora.build("+now.format("[yyyy],[m],[d],[h],[n]")+"))");
		jc.staticPart().add("exec0();");
		metodoAtual = jc.metodo("exec0").static_();
		exec("o", "principal", map, principal);
		jc.save();
	}
	
	private static void exec(String keyy, String nomeCampo, MapSO map, Class<?> type) {
		String key = keyy + "." + nomeCampo;
		add(key + " = new " + type.getSimpleName() + "();");
		execc(key, map);
	}
	
	private static void add(String s) {
		linhas++;
		if (linhas > 1000) {
			metodo++;
			JcMetodo m = jc.metodo("exec" + metodo).static_();
			metodoAtual.add("exec"+metodo+"();");
			metodoAtual = m;
			linhas = 0;
		}
		metodoAtual.add(s);
	}
	
	private static void execc(String key, MapSO map) {

		map.forEach((k,v) -> {
			
			if (v == null) {
				return;
			}

			if (v instanceof String && StringEmpty.is((String) v)) {
				return;
			}
			
			String ss = key + "." + k;
			
//			if (ss.contentEquals("o.principal.payload.getLast().modalidadesCombo.getLast().modalidade.valor")) {
//				SystemPrint.ln(k);
//			}
//			
			Atributo a = getAtributo(ss);
			
			ss += " = ";
			
			if (a.isPrimitivo()) {
				add(ss + getValorPrimitivo(a, v) + ";");
				return;
			}
			
			if (v instanceof MapSO) {
				exec(key, k, (MapSO) v, a.getType());
				return;
			}
			
			if (v instanceof List) {
				
				List<?> list = (List<?>) v;
				
				add(ss + "new Lst<>();");
				jc.addImport(Lst.class);
				
				if (list.isEmpty()) {
					return;
				}
				
				Class<?> type = a.getTypeOfList();
				
				if (UType.isPrimitiva(type)) {

					for (Object o : list) {
						add(key + "." + k + ".add("+getValorPrimitivo(a, o)+");");
					}
					
				} else {

					String sss = key + "." + k + ".getLast()";
					String newItem = key + "." + k + ".add(new "+type.getSimpleName()+"());";
					
					for (Object o : list) {
						add(newItem);
						MapSO oo = (MapSO) o;
						execc(sss, oo);
					}
					
				}
				
			} else {
				
			}
			
		});
		
	}
	
	private static Atributo getAtributo(final String sss) {
		
		ListString list = ListString.byDelimiter(sss, ".");
		list.remove(0);
		list.removeIf(ss -> ss.contentEquals("getLast()"));
		
		if (sss.endsWith(".quantidadeSurpresinhas")) {
			SystemPrint.ln(sss);
		}
		
		Class<?> classe = mockMainClass;
		
		Atributo a = null;
		
		while (!list.isEmpty()) {
			
			String s = list.remove(0);
			
			a = AtributosBuild.get(classe).getObrig(s);
			
			if (a.isList()) {
				classe = a.getTypeOfList();
			} else {
				classe = a.getType();
			}
			
		}
		
		return a;
		
	}

	private static String getValorPrimitivo(Atributo a, Object v) {
		if (a.isString()) {
			return "\"" + v + "\"";
		}
		
		String s = v.toString();
		
		if (a.is(Double.class)) {
			
			if (!s.contains(".")) {
				return s + ".";
			}
			
		}
		
		return s;
	}
	
}
