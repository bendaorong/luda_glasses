package com.luda.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * jackson工具类 json串和java复杂对象之间转换
 * 
 */
public class JsonUtil {
	private static Logger logger = Logger.getLogger(JsonUtil.class);
	private static final String UTF_8 = "utf-8";
	private static ObjectMapper jsonObjectMapper = new ObjectMapper();

	static {
		// 设置输出时包含属性的风格(全部输出)
		jsonObjectMapper.setSerializationInclusion(Inclusion.ALWAYS);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		jsonObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 禁止使用int代表Enum的order()來反序列化Enum,非常危險
		jsonObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
		// 日期格式转换
		jsonObjectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		jsonObjectMapper.getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * java对象转为jsonStr
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static String toJson(Object object){
		if (object == null) {
            return "no Object";
        }
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		try {
			JsonGenerator jsonGenerator = jsonObjectMapper.getJsonFactory().createJsonGenerator(bos, JsonEncoding.UTF8);
			jsonObjectMapper.writeValue(jsonGenerator, object);
			return bos.toString(UTF_8);
		}catch (Exception e) {
			logger.error("json转换异常", e);
        }finally{
        	 try {
				bos.close();
			} catch (IOException e) {
				logger.error("object转换json异常", e);
			}
        }
		return "";
		
	}

	/**
	 * jsonStr转为java对象
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 *
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz){
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return jsonObjectMapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			logger.error("json转换object异常", e);
		}
		return null;
	}

}
