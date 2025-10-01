package br.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import br.utils.strings.StringEmpty;

public class DateUtils {

	public static String formatToUtcIso(LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
        return dateTime
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
	
    public static LocalDateTime parseFromUtcIso(String dateTimeStr) {
    	
    	if (StringEmpty.is(dateTimeStr)) {
    		return null;
    	}
    	
    	dateTimeStr = dateTimeStr.replace("+0000", "Z");
    	
        OffsetDateTime odt = OffsetDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return odt.toLocalDateTime();
    }
    
    public static void main(String[] args) {
		parseFromUtcIso("2022-11-26T16:10:23.359+0000");
	}
	
}