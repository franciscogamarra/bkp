package gm.utils.files;

import java.io.File;
import java.util.function.Predicate;

import gm.utils.comum.Lst;

public class UFileFilter {

	public boolean desprezar_target = UFile.desprezar_target;
	public boolean desprezar_node_modules = true;
	public boolean desprezar_os_que_comecam_com_ponto = true;
	public final Lst<Predicate<GFile>> predicates = new Lst<>();
	
	public boolean deveIgnorar(File file) {
		return deveIgnorar(GFile.get(file));
	}
	
	public boolean deveIgnorar(GFile file) {
		
		if (desprezar_target && file.contains("target")) {
			return true;
		}
		
		if (desprezar_node_modules && file.contains("node_modules")) {
			return true;
		}
		
		if (desprezar_os_que_comecam_com_ponto && file.getList().anyMatch(s -> s.startsWith("."))) {
			return true;
		}
		
		return predicates.anyMatch(i -> i.test(file));
		
	}

}
