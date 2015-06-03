package util;

import java.util.*;

/**
 * Created by hwyang on 2014/11/11.
 */
public class TableListMap<R, C, V> extends TreeMap<R, Map<C, List<V>>> {
    public void put(R r, C c, V v) {
        Map<C, List<V>> cvMap = get(r);
        if (cvMap == null) {
            cvMap = new HashMap<>();
            put(r, cvMap);
        }
        List<V> vs = cvMap.get(c);
        if (vs == null) {
            vs = new ArrayList<>();
            cvMap.put(c, vs);
        }
        vs.add(v);
    }

    public List<V> get(R r, C c) {
        Map<C, List<V>> cListMap = get(r);
        if (cListMap == null) {
            return null;
        }
        return cListMap.get(c);
    }

    public TableListMap<C, R, V> reverse() {
        TableListMap<C, R, V> crvTableListMap = new TableListMap<>();
        for (Map.Entry<R, Map<C, List<V>>> entry : entrySet()) {
            R r = entry.getKey();
            for (Map.Entry<C, List<V>> innerEntry : entry.getValue().entrySet()) {
                C c = innerEntry.getKey();
                List<V> value = innerEntry.getValue();
                for (V v : value) {
                    crvTableListMap.put(c, r, v);
                }
            }
        }
        return crvTableListMap;
    }

}
