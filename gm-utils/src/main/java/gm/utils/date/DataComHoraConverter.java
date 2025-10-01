package gm.utils.date;

import java.util.Calendar;

import gm.utils.jpa.converters.JpaConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DataComHoraConverter extends JpaConverter<DataComHora, Calendar> {

	public static DataComHora debugar;
	
	@Override
	public Calendar convertToDatabaseColumn(DataComHora de) {
		if (de == null) {
			return null;
		} else {
			if (de == debugar) {
				System.out.println();
			}
			return de.toCalendar();
		}
	}

	@Override
	public DataComHora convertToEntityAttribute(Calendar de) {
		return DataComHora.from(de);
	}

}
