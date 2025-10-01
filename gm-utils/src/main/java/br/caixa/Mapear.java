package br.caixa;

import jakarta.persistence.Column;

import gm.utils.exception.NaoImplementadoException;
import gm.utils.javaCreate.JcAnotacao;
import gm.utils.javaCreate.JcAtributo;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcTipo;
import gm.utils.number.Numeric2;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.tempo.DataHora;

public class Mapear {

	public static void main(String[] args) {
		
//		validateSetSituacaoJogo(rs.getString("CO_SITUACAO_JOGO").charAt(0));
		
		ListString list = new ListString();
		list.add("//        vJogo.setQuantidadeMinimaPalpites(rs.getInt(\"QT_MINIMA_PALPITE\"));");
		list.add("//        vJogo.setQuantidadeMaximaPalpites(rs.getInt(\"QT_MAXIMA_PALPITE\"));");
		list.add("//        vJogo.setQuantidadePremio(rs.getInt(\"QT_PREMIO\"));");
		list.add("//        vJogo.setQuantidadeSorteio(rs.getInt(\"QT_SORTEIO\"));");
		list.add("//        vJogo.setQuantidadeMaximaSurpresinha(rs.getInt(\"QT_MAXIMA_SRPRA\"));");
		list.add("//        vJogo.setQuantidadeApostasBilhete(rs.getInt(\"QT_APOSTA_BILHETE\"));");
		list.add("//        vJogo.setAceitaEspelho(rs.getBoolean(\"IC_ACEITA_ESPELHO\"));");
		list.add("//        vJogo.setAceitaComplemento(rs.getBoolean(\"IC_ACEITA_CMPMO\"));");
		list.add("//        vJogo.setQuantidadeMaximaAcerto(rs.getInt(\"QT_MAXIMA_ACERTO\"));");
		list.add("//        vJogo.setValorApostaMinima(rs.getDouble(\"VR_APOSTA_MINIMA\"));");
		list.add("//        vJogo.setNome(rs.getString(\"DE_VERSAO\"));");
		list.add("//        vJogo.setvalidateSetSituacaoJogo(rs.getString(\"CO_SITUACAO_JOGO\").charAt(0));");
		list.add("//        vJogo.setCodigoVersao(rs.getInt(\"NU_VERSAO\"));");
		list.add("//        vJogo.setData(rs.getTimestamp(\"DT_VERSAO\"));");
		list.add("//        vJogo.setQuantidadeConcursoHistorico(rs.getInt(\"QT_CONCURSO_HSTRO\"));");
		list.add("//        vJogo.setIndicadorSuspensao(rs.getInt(\"IC_SUSPENSAO\"));");
		list.add("//        vJogo.setValorApostaUnitaria(rs.getDouble(\"VR_APOSTA_UNITARIA\"));");
		list.add("//        vJogo.setNomeJogo(rs.getString(\"NO_ABREVIADO_JOGO\"));");
		list.add("//        vJogo.setTipoMatricula(rs.getString(\"IC_TIPO_MATRICULA\"));");
		list.add("//        vJogo.setCodigoMatricula(rs.getInt(\"NU_MATRICULA\"));");
		list.add("//        vJogo.setUltimaAtualizacao(rs.getTimestamp(\"TS_ULTIMA_ATLZO\"));");
		
		list.replaceEach(s -> StringAfterFirst.get(s, ".set"));
		
		JcClasse jc = JcClasse.build(Mapear.class);
		
		for (String s : list) {
			String campo = StringPrimeiraMinuscula.exec(StringBeforeFirst.get(s, "("));
			s = StringAfterFirst.get(s, "(rs.get");
			String tipoString = StringBeforeFirst.get(s, "(").toLowerCase();
			s = StringAfterFirst.get(s, "(\"");
			String coluna = StringBeforeFirst.get(s, "\"").toLowerCase();
			Class<?> tipo = getTipo(tipoString);
			JcAtributo atributo = jc.atributo(campo, tipo);
			JcAnotacao column = new JcAnotacao(Column.class);
			column.addParametro("name", "\"" + coluna + "\"");
			//			JcAnotacao.
			atributo.addAnotacao(column);
			
			if (tipo == DataHora.class) {
				JcTipo converter = new JcTipo("br.caixa.loterias.util.tipos.converters.JpaConverterDataHora");
				atributo.addConverter(converter);
			} else if (tipo == Numeric2.class) {
				JcTipo converter = new JcTipo("br.caixa.loterias.util.tipos.converters.JpaConverterNumeric2");
				atributo.addConverter(converter);
			}

		}
		
		jc.setFormal(true);
		jc.print();
		
	}

	private static Class<?> getTipo(String tipo) {
		
		if (tipo.contentEquals("string")) {
			return String.class;
		}
		
		if (tipo.contentEquals("int")) {
			return Integer.class;
		}

		if (tipo.contentEquals("double")) {
			return Numeric2.class;
		}

		if (tipo.contentEquals("timestamp")) {
			return DataHora.class;
		}
		
		if (tipo.contentEquals("boolean")) {
			return Boolean.class;
		}
		

		throw new NaoImplementadoException(tipo);
		
	}
	
}
