package yangUtil;

import java.util.*;

/**
 * Created by hwyang on 2015/5/11.
 */
public class MultipleCounter {
    private String name;
    private int count;
    private MultipleCounter parent;
    private Map<String, MultipleCounter> treeMap = new TreeMap<>();

    public MultipleCounter getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public Map<String, MultipleCounter> getTreeMap() {
        return treeMap;
    }

    public MultipleCounter(String name, MultipleCounter parent) {
        this.parent = parent;
        this.name = name;
    }

    public void init(List<String> children, MultipleCounter parent) {
        for (String child : children) {
            treeMap.put(child, new MultipleCounter(child, parent));
        }
    }

    public void put(String... parameters) {
        try {
            if (parameters != null && parameters.length != 0) {
                String parameter = parameters[0];
                MultipleCounter multipleCounter = treeMap.get(parameter);
                if (multipleCounter == null) {
                    multipleCounter = new MultipleCounter(parameter, this);
                    treeMap.put(parameter, multipleCounter);
                }
                multipleCounter.put(Arrays.copyOfRange(parameters, 1, parameters.length));
            } else {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MultipleCounter get(String name) {
        return treeMap.get(name);
    }

    public void output() {
        output(this, new LinkedList<String>());
    }

    public void output(MultipleCounter multipleCounter, LinkedList<String> list) {
        Collection<MultipleCounter> values = multipleCounter.getTreeMap().values();
        for (MultipleCounter value : values) {
            list.add(multipleCounter.name);
            if (value.getTreeMap().isEmpty()) {
                System.out.println(list.toString() + value.name + " " + value.count);
            }
            output(value, list);
        }
        list.pollLast();
    }

    public void output2() {
        output2(this, null, new LinkedList<String>());
    }

    public void output2(MultipleCounter multipleCounter, Collection<String> allKeys, LinkedList<String> list) {
        Collection<String> keys = getAllKeys(multipleCounter);//所有当前层次的结点
        for (String key : keys) {
            MultipleCounter counter = multipleCounter.get(key);
            if (counter == null) {
                String outputStr = generateString(list, key, counter.count);
                System.out.println(outputStr);
            }
            list.add(key);
            output2(counter, keys, list);
        }
        list.pollLast();
    }

    private String generateString(LinkedList<String> list, String key, int count) {
        StringBuilder builder = null;
        for (String s : list) {
            if (builder == null) {
                builder = new StringBuilder(s);
            } else {
                builder.append("\t").append(s);
            }
        }
        if (builder == null) {
            builder = new StringBuilder();
        }
        builder.append("\t").append(key).append("\t").append(count);
        return builder.toString();
    }

    private static Collection<String> getAllKeys(MultipleCounter multipleCounter) {
        int i = 0;
        MultipleCounter parent = multipleCounter;
        while ((parent = parent.getParent()) != null) {
            i++;
        }
        Collection<MultipleCounter> children = getChildren(multipleCounter, i);
        Collection<String> set = new TreeSet<>();
        for (MultipleCounter child : children) {
            set.add(child.getName());
        }
        return set;
    }

    private static Collection<MultipleCounter> getChildren(MultipleCounter multipleCounter, int num) {
        if (num <= 0) {
            return multipleCounter.getTreeMap().values();
        }
        Collection<MultipleCounter> set = new TreeSet<>();
        for (MultipleCounter counter : multipleCounter.getTreeMap().values()) {
            Collection<MultipleCounter> children = getChildren(counter, --num);
            set.addAll(children);
        }
        return set;
    }

    private static Collection<String> getAllKeys2(MultipleCounter multipleCounter) {
        Collection<String> set = new TreeSet<>();
        if (multipleCounter == null) {
            return set;
        }
        for (MultipleCounter counter : multipleCounter.treeMap.values()) {
            set.addAll(counter.treeMap.keySet());
        }
        return set;
    }


    public static MultipleCounter generateMultipleCounter(List<List<String>> forms) {
        MultipleCounter head = new MultipleCounter("head", null);
        init(head, forms.listIterator());
        return head;
    }

    public static void init(MultipleCounter head, ListIterator<List<String>> iterator) {
        if (iterator.hasNext()) {
            List<String> current = iterator.next();
            head.init(current, head);
            Collection<MultipleCounter> values = head.getTreeMap().values();
            for (MultipleCounter value : values) {
                init(value, iterator);
            }
            iterator.previous();
        }
    }


    public static void main(String[] args) {
        MultipleCounter head = new MultipleCounter("head", null);
//        MultipleCounter head = MultipleCounter.generateMultipleCounter(Arrays.asList(Arrays.asList("酒店一", "酒店二"), Arrays.asList("中文", "英文"), Arrays.asList("好评", "差评")));
        head.put("酒店一", "中文", "好评");
        head.put("酒店一", "英文", "好评");
        head.put("酒店二", "中文", "好评");
        head.put("酒店一", "中文", "好评");
        head.put("酒店一", "中文", "差评");
        head.put("酒店一", "日语", "中评");
        head.output2();
        System.out.println();
    }
}
