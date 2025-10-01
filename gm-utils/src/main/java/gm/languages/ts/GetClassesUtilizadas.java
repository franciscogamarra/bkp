package gm.languages.ts;

import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import src.commom.utils.comum.OurTypes;

public class GetClassesUtilizadas {

	public static ListClass all(Class<?> appClass) {
		
		ListClass classes = UClass.classesDaMesmaPackageESubPackages(appClass);
		classes.add(OurTypes.class);
		
		ListClass list = new ListClass();

		while (classes.isNotEmpty()) {
			
			Class<?> classe = classes.remove(0);
			
			list.add(classe);

			ClassBox.get(classe).getImports().filter(i -> i.getName().contains("src.")).each(imp -> {
				if (!classes.contains(imp) && !list.contains(imp)) {
					classes.add(imp);
				}
			});
			
		}
		
		return list;
		
	}
	
}
