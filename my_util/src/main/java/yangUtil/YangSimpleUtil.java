package yangUtil;

import java.util.Collection;

/**
 * Created by hwyang on 2015/4/15.
 */
public class YangSimpleUtil {

    public static boolean isEmptyString(String str) {
        return str == null || str.equals("");
    }

    public static boolean isEmptyCollection(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
