package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hwyang on 2014/8/18.
 *
 * @author hwyang
 */
public class TestClass {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Pattern compile = Pattern.compile("aab(?<k>cc)ee(ff)dd");
        Matcher matcher = compile.matcher("aabcceeffdd");
        Method method = compile.getClass().getDeclaredMethod("namedGroups");
        if (!method.isAccessible()) {
            method.setAccessible(true);
            Map<String, Integer> map = (Map<String, Integer>) method.invoke(compile);
            Integer k = map.get("k");
            matcher.find();
            int start = matcher.start(k);
            int end = matcher.end(k);
            System.out.println("aabcceeffdd".substring(start, end));
            System.out.println();
        }
//        Map<String, Integer> map = compile.namedGroups();
//        System.out.println("         ");
//        Map<Integer, String> map = new HashMap<Integer, String>();
//        for (int i = 0; i < 40; i++) {
//            map.put(i, "test");
//        }
//        YangUtil.writeToFile("C:\\temp\\20140818\\test.data", map.entrySet(),new LineGenerator() {
//            @Override
//            public String generateWritLine(Object o) {
//                Map.Entry<Integer, String> entry = (Map.Encompile.namedGroups()try<Integer, String>) o;
//                return entry.getKey() + entry.getValue();
//            }
//        });
    }
}
