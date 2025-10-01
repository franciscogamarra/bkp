package gm.utils.classes.interfaces;

import java.lang.annotation.Annotation;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcTipo;
import gm.utils.jpa.USchema;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.Metodos;
import jakarta.persistence.Table;

public class ClasseReal extends IClass {

	private Class<?> classe;

	private ClasseReal(Class<?> classe) {
		this.classe = classe;
	}
	
	@Override
	public String getName() {
		return classe.getName();
	}

	@Override
	public String getSimpleName() {
		return classe.getSimpleName();
	}

	private Lst<IField> fields;
	
	@Override
	public Lst<IField> loadFields() {
		if (fields == null) {
			fields = Atributos.get(classe).map(i -> (IField) new FieldReal(i));
			fields.add(0, getId());
		}
		return fields.copy();
	}

	@Override
	public JcTipo toJcTipo() {
		return JcTipo.build(classe);
	}

	@Override
	public String getPackageName() {
		return classe.getPackageName();
	}

	@Override
	public IField getId() {
		return new FieldReal(Atributos.get(classe).getId());
	}

	@Override
	public String getTableName() {
		Table table = classe.getAnnotation(Table.class);
		return table == null ? sn() : table.name();
	}

	@Override
	public String getSchemaName() {
		Table table = classe.getAnnotation(Table.class);
		return table == null || table.schema() == null ? USchema.getSchemaDefault() : table.schema();
	}

	@Override
	public IAnnotation getAnnotation(Class<? extends Annotation> annotationClass) {
		return IAnnotation.build(classe.getAnnotation(annotationClass));
	}

	@Override
	public IClass getMetodoRetorno(String nomeMetodo) {
		return get(Metodos.get(classe).get(nomeMetodo).retorno().getClasse());
	}
	
	private static final Lst<ClasseReal> ITENS = new Lst<>();

	public static synchronized IClass get(Class<?> classe) {
		ClasseReal o = ITENS.unique(i -> i.eq(classe));
		if (o == null) {
			o = new ClasseReal(classe);
			ITENS.add(o);
		}
		return o;
	}

	@Override
	public boolean isEnum() {
		return classe.isEnum();
	}

	@Override
	protected IClass getSuperclass() {
		Class<?> s = classe.getSuperclass();
		return s == null ? null : get(s);
	}

	@Override
	protected boolean instanceOf(IClass classe) {
		
		if (classe instanceof ClasseReal) {
			ClasseReal cr = (ClasseReal) classe;
			return UClass.instanceOf(this.classe, cr.classe);
		} else {
			throw new NaoImplementadoException();
		}
		
	}

	@Override
	protected boolean isAbstract() {
		return UClass.isAbstract(classe);
	}

	@Override
	public GFile getJavaFile() {
		return UClass.getJavaFile(classe);
	}
	
}
