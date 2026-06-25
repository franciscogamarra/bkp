package br.gerar;

public class MontarObjects extends ManipularArquivo {
	
	private final Aba aba;

	public MontarObjects(Aba aba) {
		this.aba = aba;
	}

	@Override
	protected void impl() {
		list.addAll(GerarListCampos.exec(aba));
	}

	@Override
	public String getFileName() {
		return "/interfaces/" + primeiraMaiuscula(aba.nome) + "Objects.ts";
	}

	@Override
	public String getRegionStart() {
		return "// campos start";
	}

	@Override
	public String getRegionEnd() {
		return "// campos end";
	}
	
}
