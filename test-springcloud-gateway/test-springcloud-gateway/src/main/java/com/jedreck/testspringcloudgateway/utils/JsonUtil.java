package com.jedreck.testspringcloudgateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 单例模式
 */
enum ObjectMapperSingle {
    INSTANCE;
    private final ObjectMapper mapper;

    ObjectMapperSingle() {
        this.mapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}

public class JsonUtil {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = ObjectMapperSingle.INSTANCE.getMapper();

    /**
     * 将对象转换成json字符串
     */
    public static String object2Json(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象
     */
    public static <T> T json2Object(String json, Class<T> beanType) {
        try {
            return MAPPER.readValue(json, beanType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成List
     */
    public static <T> List<T> json2List(String json, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 示例1：
     * List<Map<Integer, String>> list = Lists.newArrayList();
     * Map<Integer, String> map = Maps.newHashMap();
     * map.put(12, "gg");
     * map.put(34, "sd");
     * list.add(map);
     * List<Map<Integer, String>> list1 = toJavaBean(JSONUtils.toJSONString(list), ArrayList.class, HashMap.class);
     * 示例2：
     * Map<Integer, String> map = Maps.newHashMap();
     * map.put(12, "gg");
     * map.put(34, "sd");
     * Map<Integer, String> map1 = toJavaBean(JSONUtils.toJSONString(map), HashMap.class, Integer.class, String.class);
     *
     * @param jsonString         JSON字符串
     * @param wrapClassType      数据类型最外层class或者泛型实际的class, 如List<Map<String, Integer>>的List.class 或者Map<String, Integer>中的Map.class
     * @param parameterClassType 参数内部类型，如List<Map<String, Object>中的Map.class 或者Map<String, Integer>中的String.class、Integer.class
     */
    public static <T> T json2Complex(String jsonString, Class<?> wrapClassType, Class<?>... parameterClassType) {
        try {
            JavaType javaType = javaType(wrapClassType, parameterClassType);
            return MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 示例1：
     * Map<Integer, String> map = Maps.newHashMap();
     * JavaType javaType = javaType(Map.class, Integer.class, String.class);
     * 示例2：
     * List<Map<Integer, String>> list = Lists.newArrayList();
     * Map<Integer, String> map = Maps.newHashMap();
     * map.put(12, "gg");
     * map.put(34, "sd");
     * list.add(map);
     * JavaType javaType = javaType(List.class, Map.class);
     *
     * @param parametrized     实际的数据类型，即最外层数据类型List
     * @param parameterClasses 内部参数类型，即Set.class Bean.class
     * @return
     */
    public static JavaType javaType(Class<?> parametrized, Class<?>... parameterClasses) {
        return MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }
}
