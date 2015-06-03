package yangUtil;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hwyang on 2015/5/25.
 */
public class MultiMap {
    private int level;
    private Float count;
    private String name;
    private Map<String, MultiMap> map;

    public MultiMap(int level, String name) {
        this.level = level;
        this.name = name;
        if (level > 0) {
            map = new HashMap<>();
        } else {
            count = Float.valueOf(0);
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, MultiMap> getMap() {
        return map;
    }

    public float getCount() {
        return count;
    }

    public void put(String... keys) {
        put(1, keys);
    }

    public void put(float value, String... keys) {
        assert keys.length == this.level;
        if (level == 0) {
            this.count += value;
        } else {
            String parameter = keys[0];
            MultiMap multiMap = map.get(parameter);
            if (multiMap == null) {
                multiMap = new MultiMap(this.level - 1, parameter);
                map.put(parameter, multiMap);
            }
            multiMap.put(Arrays.copyOfRange(keys, 1, keys.length));
        }
    }

    public float get(String... keys) {
        assert keys.length == this.level;
        if (this.level == 0) {
            return this.count;
        }
        String parameter = keys[0];
        MultiMap multiMap = map.get(parameter);
        if (multiMap == null) {
            return 0;
        }
        return multiMap.get(Arrays.copyOfRange(keys, 1, keys.length));
    }

    public void getAllChildren(List<MultiMap> list) {
        if (this.level == 1) {
            list.addAll(this.map.values());
            return;
        }
        list.addAll(this.map.values());
        for (MultiMap multiMap : this.map.values()) {
            multiMap.getAllChildren(list);
        }
    }

    public float getAllCount() {
        if (map == null) {
            return this.count;
        }
        int total = 0;
        for (Map.Entry<String, MultiMap> entry : map.entrySet()) {
            total += entry.getValue().getAllCount();
        }
        return total;
    }

    public String asString() {
        StringBuilder builder = null;
        for (Map.Entry<String, MultiMap> entry : map.entrySet()) {
            if (builder == null) {
                builder = new StringBuilder();
            } else {
                builder.append(",");
            }
            builder.append(entry.getKey()).append(":").append(entry.getValue().getAllCount());
        }
        return builder == null ? "" : String.format("{%s}", builder.toString());
    }
//    public void output() {
//        LinkedList<MultiMap> list = new LinkedList<>();
//        getAllChildren(list);
//        Map<Integer, Set<String>> listMap = new TreeMap<>();
//        for (MultiMap multiMap : list) {
//            int level = multiMap.level;
//            Set<String> mapList = listMap.get(level);
//            if (mapList == null) {
//                mapList = new TreeSet<>();
//                listMap.put(level, mapList);
//            }
//            mapList.add(multiMap.name);
//        }
//        Set<String> names = listMap.get(0);
//        StringBuilder builder = new StringBuilder("\t\t\t");
//        for (String name : names) {
//            builder.append(name).append("\t");
//        }
//        if (writer == null) {
//            System.out.println(builder.toString());
//        }else{
//            writer.println(builder.toString());
//        }
//        print(this.level, this.name,listMap,new LinkedList<String>());
//        if (writer != null) {
//            writer.close();
//        }
//        System.out.println();
//    }

    static Gson gson = new Gson();
//    private void print(int level,String name,Map<Integer, Set<String>> listMap, LinkedList<String> list) {
//        Set<String> multiMaps = listMap.get(level - 1);
//        list.add(name);
//        if (level == 1) {
//            StringBuilder builder = new StringBuilder();
//            for (String word : list) {
//                builder.append(word).append("\t");
//            }
//            String[] temp = new String[list.size()];
//            list.subList(1, list.size()).toArray(temp);
//            for (String multiMap : multiMaps) {
//                temp[temp.length - 1] = multiMap;
//                float count = this.get(temp);
//                builder.append(count).append("\t");
//            }
//            if (writer == null) {
//                System.out.println(builder.toString());
//            }else{
//                writer.println(builder.toString());
//            }
//        }else {
//            for (String multiMap : multiMaps) {
//                print(level - 1, multiMap, listMap, list);
//            }
//        }
//        list.pollLast();
//    }

    public void outputByJson(PrintWriter writer) {
        for (Map.Entry<String, MultiMap> entry : this.map.entrySet()) {
            writer.println(gson.toJson(entry.getValue()));
        }
        writer.close();
    }

    public static void main(String[] args) {
        MultiMap counter3 = new MultiMap(3, "head");
        counter3.put("中国", "辽宁", "沈阳");
        counter3.put("中国", "辽宁", "沈阳");
        counter3.put("中国", "辽宁", "大连");
        counter3.put("中国", "山东", "青岛");
        counter3.put("中国", "山东", "青岛");

        ArrayList<MultiMap> list = new ArrayList<>();
        counter3.getAllChildren(list);
//        counter3.output();
        Gson gson = new Gson();
        for (Map.Entry<String, MultiMap> entry : counter3.getMap().entrySet()) {
            System.out.println(gson.toJson(entry.getValue()));
        }
        float count = counter3.get("中国", "山东", "青岛");
        System.out.println(count);
        System.out.println();
    }
}
