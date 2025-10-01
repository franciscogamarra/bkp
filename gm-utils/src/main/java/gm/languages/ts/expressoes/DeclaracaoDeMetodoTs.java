package gm.languages.ts.expressoes;

import gm.languages.java.expressoes.ModificadorDeAcesso;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.utils.comum.SystemPrint;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeclaracaoDeMetodoTs extends Palavra {

	public void setPodeRetornarNull(boolean podeRetornarNull) {
		this.podeRetornarNull = podeRetornarNull;
	}
	
	private String nome;
	private TipoTs tipo;
	private AbreBloco bloco;
	private ModificadorDeAcesso modificadorDeAcesso;
	private AbreParentesesMetodo abreParentesesMetodo;
	private FechaParentesesMetodo fechaParentesesMetodo;
	private String fechamento;
	
	private boolean podeRetornarNull;
	private boolean override;
	private boolean declararComoFuncao;
	private boolean declararComoArrow;
	private DeclaracaoDeUmTipoGenericoTs genericParam;
	
	public boolean async;

	public DeclaracaoDeMetodoTs(ModificadorDeAcesso modificadorDeAcesso, TipoTs tipo, String nome, DeclaracaoDeUmTipoGenericoTs genericParam) {
		super("");
		this.modificadorDeAcesso = modificadorDeAcesso;
		this.tipo = tipo;
		this.nome = nome;
		this.genericParam = genericParam;
		
	}
	
	@Override
	public String getS() {
		
		if (declararComoFuncao) {
			return "function " + nome;
		}

		if (declararComoArrow) {
			return "const " + nome + " = " + (async ? "async " : "");
		}
		
		String s = nome;
		
		if (override) {
			s = "override " + s;
		}

		if (async) {
			s = "async " + s;
		}
		
		if (modificadorDeAcesso != null) {
			s = modificadorDeAcesso.getS() + " " + s;
		}

		
		if (genericParam != null) {
			s += genericParam.getS();
		}
		
		if (fechamento != null) {
			s += fechamento;
		}
		
		return s;
		
	}
	
	public boolean isConstructor() {
		return nome.contentEquals("constructor");
	}

	public boolean isStatic() {
		return getModificadorDeAcesso() != null && getModificadorDeAcesso().isStatic();
	}

	public boolean isFinal() {
		return getModificadorDeAcesso() != null && getModificadorDeAcesso().isFinal();
	}
	
	public void setTipo(TipoTs tipo) {
		
		if (tipo == null && this.tipo != null) {
			SystemPrint.ln();
		}
		
		this.tipo = tipo;
	}
	
	@Override
	public void setComplemento(String complemento) {
		
		if (getComplemento() != null) {
			System.out.println(getComplemento());
		}
		
		super.setComplemento(complemento);
	}
	
}
