package yangUtil;

import util.GroupInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 集合操作工具类
 * Created by hwyang on 2015/1/6.
 */
public class CollectionYangUtil {

    public static <E, R> Map<R, E> listToMap(List<E> list, String filedName, Class<R> clazz) {
        Map<R, E> map = new HashMap<>();
        for (E e : list) {
            R o = RegectYangUtil.getPrivateVariable(e, filedName, clazz);
            map.put(o, e);
        }
        return map;
    }


    public static <K, V extends Number> void sortMapByValue(Map<K, V> map, final boolean order) {
        assert map instanceof LinkedHashMap;
        ArrayList<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());
        map.clear();
        Collections.sort(entryList, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                double i = o2.getValue().doubleValue() * 1000 - o1.getValue().doubleValue() * 1000;
                if (!order) {
                    i = i * -1;
                }
                return i > 0 ? 1 : (i < 0 ? -1 : 0);
            }
        });
        for (Map.Entry<K, V> entry : entryList) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    public static <K, V> Map<K, Collection<V>> sortMapBySize(Map<K, Collection<V>> map, final boolean asc) {
        if (!(map instanceof LinkedHashMap)) {
            map = new LinkedHashMap<>(map);
        }
        List<Map.Entry<K, Collection<V>>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<K, Collection<V>>>() {
            @Override
            public int compare(Map.Entry<K, Collection<V>> o1, Map.Entry<K, Collection<V>> o2) {
                if (asc) {
                    return o2.getValue().size() - o1.getValue().size();
                } else {
                    return o1.getValue().size() - o2.getValue().size();
                }
            }
        });
        map.clear();
        for (Map.Entry<K, Collection<V>> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


    /**
     * 集合的差集
     */
    public static <V> Collection<V> collectionRemoveAll(Collection<V> c1, Collection<V> c2) {
        Collection<V> temp = new ArrayList<>();
        temp.addAll(c1);
        temp.removeAll(c2);
        return temp;
    }

    /**
     * 集合的交集
     */
    public static <V> Collection<V> collectionRetainAll(Collection<V> c1, Collection<V> c2) {
        Collection<V> temp = new ArrayList<>();
        temp.addAll(c1);
        temp.retainAll(c2);
        return temp;
    }

    /**
     * 集合的和集
     */
    public static <V> Collection<V> collectionAddAll(Collection<V> c1, Collection<V> c2) {
        Collection<V> temp = new HashSet<>();
        temp.addAll(c1);
        temp.addAll(c2);
        return temp;
    }

    /**
     * 给集合分组
     */
    public static <K, G> Map<G, Collection<K>> groupBySelf(Collection<K> ks, GroupInterface<G, K> kf) {
        Map<G, Collection<K>> map = new HashMap<>();
        for (K k : ks) {
            G g = kf.generateGroup(k);
            Collection<K> list = map.get(g);
            if (list == null) {
                list = new ArrayList<>();
                map.put(g, list);
            }
            list.add(k);
        }
        return map;
    }

    public static <V, R> Map<R, Collection<V>> groupByParameter(Collection<V> coll, String parameter, Class<R> clzz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<R, Collection<V>> map = new HashMap<>();
        for (V o : coll) {
            Object retVal = RegectYangUtil.getFiledByGetMethod(o, parameter);
            R group = clzz.cast(retVal);
            Collection<V> objects = map.get(group);
            if (objects == null) {
                objects = new HashSet<>();
                map.put(group, objects);
            }
            objects.add(o);
        }
        return map;
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Test> list = new ArrayList<>();
        Map<String, Collection<Test>> objectCollectionMap = groupByParameter(list, "name", String.class);
    }

    class Test {
        private String name;
        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
