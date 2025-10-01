package br.caixa.parametrosSimulacao.loteca;

import java.util.HashMap;
import java.util.Map;

import lombok.ToString;
import src.commom.utils.string.StringCompare;

@ToString
public class EquipeEsportivaDto {
	
	public Boolean indicadorSelecao;
	public String nome;
	public Integer numero;
	public Integer numeroPais;
	public String descricaoCurta;
	public String uf;
	public String nomeClass;
	
	public static final Map<Integer, EquipeEsportivaDto> map = new HashMap<>();
	
//	exemplo usado para testes
	public static final EquipeEsportivaDto abc = new EquipeEsportivaDto();
	static {
		abc.indicadorSelecao = false;
		abc.nome = "ABC";
		abc.numero = 293;
		abc.numeroPais = 10;
		abc.descricaoCurta = "ABC";
		abc.uf = "RN";
		abc.nomeClass = "ABCRN";
	}
	
	public boolean eq(EquipeEsportivaDto o) {
		
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		return StringCompare.eq(toString(), o.toString());
		
	}
	
	public static EquipeEsportivaDto find(Integer id) {
		if (id == null) {
			return null;
		} else {
			return map.get(id);
		}
	}
	
}
