package com.luda.util;

import com.luda.comm.po.DateMorpherEx;
import com.luda.comm.po.JsonDateValueProcessor;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.JSONUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	
	public static final String KEY_MD5 = "Q56GtyNkop97H334TtyturfgErvvv98a";
	
	

	/**
	 * <p>
	 * Discription:转换日期为字符串
	 * </p>
	 * 
	 * @param date
	 *            日期对象
	 * @param dateFormat
	 *            格式，默认为yyyy-MM-dd HH:mm:SS
	 * @return
	 */
	public static String formatDate(Date date, String dateFormat) {
		if (date != null) {
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(date);
		}
		return null;
	}

	/**
	 * json转换成javabean
	 * 
	 * @param jsonObj
	 * @param clazz
	 * @return
	 */
	public static <T> T convertJsonToBean(JSONObject jsonObj, Class<T> clazz) {
		if (jsonObj != null && !JSONNull.getInstance().equals(jsonObj)) {
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm", "yyyy-MM-dd" ,"HH:mm:ss",""}));
			return (T) JSONObject.toBean(jsonObj, clazz);
		}
		return null;
	}
	
	public static <T> T convertJsonToBean_(JSONObject jsonObj, Class<T> clazz, Map classMap) {
		if (jsonObj != null && !JSONNull.getInstance().equals(jsonObj)) {
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpherEx(new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm", "yyyy-MM-dd"}));
			return (T) JSONObject.toBean(jsonObj, clazz, classMap);
		}
		return null;
	}
	
	/**
	 * javabean转换成json
	 * 
	 * @param obj
	 * @param datePattern
	 *            日期转换格式(默认yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static JSONObject convertBeanToJson(Object obj, String datePattern) {
		if(StringUtils.isBlank(datePattern)){
			datePattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (obj != null) {
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor(datePattern));
			config.registerDefaultValueProcessor(Integer.class,  
			        new DefaultValueProcessor() {  
			            public Object getDefaultValue(Class type) {  
			                return null;  
			            }  
			        });  
			config.registerDefaultValueProcessor(Double.class,  
			        new DefaultValueProcessor() {  
			            public Object getDefaultValue(Class type) {  
			                return null;  
			            }  
			        });  
			return JSONObject.fromObject(obj, config);
		}
		return new JSONObject();
	}
	
	public static JSONArray convertBeanCollectionToJsonArray(Collection collection, String datePattern){
		if(collection != null){
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor(datePattern));
			config.registerDefaultValueProcessor(Integer.class,
			        new DefaultValueProcessor() {
			            public Object getDefaultValue(Class type) {
			                return null;
			            }
			        });
			config.registerDefaultValueProcessor(Double.class,
			        new DefaultValueProcessor() {
			            public Object getDefaultValue(Class type) {
			                return null;
			            }
			        });
			return JSONArray.fromObject(collection, config);
		}
		return new JSONArray();
	}
	


	
	/**
	 * 手机号验证
	 * @param mobileNumber:手机号
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String mobileNumber) {
		if(StringUtils.isNotBlank(mobileNumber)){
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$"); // 验证手机号 ;
			Matcher m = p.matcher(mobileNumber);
			return m.matches();
		}
		return false;
	}
	
	/**
	 * 获取最大有效时间
	 */
	public static Date getMaxDatetime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2099, 11, 31, 0, 0, 0);
		return calendar.getTime();
	}
	
	

	

	
	/**
	 * 格式化金额
	 */
	public static final String formatMoney(BigDecimal money, String format){
		if(money == null){
			return null;
		}
		if(StringUtils.isBlank(format)){
			format = "0.00";
		}
		return new DecimalFormat(format).format(money);
	}
	

	

	
	
	
    
    /**
	 * 转换http参数
	 * 将map参数转换成 key1=value1&key2=value2&key3=value3的格式
	 */
	public static String convertHttpParams(Map<String,String> param){
		if(param != null && !param.isEmpty()){
			StringBuilder paramStr = new StringBuilder();
			for(String key : param.keySet()){
				paramStr.append(key).append("=").append(StringUtils.isBlank(param.get(key))?"":param.get(key)).append("&");
			}
			paramStr.deleteCharAt(paramStr.lastIndexOf("&"));
			return paramStr.toString();
		}
		return null;
	}
	
	/**
	 * 合并2个数组为1个数组
	 */
	public static String[] mergeArray(String[] arr1, String[] arr2){
		String[] a = arr1;
		String[] b = arr2;
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	/**
	 * 删除文件
	 */
	public static void deleteFile(String filePath){
		if(StringUtils.isNotBlank(filePath)){
			File oldFile = new File(filePath);
			if(oldFile.exists()){
				oldFile.delete();
			}
		}
	}
	
	
	/**
	 * <p>
	 * Discription:转换字符串为日期对象
	 * </p>
	 * 
	 * @param dateStr 日期字符串
	 * @param dateFormat 格式，默认为yyyy-MM-dd HH:mm:SS
	 * @return
	 */
	public static Date parseDate(String dateStr, String dateFormat) {
		if (dateFormat == null) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				return DateUtils.parseDate(dateStr, new String[] { dateFormat });
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("parse date error -->"+e.getMessage());
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Discription:转换字符串为日期对象
	 * </p>
	 * 
	 * @param dateStr 日期字符串
	 * 支持 yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm, yyyy-MM-dd, yyyy-MM-dd'T'HH:mm:ss 格式字符串时间
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		String[] dateFormats =
				new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss",
						"yyyyMMddHHmmss" };
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				return DateUtils.parseDate(dateStr, dateFormats);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("parse date error -->"+e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 正则表达式验证
	 * @param regex:验证规则正则表达式
	 * @param inputStr:待验证字符串
	 */
	public static boolean matchString(String regex, String inputStr){
		if(StringUtils.isNotBlank(regex) && StringUtils.isNotBlank(inputStr)){
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(inputStr);
			return matcher.matches();
		}
		return false;
	}
	
	/**
	 * 获取上个月的日期
	 * @return
	 * 	dealDate:上个月的日期，例如：2015-01
	 * 	month:上个月的月份，例如 1
	 */
	public static Map<String,String> getLastMonthDealDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		
		Map<String,String> dateMap = new HashMap<String, String>();
		dateMap.put("dealDate", CommonUtils.formatDate(c.getTime(), "yyyy-MM"));
		dateMap.put("month", String.valueOf(c.get(Calendar.MONTH)+1));
		return dateMap;
	}
	
    public static int daysBetween(Date sdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        sdate=sdf.parse(sdf.format(sdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }   
    
    /**
     * @param bytes
     * @return
     */
    public static String decode(final byte[] bytes) {
        return new String(Base64.decodeBase64(bytes));
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * 过滤掉json中的空值
     * @param data
     * @return
     */
	public static JSONObject jsonFilterNull(JSONObject data) {
		List<String> removeKeys = new ArrayList<String>();
		Iterator<String> keyIt = data.keys();
		while(keyIt.hasNext()){
			String key = keyIt.next();
			Object value = data.get(key);
			if(value instanceof String){
				if(StringUtils.isBlank(String.valueOf(value))){
					removeKeys.add(key);
				}
			}
			else if(value instanceof JSONNull){
				removeKeys.add(key);
			}
		}
		
		for(String key : removeKeys){
			data.remove(key);
		}
		
		return data;
	}

    
}