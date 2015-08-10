import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 字符串数量统计
 * Created by hwyang on 2015/3/8.
 */
public class StringCount extends HashMap<String, Integer> {
    private static class Test {
        public int a;
        public String b;
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Test bean = new Test();
        bean.a = 10;
        bean.b = "a";
        System.out.println(BeanUtils.getSimpleProperty(bean, "b"));
    }

    public void doCount(String content) {
        doCount(content, 1);
    }

    public void doCount(String content, Integer add) {
        Integer count = get(content);
        if (count == null) {
            count = add;
        } else {
            count = add + count;
        }
        put(content, count);
    }
}
