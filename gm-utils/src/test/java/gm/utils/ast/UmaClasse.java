package gm.utils.ast;

import gm.utils.anotacoes.Colunas;
import gm.utils.anotacoes.Comentario;
import js.support.console;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Comentario("teste")
@Colunas(value = 3)
public class UmaClasse {

	public int a_publico;
	private String a_privado;
	double a_default;
	private UmaOutraClasse a_um_tipo_de_import_implicito;

	public String m_public_String() {
		return m_private_String_null() + m_private_String_literal();
	}

	public String m_public_String_atributo_com_this() {
		return this.a_privado;
	}

	public String m_public_String_atributo_sem_this() {
		return a_privado;
	}

	public String m_public_String_parametro_mesmo_nome_atributo(String a_privado) {
		return a_privado;
	}

	public void m_uma_declaracao_de_variavel_com_o_mesmo_nome_de_um_atributo() {
		String a_privado = "teste";
		console.log(a_privado);
	}

	private String m_private_String_null() {
		return null;
	}

	private String m_private_String_literal() {
		return "teste";
	}

	String m_default_String_null() {
		return null;
	}

	void m_default_void() {

	}

	public void m_public_void() {

	}

	String uma_string_com_um_comentario_de_linha = "//";
	String uma_string_com_um_comentario_de_bloco = "/*";
	String uma_string_com_um_scape = "\\";

	// um comentario de linha

	public static void m_public_static_void() {

	}

	/*
		Um comentario de bloco
	*/

	public static String m_public_static_String() {
		return "";
	}

	/*
		// um comentario de bloco com um comentario de linha dentro
	*/

	//*/ um comentario de linha com um bloco dentro
	//*/

	static void m_default_static() {

	}

}
