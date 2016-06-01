package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwyang on 2015/1/28.
 */
public class CustomClass {
    private Map<String, Object> map;

    public CustomClass(String... paramters) {
        map = new HashMap<>();
    }

    public void put(String k, Object v) {
        map.put(k, v);
    }

    public Object getAsObject(String k) {
        return map.get(k);
    }

    public Integer getAsInteger(String k) {
        Object o = map.get(k);
        if (o == null) {
            return null;
        }
        return (Integer) o;
    }

    public String getAsString(String k) {
        Object o = map.get(k);
        if (o == null) {
            return null;
        }
        return (String) o;
    }

}
