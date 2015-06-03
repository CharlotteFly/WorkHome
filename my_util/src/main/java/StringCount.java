import java.util.HashMap;

/**
 * 字符串数量统计
 * Created by hwyang on 2015/3/8.
 */
public class StringCount extends HashMap<String, Integer> {

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
