package gm.factoryes;

import br.com.sicoob.concessao.bndes.dtos.ProgramaBndesDTO;
import br.utils.JsonUtils;

public class Programas {

	private static ProgramaBndesDTO load(int id) {
		return JsonUtils.fromJson(JsonUtils.loadResource("programa-" + id), ProgramaBndesDTO.class);
	}
	
	public static final ProgramaBndesDTO Item2473 = load(2473);
	
}