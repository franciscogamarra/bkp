import ArrayLst from '../array/ArrayLst';

export default class StringConstants {}
StringConstants.a_primeira = "\u00aa";
StringConstants.o_primeiro = "\u00ba";
StringConstants.a_agudo = "\u00e1";
StringConstants.A_agudo = "\u00c1";
StringConstants.a_crase = "\u00e0";
StringConstants.A_crase = "\u00c0";
StringConstants.a_circunflexo = "\u00e2";
StringConstants.A_circunflexo = "\u00c2";
StringConstants.a_til = "\u00e3";
StringConstants.A_til = "\u00c3";
StringConstants.a_trema = "\u00e4";
StringConstants.A_trema = "\u00c4";
StringConstants.cifrao = "\u0024";
StringConstants.e_agudo = "\u00e9";
StringConstants.E_agudo = "\u00c9";
StringConstants.e_circunflexo = "\u00ea";
StringConstants.E_circunflexo = "\u00ca";
StringConstants.e_crase = "\u00e8";
StringConstants.E_crase = "\u00c8";
StringConstants.e_trema = "\u00eb";
StringConstants.E_trema = "\u00cb";
StringConstants.e_til = "\u1ebd";
StringConstants.E_til = "\u1ebc";
StringConstants.i_agudo = "\u00ed";
StringConstants.I_agudo = "\u00cd";
StringConstants.i_crase = "\u00ec";
StringConstants.I_crase = "\u00cc";
StringConstants.i_circunflexo = "\u00ee";
StringConstants.I_circunflexo = "\u00ce";
StringConstants.i_trema = "\u00ef";
StringConstants.I_trema = "\u00cf";
StringConstants.i_til = "\u0129";
StringConstants.I_til = "\u0128";
StringConstants.o_agudo = "\u00f3";
StringConstants.O_agudo = "\u00d3";
StringConstants.o_crase = "\u00f2";
StringConstants.O_crase = "\u00d2";
StringConstants.o_circunflexo = "\u00f4";
StringConstants.O_circunflexo = "\u00d4";
StringConstants.o_til = "\u00f5";
StringConstants.O_til = "\u00d5";
StringConstants.o_trema = "\u00f6";
StringConstants.O_trema = "\u00d6";
StringConstants.u_agudo = "\u00fa";
StringConstants.U_agudo = "\u00da";
StringConstants.u_crase = "\u00f9";
StringConstants.U_crase = "\u00d9";
StringConstants.u_til = "\u0169";
StringConstants.U_til = "\u0168";
StringConstants.u_circunflexo = "\u00fb";
StringConstants.U_circunflexo = "\u00db";
StringConstants.u_trema = "\u00fc";
StringConstants.U_trema = "\u00dc";
StringConstants.cedilha = "\u00e7";
StringConstants.CEDILHA = "\u00c7";
StringConstants.n_til = "\u00f1";
StringConstants.N_til = "\u00d1";
StringConstants.e_comercial = "\u0026";
StringConstants.aspa_simples = "\u0027";
StringConstants.aspas_duplas = "\"";
StringConstants.double_s = "\u00a7";
StringConstants.maior_maior = "\u00BB";
StringConstants.menor_menor = "\u00AB";
StringConstants.x_fechar = "\u00D7";
StringConstants.cao = StringConstants.cedilha + StringConstants.a_til + "o";
StringConstants.NUMEROS = ArrayLst.build("0","1","2","3","4","5","6","7","8","9");
StringConstants.MINUSCULAS_ESPECIAIS = ArrayLst.build(
	StringConstants.a_agudo, StringConstants.a_crase, StringConstants.a_circunflexo, StringConstants.a_til, StringConstants.a_trema
	, StringConstants.e_agudo, StringConstants.e_crase, StringConstants.e_circunflexo, StringConstants.e_til, StringConstants.e_trema
	, StringConstants.i_agudo, StringConstants.i_crase, StringConstants.i_circunflexo, StringConstants.i_til, StringConstants.i_trema
	, StringConstants.o_agudo, StringConstants.o_crase, StringConstants.o_circunflexo, StringConstants.o_til, StringConstants.o_trema
	, StringConstants.u_agudo, StringConstants.u_crase, StringConstants.u_circunflexo, StringConstants.u_til, StringConstants.u_trema
	, StringConstants.n_til, StringConstants.cedilha
);
StringConstants.MAIUSCULAS_ESPECIAIS = StringConstants.MINUSCULAS_ESPECIAIS.map(s => s.toUpperCase());
StringConstants.ESPECIAIS = StringConstants.MINUSCULAS_ESPECIAIS.concat(StringConstants.MAIUSCULAS_ESPECIAIS);
StringConstants.MINUSCULAS = ArrayLst.build(
	"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
).concat(StringConstants.MINUSCULAS_ESPECIAIS);
StringConstants.MAIUSCULAS = StringConstants.MINUSCULAS.map(s => s.toUpperCase());
StringConstants.LETRAS_E_NUMEROS = StringConstants.MINUSCULAS.concat(StringConstants.MAIUSCULAS).concat(StringConstants.NUMEROS);
StringConstants.FILE_LETTERS = StringConstants.LETRAS_E_NUMEROS.copy().add("_").add("-").add(".").add("/");
StringConstants.JAVA_LETTERS = StringConstants.LETRAS_E_NUMEROS.copy().add("_").removeAll(StringConstants.ESPECIAIS);
StringConstants.PREPOSICOES_NOME_PROPRIO = ArrayLst.build(
	"de", "da", "do", "das", "dos"
);
StringConstants.PREPOSICOES = ArrayLst.build(
	"a", "ante", "ap"+StringConstants.o_agudo+"s", "at"+StringConstants.e_agudo, "com", "contra",
	"de", "desde", "em", "entre", "para", "por", "perante", "sem",
	"sob", "sobre", "traz", "do", "da", "dos", "das",
	"dum", "duma", "duns", "dumas", "no", "na", "nos", "nas"
);
StringConstants.ARTIGOS = ArrayLst.build(
	"a", "o", "as", "os", "um", "uma", "uns", "umas"
);
StringConstants.SIMBOLOS = ArrayLst.build(
	" ",".",",",";",":","(",")","<",">"
	,"{", "}","[","]","!","@","#","$","%"
	,"&","*","-","_","=","+","/","?"
	,"|","\"","'","`","^","~","\n","\t"
);
