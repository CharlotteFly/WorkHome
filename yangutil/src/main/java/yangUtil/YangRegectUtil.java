package yangUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射工具类
 * Created by hwyang on 2015/1/6.
 */
public class YangRegectUtil {
    public static <R> R getPrivateVariable(Object instance, String fieldName, Class<R> clzz) {
        Field field = null;
        R r;
        try {
            field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            r = clzz.cast(field.get(instance));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
        return r;
    }

    public static Object getFiled(Object instance, String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field field;
        try {
            field = instance.getClass().getDeclaredField(fileName);
            Object result;
            if (field.isAccessible()) {
                result = field.get(instance);
            } else {
                field.setAccessible(true);
                result = field.get(instance);
                field.setAccessible(false);
            }
            return result;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFiledByGetMethod(Object instance, String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return instance.getClass().getDeclaredMethod("get" + captureName(fileName), null).invoke(instance, null);
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
