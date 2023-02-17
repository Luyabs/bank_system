package utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类：根据向导生成的toString方法 将String还原成实体类的成员序列
 * 在实现反射自动赋值后 这个类只为ToEntity类服务
 */
public class BaseToEntity {
    /**
     * 将String转换成实体对象属性片段
     * obj="Card{id=1, password="abc"}" => strs=[1, abc]
     * @param obj 符合由向导生成toString()的String
     * @return strs 切片的属性值数组 如
     */
    private static String[] toStrings(String obj) {
        String[] strs = new String[0];
        try {
            strs = obj.substring(obj.indexOf("{") + 1, obj.length() - 1).split(", ");
            for (int i = 0; i < strs.length; i++) {
                strs[i] = strs[i].substring(strs[i].indexOf("=") + 1);
                if (strs[i].startsWith("'"))
                    strs[i] = strs[i].substring(1, strs[i].length() - 1);
                if (strs[i].equals("null"))
                    strs[i] = null;
            }
        } catch (StringIndexOutOfBoundsException ignored) {

        }
        return strs;
    }

    /**
     * 通过Java反射使得可以根据String与变量类型，自动将String转换回实体类对象
     * @param type 实体类 请传入xxx.class
     * @param str 对象的字符串形式
     * @return 转换后的实体类
     */
    public static Object toEntity(Class type, String str) {
        Object obj = null;
        try {
            obj = type.getConstructor().newInstance();      //按class获取实例
            Field[] fields = obj.getClass().getDeclaredFields();    //获取属性对应列表
            String[] strs = toStrings(str);     //拆分原始String串
            if (strs.length == 0)   //打补丁 防空异常
                return null;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);      //强制给private属性赋值
                if (fields[i].getType().isAssignableFrom(int.class) || fields[i].getType().isAssignableFrom(Integer.class))
                    fields[i].set(obj, Integer.parseInt(strs[i]));
                else if (fields[i].getType().isAssignableFrom(double.class) || fields[i].getType().isAssignableFrom(Double.class))
                    fields[i].set(obj, Double.parseDouble(strs[i]));
                else if (fields[i].getType().isAssignableFrom(boolean.class) || fields[i].getType().isAssignableFrom(Boolean.class))
                    fields[i].set(obj, Boolean.parseBoolean(strs[i]));
                else if (fields[i].getType().isAssignableFrom(long.class) || fields[i].getType().isAssignableFrom(Long.class))
                    fields[i].set(obj, Long.parseLong(strs[i]));
                else
                    fields[i].set(obj, strs[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 通过Java反射使得可以根据String与变量类型，自动将String转换回实体类对象列表
     * @param type 实体类 请传入xxx.class
     * @param str 列表形式的toString
     * @return 列表
     * @param <T> 实体类
     */
    public static<T> List<T> toEntityList(Class type, String str) {
        List<T> objects = null;
        try {
            objects = new ArrayList<>();
            str = str.substring(1, str.length() - 1);
            String[] strs = str.split("}, ");
            for (int i = 0; i < strs.length - 1; i++) {
                strs[i] += "}";
            }
            for (String s : strs) {
                objects.add((T) toEntity(type, s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }
}
