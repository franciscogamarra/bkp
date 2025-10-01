package gm.utils.javaCreate.annotations;

import gm.utils.javaCreate.JcAnotacao;
import gm.utils.javaCreate.JcAnotacoes;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JcColumn extends JcAnotacaoGetSet {

	private String name;
	private Integer precision;
	private Integer scale;
	private Integer length;
	private Boolean nullable;

	public JcColumn() {
		super(javax.persistence.Column.class);
	}

	public static JcColumn from(JcAnotacoes as) {
		JcColumn o = new JcColumn();
		JcAnotacao an = as.get(o.getAnnotation());
		if (an == null) {
			an = as.get(javax.persistence.JoinColumn.class);
			if (an == null) {
				return null;
			}
		}
		o.set(an);
		return o;
	}

}
