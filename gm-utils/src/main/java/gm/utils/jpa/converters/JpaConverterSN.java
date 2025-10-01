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
public class JpaConverterSN extends JpaConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {

		if (value == null) {
			return null;
		}
		if (UBoolean.isTrue(value)) {
			return "S";
		} else if (UBoolean.isFalse(value)) {
			return "N";
		} else {
			return null;
		}

	}

	@Override
	public Boolean convertToEntityAttribute(String value) {

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
