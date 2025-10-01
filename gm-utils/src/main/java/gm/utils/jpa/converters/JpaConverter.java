package gm.utils.jpa.converters;

import jakarta.persistence.AttributeConverter;

public abstract class JpaConverter<JAVA,BANCO> implements AttributeConverter<JAVA,BANCO> {

	@SuppressWarnings("unchecked")
	public BANCO toBancoCast(Object o) {
		return convertToDatabaseColumn((JAVA) o);
	}

}
