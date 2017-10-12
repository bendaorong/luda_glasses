package com.luda.comm.po;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 日期格式转换器
 * @author bendaorong
 *
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
	public static final String Default_DATE_PATTERN = "yyyy-MM-dd"; 
	private DateFormat dateFormat;

	public JsonDateValueProcessor(String datePattern) {
		try {              
			this.dateFormat = new SimpleDateFormat(datePattern);         
		} catch (Exception e) {              
			this.dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);          
		} 
	}

	public DateFormat getDateFormat() {
		return this.dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	private Object process(Object value, JsonConfig jsonConfig) {
		Object dateValue = value;
		if (dateValue instanceof java.sql.Date)
			dateValue = new java.util.Date(
					((java.sql.Date) dateValue).getTime());

		if (dateValue instanceof java.util.Date)
			return this.dateFormat.format(dateValue);

		return dateValue;
	}
}
