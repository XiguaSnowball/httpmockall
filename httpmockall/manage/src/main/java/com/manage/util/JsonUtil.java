package com.manage.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
	private static   Logger logger = Logger.getLogger(JsonUtil.class);

	public static String object2string(Object ob){
		String str = "";
		try{
			str = JSON.toJSONString(ob);
		}catch(Exception e){
		}
		return str;
	}

	   /**
     * ����ת���ɶ���
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T object2Object(Object obj, Class<T> clazz) {
        String str = JSON.toJSONString(obj, SerializerFeature.UseSingleQuotes);
        return JSON.parseObject(str, clazz);
//        return JSON.toJavaObject((JSON) obj, clazz);
        //   return JSON.toJavaObject((JSON) JSON.toJSON(obj), clazz);
    }


    /**
     * JSONת�ɶ���
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T json2Object(String jsonString, Class<T> clazz) throws IOException {
        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        return JSON.parseObject(jsonString, clazz);
    }


    /**
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> json2List(String jsonString, Class<T> clazz) throws IOException {
        return JSON.parseArray(jsonString, clazz);
    }


    public static <T> List<T> json2LinkedList(String jsonString, Class<T> clazz) throws IOException {
//      return JSON.parseObject(jsonString, LinkedList.class);
       return JSON.parseArray(jsonString, clazz);
   }

}
