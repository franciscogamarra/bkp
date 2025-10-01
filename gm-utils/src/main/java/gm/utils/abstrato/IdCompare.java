package gm.utils.abstrato;

import gm.utils.exception.NaoImplementadoException;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.longo.LongCompare;

public class IdCompare {

	public static boolean eq(ObjetoComId<?> a, ObjetoComId<?> b) {
		
		if (a == b) {
			return true;
		} else if (a == null) {
			return b.getId() == null;
		} else if (b == null) {
			return a.getId() == null;
		} else if (a.equals(b)) {
			return true;
		} else {
			
			Object ida = a.getId();
			Object idb = b.getId();
			
			if (ida == idb) {
				return true;
			} else if (ida == null || idb == null) {
				return false;
			} else if (ida.equals(ida)) {
				return true;
			}
			
			if (ida instanceof Integer) {
				return IntegerCompare.eq((Integer) ida, (Integer) idb);
			} else {
				return LongCompare.eq((Long) ida, (Long) idb);
			}
			
		}
		
	}
	
	public static boolean ne(ObjetoComId<?> a, ObjetoComId<?> b) {
		return !eq(a,b);
	}

	public static int compare(Object a, Object b) {
		
		Object va = ExtraidId.exec(a);
		Object vb = ExtraidId.exec(b);
		
		if (va == vb) {
			return 0;
		} else if (va == null) {
			return -1;
		} else if (vb == null) {
			return 1;
		} else if (va.equals(vb)) {
			return 0;
		} else if (va instanceof Integer) {
			Integer vai = (Integer) va; 
			if (vb instanceof Integer) {
				return IntegerCompare.compare(vai, (Integer) vb);
			} else if (vb instanceof Long) {
				return LongCompare.compare(vai.longValue(), (Long) vb);
			} else {
				throw new NaoImplementadoException();
			}
		} else if (va instanceof Long) {
			Long vai = (Long) va; 
			if (vb instanceof Integer) {
				Integer vbi = (Integer) vb;
				return LongCompare.compare(vai, vbi.longValue());
			} else if (vb instanceof Long) {
				return LongCompare.compare(vai, (Long) vb);
			} else {
				throw new NaoImplementadoException();
			}
		} else {
			throw new NaoImplementadoException();
		}
		
	}
	
}