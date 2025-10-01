package gm.teste;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "S_SOLICITACAO_AREA")
public class SolicitacaoArea {

	@Column(name = numero, nullable = false)
	private String numero;

	@Column(name = ano, nullable = false)
	private Integer ano;

}
