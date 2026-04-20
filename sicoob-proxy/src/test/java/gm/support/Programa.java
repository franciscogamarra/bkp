package gm.support;

import java.util.HashMap;
import java.util.Map;

import br.support.comum.Lst;
import br.utils.DevException;
import br.utils.JsonUtils;
import lombok.Getter;

@Getter
public class Programa extends TesteBase {

	private static final Lst<Programa> ITENS = new Lst<>();
	
	private Map<String, Object> dados;
	
	public Programa(int id) {
		load(id);
		ITENS.add(this);
	}
	
	public Programa() {
		this(10013);
	}
	
	public void setId(Integer value) {
		dados.put("id", value);
	}
	
	public Integer getId() {
		return (Integer) dados.get("id");
	}
	
	public void load(int id) {
		this.dados = JsonUtils.fromJson(JsonUtils.loadResource("programa-" + id));
	}
	
	public static final Programa O10013 = new Programa(10013);
	public static final Programa O2424 = new Programa(2424);
	public static final Programa O2473 = new Programa(2473);

	public static Programa get(Integer id) {
		if (id == null) {
			return null;
		}
		Programa o = ITENS.unique(i -> i.getId().equals(id));
		if (o == null) {
			throw DevException.build("Não localizado: Programa " + id);
		}
		return o;
	}

	public Map<String, Object> toSingle() {
		Map<String, Object> o = new HashMap<>();
		o.put("id", getId());
		return o;
	}
	
}
