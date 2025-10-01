package gm.utils.jpa.nativeQuery;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import gm.utils.comum.UBoolean;
import gm.utils.date.Data;
import gm.utils.number.Numeric2;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UDouble;
import gm.utils.number.ULong;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;

public class QRow {

	private Object[] array;
	private int index = 0;

	public QRow(Object[] array) {
		this.array = array;
	}

	public Date getDate(int i) {
		Data data = Data.to(array[i]);
		if (data == null) {
			return null;
		}
		return data.getDate();
	}

	public Data getData() {
		return getData(index++);
	}

	public Data getData(int i) {
		return Data.to(getDate(i));
	}

	public Calendar getCalendar(int i) {
		Data data = getData(i);
		return data == null ? null : data.getCalendar();
	}

	public String getStringUpper(int i) {
		String s = getString(i);
		if (StringEmpty.is(s)) {
			return null;
		}
		return s.toUpperCase();
	}

	public String getString() {
		return getString(index++);
	}

	public String getString(int i) {
		return StringParse.get(array[i]);
	}

	public Double getDouble(int i) {
		return UDouble.toDouble(array[i]);
	}

	public Boolean getBoolean() {
		return getBoolean(index++);
	}

	public Boolean getBoolean(int i) {
		return UBoolean.toBoolean(array[i]);
	}

	public boolean isTrue() {
		return isTrue(index++);
	}

	public boolean isTrue(int i) {
		return UBoolean.isTrue(array[i]);
	}

	public Integer getInt() {
		return getInt(index++);
	}

	public Integer getInt(int i) {
		return IntegerParse.toInt(array[i]);
	}

	public Long getLong(int i) {
		return ULong.toLong(array[i]);
	}

	public int length() {
		return array.length;
	}

	public BigDecimal getBigDecimal(int i) {
		Double o = getDouble(i);
		if (o == null) {
			return null;
		}
		return UBigDecimal.toBigDecimal(o);
	}

	public BigDecimal getBigDecimal2(int i) {
		return UBigDecimal.setScale(getBigDecimal(i), 2);
	}

	public Numeric2 getNumeric2() {
		return getNumeric2(index++);
	}

	public Numeric2 getNumeric2(int i) {
		Double o = getDouble(i);
		if (o == null) {
			return null;
		}
		return new Numeric2(o);
	}

	public Character getCharacter(int i) {
		String s = getString(i);
		if (StringEmpty.is(s)) {
			return null;
		}
		return s.charAt(0);
	}

}
