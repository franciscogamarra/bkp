package gm.utils.config;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;

public class ConfigAuto extends UConfig {

	@Override
	public final boolean emDesenvolvimento() {
		return false;
	}

	@Override
	public final boolean onLine() {

		return false;
	}

	@Override
	public final String getOwnerBanco() {

		return null;
	}

	@Override
	protected final UConfigJpa loadConfigJpa() {

		return null;
	}

	@Override
	protected final UConfigJdbc loadConfigJdbc() {

		return null;
	}

	@Override
	protected final Lst<GFile> loadPathRaizProjetosVinculados() {
		return GetDependencias.get();
	}

	@Override
	protected final ListString loadPackagesApp() {

		return null;
	}

	@Override
	public final void validaStop() {

	}

	@Override
	public final int execSql(String sql) {

		return 0;
	}

	public static ConfigAuto config() {
		return new ConfigAuto();
	}

	public static void main(String[] args) {
		config();
	}

}
