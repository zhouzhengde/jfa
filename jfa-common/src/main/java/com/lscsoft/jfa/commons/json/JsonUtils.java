/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.json;

import com.jayway.jsonpath.JsonPath;
import com.lscsoft.jfa.commons.json.annotation.JsonPathValue;
import com.lscsoft.jfa.commons.util.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * JSON操作工具
 *
 * @author Bond(China)
 * @version 1.0.0
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    /**
     * 将JSON对象转换成JavaBean
     *
     * @param jsonObject
     * @param clz
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T toBean(JSONObject jsonObject, Class<T> clz) {

        if (jsonObject == null) {
            return null;
        }

        T instance = null;
        try {
            instance = clz.newInstance();

            Field[] fields = clz.getDeclaredFields();

            if (fields == null || fields.length == 0) {
                return instance;
            }

            for (Field field : fields) {

                injectJsonValue(jsonObject, instance, field);
            }
        } catch (Exception e) {
            LOGGER.warn("[JsonUtils toBean]", e);
        }
        return instance;
    }

    /**
     * Inject value to attribute of instance
     *
     * @param jsonObject
     * @param instance
     * @param field
     * @param <T>
     */
    private static <T extends Serializable> void injectJsonValue(JSONObject jsonObject, T instance, Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();

        if (annotations == null || annotations.length == 0) {
            return;
        }

        for (Annotation annotation : annotations) {

            if (annotation.annotationType().equals(JsonPathValue.class)) {

                JsonPathValue value = (JsonPathValue) annotation;
                injectJsonValue(instance, jsonObject, field, value);
            }
        }
    }

    private static void injectJsonValue(Object instance, JSONObject jsonObject, Field field, JsonPathValue jsonPathValue) {

        switch (jsonPathValue.dataType()) {
            case Value:
                injectToValue(instance, jsonObject, field, jsonPathValue);
                break;
            case Object:
                injectToObject(instance, jsonObject, field, jsonPathValue);
                break;
            case List:
                injectToList(instance, jsonObject, field, jsonPathValue);
                break;
            case Date:
                injectToDate(instance, jsonObject, field, jsonPathValue);
                break;
            default:
                LOGGER.warn("[JsonPathValue], dataType is undefined");
                break;
        }
    }

    /**
     * Inject To date
     *
     * @param instance
     * @param jsonObject
     * @param field
     * @param jsonPathValue
     */
    private static void injectToDate(Object instance, JSONObject jsonObject, Field field, JsonPathValue jsonPathValue) {
        try {
            Object value = getValue(jsonObject, getPath(jsonPathValue));
            String pattern = jsonPathValue.pattern();
            if (field.getType().equals(Date.class)) {
                if (value instanceof String) {
                    value = DateUtils.parse((String) value, pattern);
                } else if (value instanceof Integer) {
                    value = new Date((Integer) value);
                } else if (value instanceof Long) {
                    value = new Date((Long) value);
                }
            }
            injectValue(instance, field, value);
        } catch (Exception e) {
            LOGGER.warn("[JsonPathValue], injectToDate", e);
        }
    }

    /**
     * Inject to List
     *
     * @param instance
     * @param jsonObject
     * @param field
     * @param jsonPathValue
     */
    private static void injectToList(Object instance, JSONObject jsonObject, Field field, JsonPathValue jsonPathValue) {

        try {
            if (!field.getType().isAssignableFrom(List.class)) {
                LOGGER.warn("Not match data type");
                return;
            }

            Type genType = field.getGenericType();

            if (genType == null) {
                LOGGER.warn("The List attribute not genericType");
                return;
            }

            Class<Serializable> genClz = null;
            if (genType instanceof ParameterizedType) {
                genClz = (Class<Serializable>) ((ParameterizedType) genType).getActualTypeArguments()[0];
            }

            if (genClz != null) {
                injectToList(instance, jsonObject, field, jsonPathValue, genClz);
            }
        } catch (Exception e) {
            LOGGER.warn("[JsonPathValue], injectToList", e);
        }
    }

    private static void injectToList(Object instance, JSONObject jsonObject, Field field, JsonPathValue jsonPathValue, Class<Serializable> genClz) {
        Object arrayObject = getValue(jsonObject, getPath(jsonPathValue));
        if (arrayObject == null) {
            return;
        }
        List rsList = new ArrayList();
        JSONArray jsonArray = null;
        if (arrayObject instanceof JSONArray) {

            jsonArray = (JSONArray) getValue(jsonObject, getPath(jsonPathValue));
            rsList = injectToListByClass(jsonArray, genClz);
            injectValue(instance, field, rsList);
        } else if (arrayObject instanceof JSONObject) {

            JSONObject jsonObject2 = (JSONObject) arrayObject;
            Set<Object> keys = jsonObject2.keySet();
            if (keys.size() == 1) {
                arrayObject = getOneInArray(jsonObject2);
            }
            Object childObject = toBean((JSONObject) arrayObject, genClz);
            rsList.add(childObject);
            injectValue(instance, field, rsList);
        } else {
            rsList.add(arrayObject);
            injectValue(instance, field, rsList);
        }
    }

    /**
     * Get on object in array
     *
     * @param jsonObject2
     * @return
     */
    private static Object getOneInArray(JSONObject jsonObject2) {

        Iterator<Object> iterator = jsonObject2.keys();
        Object rs = null;
        while (iterator.hasNext()) {
            Object key = iterator.next();
            rs = jsonObject2.get(key);
        }
        return rs;
    }

    /**
     * Inject to List By genClz
     *
     * @param jsonArray
     * @param genClz
     * @return
     */
    private static List injectToListByClass(JSONArray jsonArray, Class<Serializable> genClz) {

        if (jsonArray == null || jsonArray.isEmpty()) {
            return Collections.emptyList();
        }

        List rsList = new ArrayList();

        for (Object object : jsonArray) {
            if (genClz.isPrimitive() || genClz.equals(String.class)) {
                rsList.add(object);
                continue;
            }
            Object childObject = toBean((JSONObject) object, genClz);
            rsList.add(childObject);
        }
        return rsList;
    }

    /**
     * Inject to an Object
     *
     * @param instance
     * @param jsonObject
     * @param field
     * @param jsonPathValue
     */
    private static void injectToObject(Object instance, JSONObject jsonObject, Field field, JsonPathValue jsonPathValue) {
        try {

            JSONObject subJsonObject = JsonPath.read(jsonObject, getPath(jsonPathValue));

            Object value = toBean(subJsonObject, (Class<Serializable>) field.getType());

            injectValue(instance, field, value);

        } catch (Exception e) {
            LOGGER.warn("[JsonPathValue injectToValue]", e);
        }
    }

    /**
     * Inject basic type value
     *
     * @param instance
     * @param jsonObject
     * @param field
     * @param jsonPathValue
     */
    private static void injectToValue(Object instance, JSONObject jsonObject, Field field, JsonPathValue jsonPathValue) {
        try {
            Object value = getValue(jsonObject, getPath(jsonPathValue));
            injectValue(instance, field, value);
        } catch (Exception e) {
            LOGGER.warn("[JsonPathValue injectToValue]", e);
        }
    }

    /**
     * Inject Value
     *
     * @param instance
     * @param field
     * @param value
     */
    private static void injectValue(Object instance, Field field, Object value) {

        try {
            Class<?> type = field.getType();
            field.setAccessible(true);
            if (value != null) {
                field.set(instance, covert(type, value));
            }
        } catch (Exception e) {
            LOGGER.error("[JsonUtils Inject Value]", e);
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * Covert type & value
     *
     * @param type
     * @param value
     * @return
     */
    private static Object covert(Class<?> type, Object value) {
        return value;
    }


    /**
     * Get value in jsonObject
     *
     * @param jsonObject
     * @param path
     * @return
     */
    private static Object getValue(JSONObject jsonObject, String path) {

        try {
            return JsonPath.read(jsonObject, path);
        } catch (Exception e) {
            LOGGER.warn("[JsonUtils getValue]", e);
            return null;
        }
    }

    /**
     * Get path value
     *
     * @param jsonPathValue
     * @return
     */
    private static String getPath(JsonPathValue jsonPathValue) {
        return "$." + jsonPathValue.path();
    }
}