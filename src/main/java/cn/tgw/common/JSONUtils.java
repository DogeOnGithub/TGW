package cn.tgw.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {




        // 定义jackson对象
        private static final ObjectMapper MAPPER = new ObjectMapper();


        /**
         * 将对象转换成json字符串。普通对象或者list对象都可以
         * <p>Title: pojoToJson</p>
         * <p>Description: </p>
         * @param data
         * @return
         */
        public static String objectToJson(Object data) {
            try {
                String string = MAPPER.writeValueAsString(data);
                return string;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * &#x5c06;json&#x7ed3;&#x679c;&#x96c6;&#x8f6c;&#x5316;&#x4e3a;&#x5bf9;&#x8c61;
         *
         * @param  beanType&#x6570;&#x636e;
         * @param   &#x5bf9;&#x8c61;&#x4e2d;&#x7684;object&#x7c7b;&#x578b;
         * @return
         */
        public static <T> T jsonToPojo(Object o, Class<T> beanType) {
            try {
                T t = JSONObject.toJavaObject(JSON.parseObject(o.toString()), beanType);
                return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         *        将json结果集转化为对象
         * @param o json数组的toString形式
         * @param class1 java bean 的类类型
         * @param <T>
         * @return
         */
        public static <T> List<T> getListByJsonArray(Object o,Class<T> class1) {
            String jArrayStr=o.toString();
            List<T> list = new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(jArrayStr);
            if (jsonArray==null || jsonArray.isEmpty()) {
                return list;//nerver return null
            }
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                T t = JSONObject.toJavaObject(jsonObject, class1);
                list.add(t);
            }
            return list;
        }

    /**
     * 从list数组转为json数组
     * @param list
     * @return
     */
    public static JSONArray listToJsonArray(List<?>list){
            return JSONArray.parseArray(net.minidev.json.JSONArray.toJSONString(list));
        }

    /**
     * +通过key值来获取存储在redis中的json数据
     * @param o
     * @return
     */
    public static JSONObject getJsonObjectByRedisKey(Object o){
           return JSON.parseObject(o.toString());
        }


}