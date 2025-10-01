/*
exemplo:

import jakarta.persistence.Convert;
@Convert(converter=JpaConverterSN.class)
private Boolean campo;
*/

package gm.utils.jpa.converters;

import jakarta.persistence.Converter;

import gm.utils.comum.UBoolean;

@Converter(autoApply = true)
public class JpaConverterBit extends JpaConverter<Boolean, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Boolean value) {

		if (value == null) {
			return null;
		}
		if (UBoolean.isTrue(value)) {
			return 1;
		} else if (UBoolean.isFalse(value)) {
			return 0;
		} else {
			return null;
		}

	}

	@Override
	public Boolean convertToEntityAttribute(Integer value) {

		if (value == null) {
			return null;
		}
		if (UBoolean.isTrue(value)) {
			return true;
		} else if (UBoolean.isFalse(value)) {
			return false;
		} else {
			return null;
		}

	}

}
