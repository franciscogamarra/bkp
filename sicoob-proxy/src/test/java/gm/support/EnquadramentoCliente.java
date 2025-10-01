package gm.support;

import java.util.HashMap;
import java.util.Map;

import br.utils.DevException;
import br.utils.JsonUtils;
import br.utils.Lst;
import lombok.Getter;

@Getter
public class EnquadramentoCliente extends TesteBase {

	private static final Lst<EnquadramentoCliente> ITENS = new Lst<>();
	
	private Map<String, Object> dados = new HashMap<>();
	
	public EnquadramentoCliente() {
		ITENS.add(this);
	}
	public EnquadramentoCliente(String cpfOuCnpj) {
		this();
		load(cpfOuCnpj);
	}
	
	public void setId(Integer value) {
		dados.put("id", value);
	}
	
	public Integer getId() {
		return (Integer) dados.get("id");
	}
	
	public void load(String cpfOuCnpj) {
		this.dados = JsonUtils.fromJson(JsonUtils.loadResource("enquadramento-cliente/" + cpfOuCnpj));
	}
	
	public static final EnquadramentoCliente ID4_00213678166808 = new EnquadramentoCliente("00213678166808");
	public static final EnquadramentoCliente ID38_25150839949 = new EnquadramentoCliente("25150839949");
	
	public static EnquadramentoCliente get(Integer id) {
		if (id == null) {
			return null;
		}
		EnquadramentoCliente o = ITENS.unique(i -> i.getId().equals(id));
		if (o == null) {
			throw DevException.build("Não localizado: EnquadramentoCliente " + id);
		}
		return o;
	}

	public Map<String, Object> toSingle() {
		Map<String, Object> o = new HashMap<>();
		o.put("id", getId());
		return o;
	}
	
}
