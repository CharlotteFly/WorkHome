package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwyang on 2014/8/18.
 *
 * @author hwyang
 */
public class TestClass {
    public static void main(String[] args) throws IOException {
        System.out.println("         ");
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < 40; i++) {
            map.put(i, "test");
        }
        YangUtil.writeToFile("C:\\temp\\20140818\\test.data", map.entrySet(),new LineGenerator() {
            @Override
            public String generateWritLine(Object o) {
                Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) o;
                return entry.getKey() + entry.getValue();
            }
        });
    }
}
