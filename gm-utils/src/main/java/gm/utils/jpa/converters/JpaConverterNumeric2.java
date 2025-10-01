/*
exemplo:

import jakarta.persistence.Convert;
@Convert(converter=JpaConverterNumeric2.class)
private Numeric2 campo;
*/

package gm.utils.jpa.converters;

import java.math.BigDecimal;

import jakarta.persistence.Converter;

import gm.utils.number.Numeric2;

@Converter(autoApply = true)
public class JpaConverterNumeric2 extends JpaConverter<Numeric2, Double> {

	@Override
	public Double convertToDatabaseColumn(Numeric2 value) {
		return value == null ? null : value.toDouble();
	}

	@Override
	public Numeric2 convertToEntityAttribute(Double value) {
		return value == null ? null : new Numeric2(value);
	}
	
	@Override
	public Double toBancoCast(Object o) {
		
		if (o instanceof BigDecimal) {
			o = new Numeric2((BigDecimal) o);
		}
		
		return super.toBancoCast(o);
	}
	

}
