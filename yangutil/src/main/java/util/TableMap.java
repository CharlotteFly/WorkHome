package util;

import yangUtil.YangCollectionUtil;

import java.util.*;

/**
 * Created by hwyang on 2014/11/11.
 */
public class TableMap<R, C, V> {
    private Map<R, List<V>> rowMap = new HashMap<>();
    private Map<C, List<V>> colMap = new HashMap<>();

    public List<R> getRows() {
        return new ArrayList<>(rowMap.keySet());
    }

    public List<C> getCols() {
        return new ArrayList<>(colMap.keySet());
    }

    public List<V> getValues(R row, C col) {
        List<V> rvs = rowMap.get(row);
        List<V> cvs = colMap.get(col);
        if (rvs == null && cvs == null) {
            return Collections.EMPTY_LIST;
        }
        List<V> vs = YangCollectionUtil.collectionRetainAll(rvs, cvs);
        if (vs == null || vs.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return vs;
    }

    public V getValue(R row, C col) {
        List<V> values = getValues(row, col);
        if (values == null || values.isEmpty()) {
            return null;
        }
        assert values.size() == 1;
        return values.get(0);
    }

    public void put(R row, C col, V value) {
        putToMap(colMap, col, value);
        putToMap(rowMap, row, value);
    }

    private <K> void putToMap(Map<K, List<V>> map, K key, V value) {
        List<V> values = map.get(key);
        if (values == null) {
            values = new ArrayList<>();
            map.put(key, values);
        }
        values.add(value);
    }
}