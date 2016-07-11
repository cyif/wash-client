package com.zju.chen.wash_client.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 * Created by chen on 16/7/9.
 */
public class JacksonUtil {
    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static <T> T parseJson(String jsonString, TypeReference<T> typeOfT) {
        JACKSON_MAPPER.setDateFormat(fmt);
        T t = null;
        try {
            t = (T) JACKSON_MAPPER.readValue(jsonString, typeOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }

    public static String convertObjectToString(Object obj) {
        StringWriter w = new StringWriter();
        String jsonValue = null;
        try {
            JACKSON_MAPPER.writeValue(w, obj);
            jsonValue = w.toString();
        } catch (JsonParseException e) {
            // 异常时，记录日志，不中断程序
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // 异常时，记录日志，不中断程序
            e.printStackTrace();
        } catch (IOException e) {
            // 异常时，记录日志，不中断程序
            e.printStackTrace();
        }
        return jsonValue;
    }
}
