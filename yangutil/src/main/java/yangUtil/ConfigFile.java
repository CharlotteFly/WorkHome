//package yangUtil;
//
//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// * Created by hwyang on 2015/8/21.
// */
//public class ConfigFile {
//    private boolean init = false;
//    private String decollator = "\\t";
//    private List<String> lines;
//    private String path;
//    private Gson gson = new Gson();
//    public static final StringConverter STRING_CONVERTER = new StringConverter();
//    public static final IntegerConverter INTEGER_CONVERTER = new IntegerConverter();
//
//    public String getDecollator() {
//        return decollator;
//    }
//
//    public void setDecollator(String decollator) {
//        this.decollator = decollator;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    private synchronized void init() throws IOException {
//        if (init) {
//            return;
//        }
//        lines = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(new FileReader(getPath()));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            lines.add(line);
//        }
//        init = true;
//    }
//
//    public <K, V> Map<K, V> getAsMap(Converter<K> keyConverter, Converter<V> valueConverter) throws IOException {
//        init();
//        Map<K, V> map = new HashMap<>();
//        for (String line : lines) {
//            String[] split = line.split(decollator);
//            map.put(keyConverter.convert(split[0]), valueConverter.convert(split[1]));
//        }
//        return map;
//    }
//
//    public <K, V> Map<K, List<V>> getAsMapList(Converter<K> keyConverter, Converter<V> valueConverter) throws IOException {
//        init();
//        Map<K, List<V>> map = new HashMap<>();
//        for (String line : lines) {
//            line = line.trim();
//            String[] split = line.split("\\t");
//            K key = keyConverter.convert(split[0]);
//            List<V> value = new ArrayList<>();
//            for (int index = 1; index < split.length; index++) {
//                value.add(valueConverter.convert(split[index]));
//            }
//            map.put(key, value);
//        }
//        return map;
//    }
//
//    public List<String> getAsList() throws IOException {
//        init();
//        return lines;
//    }
//
//    public <E> List<E> getAsObjeList(Class<E> type) throws IOException {
//        init();
//        List<E> result = new ArrayList<>();
//        for (String line : lines) {
//            E e = gson.fromJson(line, type);
//            result.add(e);
//        }
//        return result;
//    }
//
//    public interface Converter<E>{
//        E convert(String str);
//    }
//
//    public static class StringConverter implements Converter<String> {
//        @Override
//        public String convert(String str) {
//            return str;
//        }
//    }
//
//    public static class IntegerConverter implements Converter<Integer> {
//        @Override
//        public Integer convert(String str) {
//            return Integer.parseInt(str);
//        }
//    }
//
//}
