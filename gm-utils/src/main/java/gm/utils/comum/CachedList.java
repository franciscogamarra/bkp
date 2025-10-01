package gm.utils.comum;

import gm.utils.date.Data;

public abstract class CachedList<T> {

	public static final int MINUTOS_PADRAO = 120;

	private Lst<T> list;
	private Data data;
	public static Data limpezaCache;
	private final int minutos;

	public CachedList(int minutos) {
		this.minutos = minutos;
	}
	public CachedList() {
		this(MINUTOS_PADRAO);
	}

	public Lst<T> get() {
		if (expirado() ) {
			atualiarSync();
		}
		return list;
	}

	private synchronized void atualiarSync() {
		if (expirado()) {
			this.list = atualizar();
			this.data = getNow();
		}
	}

	protected Data getNow() {
		return Data.now();
	}

	private boolean expirado() {
		return expirado(data, minutos);
	}

	public static boolean expirado(Data data, int minutos) {
		return data == null || data.jaPassouMinutos(minutos) || (limpezaCache != null && data.menor(limpezaCache));
	}

	protected abstract Lst<T> atualizar();

	public void clear() {
		data = null;
	}

}
