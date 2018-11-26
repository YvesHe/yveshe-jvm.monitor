/**
*
* Copyright:   Copyright (c)2016
* Company:     YvesHe
* @version:    1.0
* Create at:   2018年11月26日
* Description:
*
* Author       YvesHe
*/
package com.yveshe.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

public class JsonUtil {

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    public static <T> List<T> fromJsonToArray(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    public static <T> String toJson(T obj, boolean foramt) {
        return obj == null ? null : JSON.toJSONString(obj, foramt);
    }

    public static <T> String toJson(T obj, String... ignoreProperties) {
        if (obj == null) {
            return null;
        }
        if (ignoreProperties != null && ignoreProperties.length > 0) {
            return JSON.toJSONString(obj, new IgnoreSerializer(ignoreProperties));
        } else {
            return JSON.toJSONString(obj);
        }
    }

    /**
     * 忽略序列化字段过滤器
     *
     * @author YvesHe
     *
     */
    private static class IgnoreSerializer implements PropertyFilter {
        private final String[] ignores;

        public IgnoreSerializer(String[] ignores) {
            this.ignores = ignores;
        }

        public boolean apply(Object object, String propertyName, Object value) {
            if (ignores == null || ignores.length == 0 || propertyName == null) {
                return true;
            }
            for (String i : ignores) {
                if (propertyName.equals(i)) {
                    return false;
                }
            }
            return true;
        }
    }

}
