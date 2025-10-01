package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class StringConstants {

	private StringConstants() {}

	public static final String a_primeira = "\u00aa";
	public static final String o_primeiro = "\u00ba";

	public static final String a_agudo = "\u00e1";
	public static final String A_agudo = "\u00c1";

	public static final String a_crase = "\u00e0";
	public static final String A_crase = "\u00c0";

	public static final String a_circunflexo = "\u00e2";
	public static final String A_circunflexo = "\u00c2";

	public static final String a_til = "\u00e3";
	public static final String A_til = "\u00c3";

	public static final String a_trema = "\u00e4";
	public static final String A_trema = "\u00c4";

	public static final String cifrao = "\u0024";

	public static final String e_agudo = "\u00e9";
	public static final String E_agudo = "\u00c9";

	public static final String e_circunflexo = "\u00ea";
	public static final String E_circunflexo = "\u00ca";

	public static final String e_crase = "\u00e8";
	public static final String E_crase = "\u00c8";

	public static final String e_trema = "\u00eb";
	public static final String E_trema = "\u00cb";

	public static final String e_til = "\u1ebd";
	public static final String E_til = "\u1ebc";

	public static final String i_agudo = "\u00ed";
	public static final String I_agudo = "\u00cd";

	public static final String i_crase = "\u00ec";
	public static final String I_crase = "\u00cc";

	public static final String i_circunflexo = "\u00ee";
	public static final String I_circunflexo = "\u00ce";

	public static final String i_trema = "\u00ef";
	public static final String I_trema = "\u00cf";

	public static final String i_til = "\u0129";
	public static final String I_til = "\u0128";

	public static final String o_agudo = "\u00f3";
	public static final String O_agudo = "\u00d3";

	public static final String o_crase = "\u00f2";
	public static final String O_crase = "\u00d2";

	public static final String o_circunflexo = "\u00f4";
	public static final String O_circunflexo = "\u00d4";

	public static final String o_til = "\u00f5";
	public static final String O_til = "\u00d5";

	public static final String o_trema = "\u00f6";
	public static final String O_trema = "\u00d6";

	public static final String u_agudo = "\u00fa";
	public static final String U_agudo = "\u00da";

	public static final String u_crase = "\u00f9";
	public static final String U_crase = "\u00d9";

	public static final String u_til = "\u0169";
	public static final String U_til = "\u0168";

	public static final String u_circunflexo = "\u00fb";
	public static final String U_circunflexo = "\u00db";

	public static final String u_trema = "\u00fc";
	public static final String U_trema = "\u00dc";

	public static final String cedilha = "\u00e7";
	public static final String CEDILHA = "\u00c7";

	public static final String n_til = "\u00f1";
	public static final String N_til = "\u00d1";

	public static final String e_comercial = "\u0026";
	public static final String aspa_simples = "\u0027";
	public static final String aspas_duplas = "\"";
	public static final String double_s = "\u00a7";

	public static final String maior_maior = "\u00BB";
	public static final String menor_menor = "\u00AB";

	public static final String x_fechar = "\u00D7";

	public static final String cao = cedilha + a_til + "o";

	public static final Itens<String> NUMEROS = Itens.buildString("0","1","2","3","4","5","6","7","8","9");

	public static final Itens<String> MINUSCULAS_ESPECIAIS = Itens.buildString(
		  StringConstants.a_agudo, StringConstants.a_crase, StringConstants.a_circunflexo, StringConstants.a_til, StringConstants.a_trema
		, StringConstants.e_agudo, StringConstants.e_crase, StringConstants.e_circunflexo, StringConstants.e_til, StringConstants.e_trema
		, StringConstants.i_agudo, StringConstants.i_crase, StringConstants.i_circunflexo, StringConstants.i_til, StringConstants.i_trema
		, StringConstants.o_agudo, StringConstants.o_crase, StringConstants.o_circunflexo, StringConstants.o_til, StringConstants.o_trema
		, StringConstants.u_agudo, StringConstants.u_crase, StringConstants.u_circunflexo, StringConstants.u_til, StringConstants.u_trema
		, StringConstants.n_til, StringConstants.cedilha
	);

	public static final Itens<String> MAIUSCULAS_ESPECIAIS = MINUSCULAS_ESPECIAIS.map(s -> s.toUpperCase());

	public static final Itens<String> ESPECIAIS = MINUSCULAS_ESPECIAIS.concat(MAIUSCULAS_ESPECIAIS);

	public static final Itens<String> MINUSCULAS_SEM_ACENTOS = Itens.buildString(
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
	);
	
	public static final Itens<String> MINUSCULAS = MINUSCULAS_SEM_ACENTOS.concat(MINUSCULAS_ESPECIAIS);

	public static final Itens<String> MAIUSCULAS_SEM_ACENTOS = MINUSCULAS_SEM_ACENTOS.map(s -> s.toUpperCase());
	
	public static final Itens<String> MAIUSCULAS = MINUSCULAS.map(s -> s.toUpperCase());
	
	public static final Itens<String> LETRAS_E_NUMEROS = MINUSCULAS.concat(MAIUSCULAS).concat(NUMEROS);
	public static final Itens<String> FILE_LETTERS = LETRAS_E_NUMEROS.copy().add("_").add("-").add(".").add("/");
	public static final Itens<String> JAVA_LETTERS = LETRAS_E_NUMEROS.copy().add("_").removeAll(ESPECIAIS);

	public static final Itens<String> PREPOSICOES_NOME_PROPRIO = Itens.buildString(
		"de", "da", "do", "das", "dos"
	);

	public static final Itens<String> PREPOSICOES = Itens.buildString(
		"a", "ante", "ap"+o_agudo+"s", "at"+e_agudo, "com", "contra",
		"de", "desde", "em", "entre", "para", "por", "perante", "sem",
		"sob", "sobre", "tr"+a_agudo+"z", "do", "da", "dos", "das",
		"dum", "duma", "duns", "dumas", "no", "na", "nos", "nas",
		"afora", "ao", "durante", "exceto", "mediante",
		"menos", "salvo", "segundo", "conforme"
	);

	public static final Itens<String> ARTIGOS = Itens.buildString(
		"a", "o", "as", "os", "um", "uma", "uns", "umas"
	);

	public static final Itens<String> SIMBOLOS = Itens.buildString(
		" ",".",",",";",":","(",")","<",">"
		,"{", "}","[","]","!","@","#","$","%"
		,"&","*","-","_","=","+","/","?"
		,"|","\"","'","`","^","~","\n","\t"
	);

}
