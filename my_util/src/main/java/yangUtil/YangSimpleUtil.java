package yangUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Created by hwyang on 2015/4/15.
 */
public class YangSimpleUtil {

    public static boolean isEmptyString(String str) {
        return str == null || str.equals("");
    }

    public static boolean isEmptyCollection(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static List<Integer> range(int r) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<Integer> range(int star, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = star; i < end; i++) {
            list.add(i);
        }
        return list;
    }

    public static boolean isContains(Integer start1, Integer end1, Integer start2, Integer end2) {
        return !(start1 >= end2 || end1 <= start2);
    }

}
