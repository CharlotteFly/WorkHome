package yangUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by hwyang on 2016/1/6.
 */
public class YangPatternUtil {
    @SuppressWarnings (value={"unchecked"})
    public static Map<String, Integer> namedGroups(Pattern instance) {
        if (instance == null) {
            return null;
        }
        Method namedGroupsMethod = null;
        try {
            namedGroupsMethod = Pattern.class.getDeclaredMethod("namedGroups");
            namedGroupsMethod.setAccessible(true);
            Map<String, Integer> result = (Map<String, Integer>) namedGroupsMethod.invoke(instance);
            if (result == null) {
                return null;
            }
            return result;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }finally {
            if (namedGroupsMethod != null) {
                namedGroupsMethod.setAccessible(false);
            }
        }
    }
}
