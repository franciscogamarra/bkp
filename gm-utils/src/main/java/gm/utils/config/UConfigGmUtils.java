package gm.utils.config;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;

public class UConfigGmUtils extends UConfig {

	public static UConfigGmUtils config() {
		return new UConfigGmUtils();
	}

	@Override
	public boolean emDesenvolvimento() {
		return false;
	}

	@Override
	public boolean onLine() {
		return false;
	}

	@Override
	public String getOwnerBanco() {
		return null;
	}

	@Override
	protected UConfigJpa loadConfigJpa() {
		return null;
	}

	@Override
	protected UConfigJdbc loadConfigJdbc() {
		return null;
	}

	@Override
	protected GFile loadPathRaizProjetoAtual() {
		return GFile.get("/opt/desen/gm/cs2019/gm-utils/");
	}

	@Override
	protected String loadNomeProjetoGlobal() {
		return "gm-utils";
	}

	@Override
	protected Lst<GFile> loadPathRaizProjetosVinculados() {
		return null;
	}

	@Override
	protected ListString loadPackagesApp() {
		return null;
	}

	@Override
	public void validaStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public int execSql(String sql) {
		return 0;
	}

}
