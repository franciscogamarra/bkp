package src.commom.utils.string;

public class StringColocarAcentos {

	private StringColocarAcentos() {}

	public static String exec(String s) {

		if (StringEmpty.is(s)) {
			return s;
		}

		s = " " + s + " ";
		s = s.replace("ario ", StringConstants.a_agudo + "rio ");
		s = s.replace("Hodometro ", "Hod" + StringConstants.o_circunflexo + "metro ");
		s = s.replace("Codigo ", "C" + StringConstants.o_agudo + "digo ");
		s = s.replace(" Debito ", " D" + StringConstants.e_agudo + "bito ");
		s = s.replace("Matricula ", "Matr" + StringConstants.i_agudo + "cula ");
		s = s.replace("Pratica ", "Pr" + StringConstants.a_agudo + "tica ");
		s = s.replace("Numero ", "N" + StringConstants.u_agudo + "mero ");
		s = s.replace("Pais ", "Pa" + StringConstants.i_agudo + "s ");
		s = s.replace("Veiculo ", "Ve" + StringConstants.i_agudo + "culo ");
		s = s.replace("Uf ", "UF ");
		s = s.replace(" U F ", " UF ");
		s = s.replace(" A ", " a ");
		s = s.replace(" O ", " o ");
		s = s.replace(" As ", " as ");
		s = s.replace(" Aos ", " aos ");
		s = s.replace(" Os ", " os ");
		s = s.replace(" Da ", " da ");
		s = s.replace(" Do ", " do ");
		s = s.replace(" Das ", " das ");
		s = s.replace(" Dos ", " dos ");
		s = s.replace(" Que ", " que ");
		s = s.replace("cao ", StringConstants.cedilha + StringConstants.a_til + "o ");
		s = s.replace(" Orgao ", " " + StringConstants.O_agudo + "rg" + StringConstants.a_til + "o ");
		s = s.replace(" orgao ", " " + StringConstants.o_agudo + "rg" + StringConstants.a_til + "o ");
		s = s.replace("ao ", StringConstants.a_til + "o ");
		s = s.replace("oes ", StringConstants.o_til + "es ");
		s = s.replace("iao ", "i" + StringConstants.a_til + "o ");
		s = s.replace("sao ", "s" + StringConstants.a_til + "o ");
		s = s.replace("aria ", StringConstants.a_agudo + "ria ");
		s = s.replace("aveis ", StringConstants.a_agudo + "veis ");
		s = s.replace("ario ", StringConstants.a_agudo + "rio ");
		s = s.replace("icio ", StringConstants.i_agudo + "cio ");
		s = s.replace("ivel ", StringConstants.i_agudo + "vel ");
		s = s.replace("avel ", StringConstants.a_agudo + "vel ");
		s = s.replace("ipio ", StringConstants.i_agudo + "pio ");
		s = s.replace("encia ", StringConstants.e_circunflexo + "ncia ");
		s = s.replace("ogico ", StringConstants.o_agudo + "gico ");
		s = s.replace("imetro", StringConstants.i_agudo + "metro");

		//https://www.rhymit.com/pt/palavras-terminadas-em-%C3%AAnio
		s = s.replace("enio ", "ênio ");

		s = s.replace(" Em ", " em ");
		s = s.replace(" Ro ", " RO ");
		s = s.replace(" Area ", " " + StringConstants.a_agudo + "rea ");
		s = s.replace(" Agua ", " " + StringConstants.a_agudo + "gua ");
		s = s.replace(" Ultimo", " " + StringConstants.U_agudo + "ltimo");
		s = s.replace(" Unico", " " + StringConstants.U_agudo + "nico");
		s = s.replace(" Uteis ", " " + StringConstants.U_agudo + "teis ");
		s = s.replace(" Util ", " " + StringConstants.U_agudo + "til ");
		s = s.replace("Credito ", "Cr" + StringConstants.e_agudo + "dito ");
		s = s.replace(" Mae ", " M" + StringConstants.a_til + "e ");
		s = s.replace(" Ja ", " j" + StringConstants.a_agudo + " ");
		s = s.replace(" Ficara ", " Ficar" + StringConstants.a_agudo + " ");
		s = s.replace(" Valido ", " V" + StringConstants.a_agudo + "lido ");
		s = s.replace(" Seguranca ", " Seguran" + StringConstants.cedilha + "a ");
		s = s.replace(" Eh ", " " + StringConstants.E_agudo + " ");
		s = s.replace(" Pre ", " Pr" + StringConstants.e_agudo + "-");
		s = s.replace(" Nao ", " N" + StringConstants.a_til + "o ");
		s = s.replace(" Media ", " M" + StringConstants.e_agudo + "dia ");
		s = s.replace(" Acrescimo", " Acr" + StringConstants.e_agudo + "scimo");
		s = s.replace(" Endereco ", " Endere" + StringConstants.cedilha + "o ");
		s = s.replace(" Cobranca ", " Cobran" + StringConstants.cedilha + "a ");
		s = s.replace(" Sabado", " S" + StringConstants.a_agudo + "bado");
		s = s.replace("emico ", StringConstants.e_circunflexo + "mico ");
		s = s.replace(" Maxim", " M" + StringConstants.a_agudo + "xim");
		s = s.replace(" Minim", " M" + StringConstants.i_agudo + "nim");
		s = s.replace(" Apos ", " Ap" + StringConstants.o_agudo + "s ");
		s = s.replace(" Serie ", " S" + StringConstants.e_agudo + "rie ");
		s = s.replace("onteudo ", "onte" + StringConstants.u_agudo + "do ");
		s = s.replace("orio ", StringConstants.o_agudo + "rio ");
		s = s.replace("cluido ", "clu" + StringConstants.i_agudo + "do ");
		s = s.replace(" Email ", " E-mail ");
		s = s.replace(" Cnpj ", " CNPJ ");
		s = s.replace(" Cpj ", " CPF ");
		s = s.replace(" Cdi ", " CDI ");
		s = s.replace(" Tr ", " TR ");
		s = s.replace(" Selic ", " SELIC ");
		s = s.replace(" Hsbc ", " HSBC ");
		s = s.replace(" mes ", " m" + StringConstants.e_circunflexo + "s ");
		s = s.replace(" Mes ", " M" + StringConstants.e_circunflexo + "s ");
		s = s.replace(" Primeiro ", " 1" + StringConstants.o_primeiro + " ");
		s = s.replace(" Segundo ", " 2" + StringConstants.o_primeiro + " ");
		s = s.replace(" Terceiro ", " 3" + StringConstants.o_primeiro + " ");
		s = s.replace(" Primeira ", " 1" + StringConstants.a_primeira + " ");
		s = s.replace(" Segunda ", " 2" + StringConstants.a_primeira + " ");
		s = s.replace(" Terceira ", " 3" + StringConstants.a_primeira + " ");
		s = s.replace(" Indição ", " Indicação ");
		s = s.replace(" Vinculo ", " Vínculo ");

		return StringRight.ignore1(s.substring(1));

	}

}
