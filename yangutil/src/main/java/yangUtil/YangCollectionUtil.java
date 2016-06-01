package yangUtil;

import handler.BooleanHandler;
import util.GroupInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 集合操作工具类
 * Created by hwyang on 2015/1/6.
 */
public class YangCollectionUtil {

    public static <E, R> Map<R, E> listToMap(List<E> list, String filedName, Class<R> clazz) {
        Map<R, E> map = new HashMap<>();
        for (E e : list) {
            R o = YangRegectUtil.getPrivateVariable(e, filedName, clazz);
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
                Double value2 = o2.getValue().doubleValue();
                Double value1 = o1.getValue().doubleValue();
                if (order) {
                    return value2.compareTo(value1);
                } else {
                    return value1.compareTo(value2);
                }
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
    public static <V> List<V> collectionRetainAll(Collection<V>... c1) {
        List<V> temp = new ArrayList<>();
        for (Collection<V> vs : c1) {
            if (temp.isEmpty()) {
                temp.addAll(vs);
            } else {
                if (vs == null) {
                    continue;
                }
                temp.retainAll(vs);
            }
        }
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
            Object retVal = YangRegectUtil.getFiled(o, parameter);
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

    /**
     * 过滤集合
     */
    public static <E> void collectionFilter(Collection<E> collection, BooleanHandler<E> filter) {
        Iterator<E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            boolean b = filter.doHandler(next);
            if (b) {
                iterator.remove();
            }
        }
    }

    /**
     * 过滤MAP
     */
    public static <K, V> void mapFilter(Map<K, V> map, BooleanHandler<Map.Entry<K, V>> filter) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> next = iterator.next();
            boolean b = filter.doHandler(next);
            if (b) {
                iterator.remove();
            }
        }
    }

    public static <K, V> Map<K, List<V>> listToMapList(List<V> list, String paramter, Class<K> key) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<K, List<V>> map = new HashMap<>();
        for (V v : list) {
            Object filedByGetMethod = YangRegectUtil.getFiled(v, paramter);
            K cast = key.cast(filedByGetMethod);
            List<V> vs = map.get(cast);
            if (vs == null) {
                vs = new ArrayList<>();
                map.put(cast, vs);
            }
            vs.add(v);
        }
        return map;
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        List<Test> list = new ArrayList<>();
//        Map<String, Collection<Test>> objectCollectionMap = groupByParameter(list, "name", String.class);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list);
        collectionFilter(list, new BooleanHandler<String>() {
            @Override
            public boolean doHandler(String s) {
                if (s.equals("a")) {
                    return true;
                }
                return false;
            }
        });
        System.out.println(list);
    }


    public static <E> List<E> getShuffle(List<E> list, int n) {
        ArrayList<E> es = new ArrayList<E>(list);
        Collections.shuffle(es);
        List<E> result = new ArrayList<>();
        for (E e : es) {
            n--;
            if (n < 0) {
                break;
            }
            result.add(e);
        }
        return result;
    }


    public static <K> Map<K, Integer> listToCountMap(List<K> list) {
        Map<K, Integer> map = new LinkedHashMap<>();
        for (K key : list) {
            Integer count = map.get(key);
            if (count == null) {
                map.put(key, 1);
            } else {
                map.put(key, ++count);
            }
        }
        return map;
    }


    public static <C, R, V> void tableOutput(Map<C, Map<R, V>> map, String output) throws IOException {
        Set<R> rs = new HashSet<>();
        for (Map<R, V> rvMap : map.values()) {
            rs.addAll(rvMap.keySet());
        }
        PrintWriter writer = new PrintWriter(new FileWriter(output));
        writer.println("" + "\t" + rs.toString().replace(",", "\t"));
        for (Map.Entry<C, Map<R, V>> entry : map.entrySet()) {
            C key = entry.getKey();
            writer.print(key);
            Map<R, V> value = entry.getValue();
            for (R r : rs) {
                V v = value.get(r);
                writer.print("\t" + v);
            }
            writer.println();
        }

        writer.close();
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
