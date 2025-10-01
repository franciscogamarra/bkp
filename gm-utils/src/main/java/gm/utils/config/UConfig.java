package gm.utils.config;

import gm.utils.comum.Lst;
import gm.utils.comum.UAssert;
import gm.utils.comum.ULog;
import gm.utils.files.GFile;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public abstract class UConfig {

	public static UConfig instance;
	private final GFile pathRaizProjetoAtual;
	private final Lst<GFile> pathRaizProjetosVinculados;
	private final Lst<GFile> pathRaizProjetos;
	private final String nomeProjetoGlobal;
	private final ListString packagesApp = ListString.array("gm");
	private final UConfigJpa jpa;
	private final UConfigJdbc jdbc;

	public UConfig() {

		if (instance != null) {
			if (instance.getClass().equals(this.getClass())) {
				ULog.warn("Jah foi criada uma instancia de UConfig");
			}
		} else {
			instance = this;
		}
		
		GFile s = loadPathRaizProjetoAtual();

		if (s == null) {
			pathRaizProjetoAtual = null;
			pathRaizProjetosVinculados = null;
			pathRaizProjetos = null;
			nomeProjetoGlobal = null;
		} else {
			
			pathRaizProjetoAtual = s.join("src").join("main");

			pathRaizProjetosVinculados = new Lst<>();
			Lst<GFile> list = loadPathRaizProjetosVinculados();
			if (list != null) {
				for (GFile ss : list) {
					pathRaizProjetosVinculados.addIfNotContains(ss);
				}
			}

			packagesApp.addAll(loadPackagesApp());
			packagesApp.setBloqueada(true);

			nomeProjetoGlobal = loadNomeProjetoGlobal();
			UAssert.notEmpty(nomeProjetoGlobal, "nomeProjetoGlobal == null - loadNomeProjetoGlobal retornou vazio");

			pathRaizProjetos = pathRaizProjetosVinculados.copy();
			pathRaizProjetos.addIfNotContains(pathRaizProjetoAtual);
			pathRaizProjetos.setBloqueada(true);

		}

		jpa = loadConfigJpa();
		jdbc = loadConfigJdbc();

	}

	public static UConfig get() {
		if (instance == null) {
			ConfigAuto.config();
		}
		return instance;
	}

	public abstract boolean emDesenvolvimento();
	public abstract boolean onLine();

	public abstract String getOwnerBanco();

	protected abstract UConfigJpa loadConfigJpa();
	protected abstract UConfigJdbc loadConfigJdbc();
	
	protected GFile loadPathRaizProjetoAtual() {
		return GFile.userDir();
	}
	
	protected String loadNomeProjetoGlobal() {
		return GFile.userDir().getSimpleName();
	}
	
	protected abstract Lst<GFile> loadPathRaizProjetosVinculados();
	protected abstract ListString loadPackagesApp();
	public void onStartApplication() {}

	public abstract void validaStop();

	public static UConfigJpa jpa() {
		return get().getJpa();
	}
	public static ConexaoJdbc con() {

		UConfig config = get();

		if (config.onLine()) {
			return new ConexaoJdbc(config.getJpa().getConnection());
		}
		UConfigJdbc jdbc = config.getJdbc();
		if (jdbc == null) {
			return null;
		}
		return config.getJdbc().getCon();

	}
	public static void checaStop() {
		if (get() != null) {
			get().validaStop();
		}
	}

	public abstract int execSql(String sql);

//	public static ListString returnPathRaizProjetos() {
//		UConfig config = UConfig.get();
//		if (config == null) {
//			ListString list = new ListString();
//			return list;
//		} else {
//			return config.getPathRaizProjetos();
//		}
//	}
//	public static void main(String[] args) {
//		 String s = System.getProperty("user.dir");
//		 SystemPrint.ln(s);
//	}

}
