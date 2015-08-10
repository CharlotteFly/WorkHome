package yangUtil;

import com.google.gson.Gson;
import handler.BooleanHandler;
import handler.VoidHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by hwyang on 2015/3/4.
 */
public class YangDataUtil {

    public static <E> List<E> getDataFromJsonFile(String inputPath, final Class<E> eClass) throws IOException {
        final Gson gson = new Gson();
        final List<E> result = new ArrayList<>();
        YangInputStringUtil.readFile(inputPath, new BooleanHandler<String>() {
            @Override
            public boolean doHandler(String s) {
                try {
                    E e = gson.fromJson(s, eClass);
                    result.add(e);
                } catch (Exception e) {
                    return true;
                }
                return true;
            }
        });
        return result;
    }

    public static <E> void writeToDiskByGsonFile(String output, List<E> datas) throws IOException {
        Gson gson = new Gson();
        PrintWriter printWriter = new PrintWriter(new FileWriter(output));
        for (E data : datas) {
            printWriter.println(gson.toJson(data));
        }
        printWriter.close();
    }

    /**
     * 输出来excel
     */
    public static <C> void writeToExcel(List<C> cList, Class<C> cClass, String output) throws IOException {
        Field[] declaredFields = cClass.getDeclaredFields();
        String[] headers = new String[declaredFields.length];
        for (int i = 0; i < headers.length; i++) {
            headers[i] = declaredFields[i].getName();
        }
        SimpleExcelWriter simpleExcelWriter = new SimpleExcelWriter(output, headers);
        for (C value : cList) {
            List<Object> rows = new ArrayList<>();
            for (Field declaredField : declaredFields) {
                try {
                    rows.add(declaredField.get(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            simpleExcelWriter.writeRow(rows);
        }
        simpleExcelWriter.save();
    }


    public static <R, C, V> void writeToExcel(Map<R, Map<C, V>> map, String rName, String output) throws IOException {
        List<C> temp = new ArrayList<>();
        for (Map<C, V> cvMap : map.values()) {
            temp.addAll(cvMap.keySet());
        }
        List<C> cols = new ArrayList<>(new HashSet(temp));
        String[] headers = new String[cols.size() + 1];
        headers[0] = rName;
        for (int i = 1; i < headers.length; i++) {
            headers[i] = cols.get(i - 1).toString();
        }
        SimpleExcelWriter simpleExcelWriter = new SimpleExcelWriter(output, headers);
        for (Map.Entry<R, Map<C, V>> rMapEntry : map.entrySet()) {
            R key = rMapEntry.getKey();
            List<String> rows = new ArrayList<>();
            rows.add(key.toString());
            Map<C, V> value = rMapEntry.getValue();
            for (C c : cols) {
                V v = value.get(c);
                rows.add(v == null ? "" : v.toString());
            }
            simpleExcelWriter.writeRow(rows);
        }
        simpleExcelWriter.save();
    }

    /**
     * 处理
     */
    public static <E> void doHandler(String input, String output, Class<E> clazz, VoidHandler<E> handler) throws IOException {
        List<E> dataFromJsonFile = getDataFromJsonFile(input, clazz);
        int ln = 0;
        for (E e : dataFromJsonFile) {
            ln++;
            if (ln % 100 == 0) {
                System.out.println(ln);
            }
            try {
                handler.doHandler(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        writeToDiskByGsonFile(output, dataFromJsonFile);
    }


    /**
     * 多数据处理
     */
    public static <E> void doHandlerByBigData(String input, String output, final Class<E> clazz, final VoidHandler<E> handler) throws IOException {
        final Gson gson = new Gson();
        final PrintWriter printWriter = new PrintWriter(new FileWriter(output));
        YangInputStringUtil.readFile(input, new BooleanHandler<String>() {
            @Override
            public boolean doHandler(String s) {
                E e = gson.fromJson(s, clazz);
                try {
                    handler.doHandler(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return true;
                }
                printWriter.println(gson.toJson(e));
                return true;
            }
        });
        printWriter.close();
    }

    /**
     * 过滤
     */
    public static <E> void doFilter(String input, String output, Class<E> clazz, BooleanHandler<E> handler) throws IOException {
        List<E> dataFromJsonFile = getDataFromJsonFile(input, clazz);
        Iterator<E> iterator = dataFromJsonFile.iterator();
        while (iterator.hasNext()) {
            boolean b = handler.doHandler(iterator.next());
            if (b) {
                iterator.remove();
            }
        }
        writeToDiskByGsonFile(output, dataFromJsonFile);
    }

    /**
     * 友好的可视化输出
     */
    public static <E> void writerToView(String input, String output, Class<E> clazz) throws IOException, IllegalAccessException {
        List<E> dataFromJsonFile = getDataFromJsonFile(input, clazz);
        PrintWriter writer = new PrintWriter(new FileWriter(output));
        for (E e : dataFromJsonFile) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Object o = declaredField.get(e);
                String str;
                if (o instanceof Collection) {
                    Collection collection = Collection.class.cast(o);
                    str = YangStringUtil.getCollectionString(collection);
                } else {
                    str = o.toString();
                }
                writer.println(str);
            }
            writer.println();
        }
        writer.close();
    }


    public static Map<Object, Integer> getDistribution(List<Object> objects) {
        Map<Object, Integer> map = new TreeMap<>();
        for (Object object : objects) {
            Integer count = map.get(object);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            map.put(object, count);
        }
        return map;
    }


}
