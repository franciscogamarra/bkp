package gm.utils.comum;

import gm.utils.date.Data;
import lombok.Getter;

@Getter
public abstract class Cache<T> {

	public static final int MINUTOS_PADRAO = 120;

	private T value;
	private Data data;
	public static Data limpezaCache;
	private final int minutos;

	public Cache(int minutos) {
		this.minutos = minutos;
	}
	
	public Cache() {
		this(MINUTOS_PADRAO);
	}

	public T get() {
		if (expirado() ) {
			atualiarSync();
		}
		return value;
	}

	private synchronized void atualiarSync() {
		if (expirado()) {
			this.value = atualizar();
			this.data = getNow();
		}
	}

	public Data getNow() {
		return Data.now();
	}

	public boolean expirado() {
		return expirado(data, minutos);
	}

	public static boolean expirado(Data data, int minutos) {
		return data == null || data.jaPassouMinutos(minutos) || (limpezaCache != null && data.menor(limpezaCache));
	}

	protected abstract T atualizar();

	public void clear() {
		data = null;
	}

}
