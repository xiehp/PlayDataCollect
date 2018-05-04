package xie.common.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * JSON工具
 * 
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XJsonUtil {
	public final static String SHORT_DATE_PATTERN = "yyyy-MM-dd";
	public final static String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public final static String TIMSTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSSSS";

	private static final Logger LOG = LoggerFactory.getLogger(XJsonUtil.class);
	private static ObjectMapper objectMapper;

	public static void main(String[] args) throws Exception {
		XObjectMapperFactoryBean xObjectMapperFactoryBean = new XObjectMapperFactoryBean();
		System.out.println(getObjectMapper().equals(xObjectMapperFactoryBean.getObject()));
	}

	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			XObjectMapperFactoryBean xObjectMapperFactoryBean = new XObjectMapperFactoryBean();
			try {
				objectMapper = xObjectMapperFactoryBean.getObject();
			} catch (Exception e) {
				LOG.error("获得objectMapper出错", e);
			}
		}

		return objectMapper;
	}

	/**
	 * 
	 * 设定时间格式
	 * 
	 * @param pattern 时间格式
	 */
	public static void setDatePattern(final String pattern) {
		getObjectMapper().setDateFormat(new SimpleDateFormat(pattern, Locale.getDefault()));
	}

	/**
	 * 
	 * 把对象转化成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(final Object obj) {
		String str = null;
		try {
			if (obj != null) {
				str = getObjectMapper().writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			LOG.error("", e);
		}
		return str;
	}

	public static Map<String, Object> fromJsonString(final String jsonStr) {
		Map<String, Object> obj = null;
		try {
			if (jsonStr != null) {
				obj = getObjectMapper().readValue(jsonStr, LinkedHashMap.class);
			}
		} catch (IOException e) {
			LOG.error("", e);
			e.printStackTrace();
		}
		return obj;
	}

	public static <X> X fromJsonString(final String jsonStr, final TypeReference<X> typeReference) {
		X obj = null;
		try {
			if (jsonStr != null) {
				obj = getObjectMapper().readValue(jsonStr, typeReference);
			}
		} catch (IOException e) {
			LOG.error("", e);
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <X> X fromJsonString(final String jsonStr, final Class<X> clazz) {
		JavaType javaType = getJavaType(clazz);
		return (X) fromJsonString(jsonStr, javaType);
	}

	/**
	 * 
	 * 把Json字符串转化在对象
	 * 
	 * @param jsonStr JOSN字符串
	 * @param javaType
	 * @return
	 */
	public static Object fromJsonString(final String jsonStr, final JavaType javaType) {
		Object obj = null;
		try {
			if (jsonStr != null) {
				obj = getObjectMapper().readValue(jsonStr, javaType);
			}
		} catch (IOException e) {
			LOG.error("", e);
			e.printStackTrace();
		}
		return obj;
	}

	public static JavaType getJavaType(final Class<?> clazz) {
		return getObjectMapper().getTypeFactory().constructType(clazz);
	}

	public static JavaType getJavaType(final Type type, final Class<?> contextClass) {
		return getObjectMapper().getTypeFactory().constructType(type, contextClass);
	}

	public static JavaType getCollectionJavaType(final Class<? extends Collection> collectionClass, final Class<?> elecmentClass) {
		return getObjectMapper().getTypeFactory().constructCollectionType(collectionClass, elecmentClass);
	}

	public static JavaType getCollectionJavaType(final Class<? extends Collection> collectionClass, final JavaType javaType) {
		return getObjectMapper().getTypeFactory().constructCollectionType(collectionClass, javaType);
	}

	public static JavaType getMapJavaType(final Class<? extends Map> mapClass, final Class<?> keyClass, final Class<?> valueClass) {
		return getObjectMapper().getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
	}

	public static JavaType getMapJavaType(final Class<? extends Map> mapClass, final JavaType keyType, final JavaType valueType) {
		return getObjectMapper().getTypeFactory().constructMapType(mapClass, keyType, valueType);
	}
}
