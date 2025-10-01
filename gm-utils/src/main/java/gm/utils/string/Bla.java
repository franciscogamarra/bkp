package gm.utils.string;

import gm.utils.date.Data;
import gm.utils.number.Numeric2;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

public class Bla {
	
	private static boolean testando = false;
	
	private static final ListString delete = new ListString();
	static {
		delete.add("delete from funcafe_mapa.w_guia_recolhimento_uniao where id_gru in (");
	}
	
	private static final ListString script = new ListString();

	public static void main(String[] args) {
		ListString list = new ListString();
		list.load("C:\\temp\\temp0.txt");
		for (String s : list) {
			exec(s);
		}
		
		if (testando) {
			String s = delete.removeLast();
			s = StringRight.ignore1(s);
			delete.add(s);
			delete.add(")");
			delete.print();
		}
		
		script.saveTemp();
		
	}

	private static void exec(String s) {

		s = StringTrim.plus(s);
		
		ListString list = ListString.byDelimiter(s, " ");
		
		String idGru = list.remove(0).replace("/", "");
		
		if (testando) {
			delete.add(idGru + ",");
			return;
		}
		
		String ugArrecadadora = list.remove(0);
		String codigoRecolhimento = list.remove(0);
		Data dataPagamento = Data.unformat("[m]/[d]/[yyyy]", list.remove(0));
		String contribuinte = list.remove(0);
		String numeroReferencia = list.remove(0);
		Numeric2 valorPrincipal = new Numeric2(list.remove(0).replace(".", "").replace(",", "."));
		Numeric2 valorMoraMulta = new Numeric2(list.remove(0).replace(".", "").replace(",", "."));
		Numeric2 valorJurosEncargos = new Numeric2(list.remove(0).replace(".", "").replace(",", "."));
		Numeric2 valorTotal = new Numeric2(list.remove(0).replace(".", "").replace(",", "."));
		String documentoOrigem = list.remove(0);
		String raContabilizacao = list.remove(0);
		
		list.clear();
		
		list.add("insert into funcafe_mapa.w_guia_recolhimento_uniao (");
		list.add("  id_gru");
		list.add(", cd_num_referencia");
		list.add(", cd_agente_arrecadador");
		list.add(", cd_num_aut_bancaria");
		list.add(", cd_competencia");
		list.add(", cd_recolhedor");
		list.add(", nr_recolhimento");
		list.add(", nr_recolhimento_contabilizado");
		list.add(", dh_criacao_sisgru");
		list.add(", dt_emissao");
		list.add(", dh_contabilizacao_siafi");
		list.add(", dh_retificacao");
		list.add(", dt_transferencia");
		list.add(", dt_vencimento");
		list.add(", cd_gr_origem");
		list.add(", cd_especie_gr");
		list.add(", cd_especie_ingresso");
		list.add(", cd_gru_retificada");
		list.add(", cd_meio_pagamento");
		list.add(", ds_observacao");
		list.add(", cd_origem_arrecadacao");
		list.add(", ds_processo");
		list.add(", cd_num_ravinculada");
		list.add(", cd_numero_ra");
		list.add(", cd_situacao");
		list.add(", cd_tipo_recolhedor");
		list.add(", cd_tipo_registro_gru");
		list.add(", cd_ug_arrecadadora");
		list.add(", cd_ug_emitente");
		list.add(", vl_principal");
		list.add(", vl_total");
		list.add(", vl_desconto");
		list.add(", vl_juros");
		list.add(", vl_multa");
		list.add(", vl_outra_deducao");
		list.add(", vl_acrescimo");		
		list.add(")");
		list.add("select");
		list.add("'" + idGru + "' as id_gru,");
		list.add("'" + numeroReferencia + "' as cd_num_referencia,");
		list.add("'009' as cd_agente_arrecadador,");
		list.add("null as cd_num_aut_bancaria,");
		list.add("null as cd_competencia,");
		list.add("'" + contribuinte + "' as cd_recolhedor,");
		list.add(codigoRecolhimento + " as nr_recolhimento,");
		list.add(codigoRecolhimento + " as nr_recolhimento_contabilizado,");
		
		Data criacao = dataPagamento.copy();
		criacao.setHora(4);
		criacao.setMinuto(33);
		list.add(criacao.toSqlOracle() + " as dh_criacao_sisgru,");
		list.add(dataPagamento.toSqlOracle() + " as dt_emissao,");
		
		Data contabilizacaoSiafi = dataPagamento.copy();
		contabilizacaoSiafi.setHora(23);
		contabilizacaoSiafi.setMinuto(5);
		list.add(contabilizacaoSiafi.toSqlOracle() + " as dh_contabilizacao_siafi,");
		list.add("null as dh_retificacao,");
		list.add("null as dt_transferencia,");
		list.add(dataPagamento.toSqlOracle() + " as dt_vencimento,");
		list.add("'" + documentoOrigem + "' as cd_gr_origem,");
		list.add("'2' as cd_especie_gr,");
		list.add("'4' as cd_especie_ingresso,");
		list.add("null as cd_gru_retificada,");
		list.add("'00' as cd_meio_pagamento,");
		list.add("'REGISTRO DA CLASSIFICACAO DA ARRECADACAO DE GUIAS DE RECOLHIMENTO             DA UNIAO DO DIA: "+dataPagamento.format("[dd][mmm][yy]")+"' as ds_observacao,");
		list.add("'4' as cd_origem_arrecadacao,");
		list.add("null as ds_processo,");
		list.add("null as cd_num_ravinculada,");
		list.add("'" + raContabilizacao + "' as cd_numero_ra,");
		list.add("'02' as cd_situacao,");
		list.add("'2' as cd_tipo_recolhedor,");
		list.add("'1' as cd_tipo_registro_gru,");
		list.add("'" + ugArrecadadora + "' as cd_ug_arrecadadora,");
		list.add("'" + ugArrecadadora + "' as cd_ug_emitente,");
		list.add(valorPrincipal.toStringPonto() + " as vl_principal,");
		list.add(valorTotal.toStringPonto() + " as vl_total,");
		list.add("0.00 as vl_desconto,");
		list.add(valorJurosEncargos.toStringPonto() + " as vl_juros,");
		list.add(valorMoraMulta.toStringPonto() + " as vl_multa,");
		list.add("0.00 as vl_outra_deducao,");
		list.add("0.00 as vl_acrescimo");
		list.add("from funcafe_mapa.w_guia_recolhimento_uniao");
		list.add("where cd_num_referencia = 00016501036800302021");
		list.add(";");
		
//		list.filter(i -> i.contains(" as ")).replaceEach(i -> StringAfterFirst.get(i, " as ")).print();

//		StringClipboard.set(list.toString("\n"));
		
//		list.print();
		
		script.addAll(list);
		script.add();
		script.add("/* ======================================================== */");
		script.add();
		
	}
	
}