package util;

import java.util.*;

/**
 * Created by hwyang on 2014/11/11.
 */
public class TableMap<K, V> implements Iterator<AbstractMap.SimpleEntry>,Iterable<AbstractMap.SimpleEntry>{
    private Map<K, List<V>> map;

    public TableMap() {
        map = new HashMap<>();
    }

    public void put(K key, V value) {
        List<V> vs = map.get(key);
        if (vs == null) {
            vs = new ArrayList<>();
            map.put(key, vs);
        }
        vs.add(value);
    }

    public List<V> get(K key) {
        return map.get(key);
    }

    @Override
    public Iterator<AbstractMap.SimpleEntry> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public AbstractMap.SimpleEntry next() {
        return null;
    }

    @Override
    public void remove() {

    }
}
