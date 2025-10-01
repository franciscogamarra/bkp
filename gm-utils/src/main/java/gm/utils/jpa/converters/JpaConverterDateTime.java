/*
exemplo:

import jakarta.persistence.Convert;
@Convert(converter=JpaConverterSN.class)
private Boolean campo;
*/

package gm.utils.jpa.converters;

import java.util.Date;

import jakarta.persistence.Converter;

import gm.utils.date.Data;

@Converter(autoApply = true)
public class JpaConverterDateTime extends JpaConverter<Data, Date> {

	@Override
	public Date convertToDatabaseColumn(Data value) {
		return value == null ? null : value.date();
	}

	@Override
	public Data convertToEntityAttribute(Date value) {
		return value == null ? null : Data.to(value);
	}

}
