package util;

import com.google.gson.Gson;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;
import java.util.regex.Pattern;

/**
 * Created by hwyang on 2014/8/6.
 * @author hwyang
 */
public class YangUtil {

    public static final String SAVE_OBJECT_BASE_PATH = "D:\\hwyang\\object\\";
    public static DocumentBuilder builder;
    public static Transformer transformer;
    public static MessageDigest md;

    static {
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            md = MessageDigest.getInstance("MD5");
        } catch (ParserConfigurationException | TransformerConfigurationException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void writeClass2File(String name,Object o) throws IOException {
        File file = new File(SAVE_OBJECT_BASE_PATH + name);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(o);
        oos.close();
    }

    public static Object readClassFromFile(String name) throws IOException, ClassNotFoundException {
        File file = new File(SAVE_OBJECT_BASE_PATH + name);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static <K,V extends Number> void sortMapByValue(Map<K,V> map,final boolean order) {
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
    public static void readFile(InputStream inputStream,LineProcessor lp,int ln) throws IOException{
        //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        readFile(new InputStreamReader(inputStream, "utf-8"),lp,ln);
    }
    public static void readFile(InputStream inputStream,LineProcessor lp) throws IOException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        readFile(new InputStreamReader(inputStream, "utf-8"), lp, -1);
    }

    public static void readFile(File file,LineProcessor lp,int ln) throws IOException {
        readFile(new InputStreamReader(new FileInputStream(file),"utf-8"),lp,ln);
    }
    public static void readFile(File file,LineProcessor lp) throws IOException {
        readFile(new InputStreamReader(new FileInputStream(file),"utf-8"),lp,-1);
    }

    public static void readFile(InputStreamReader inputStream, LineProcessor lp) throws IOException {
        readFile(inputStream, lp, -1);
    }
    public static void readFile(InputStreamReader inputStream,LineProcessor lp,int ln) throws IOException{
        BufferedReader reader = new BufferedReader(inputStream);
        String line;
        int lineNum = 0;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            if(ln > 0 && ln<lineNum) {
                break;
            }
            if (lineNum % 10000 == 0) {
                System.out.println("read file on line :" + lineNum);
            }
            boolean b = lp.processLine(line);
            if (!b) {
                break;
            }
        }
        reader.close();
    }

    public static void readFile(String path, LineProcessor lp, int ln) throws IOException {
        File file = new File(path);
        readFile(file, lp, ln);
    }
    public static void readFile(String path, LineProcessor lp) throws IOException {
        File file = new File(path);
        readFile(file, lp, -1);
    }

    public static <V> void writeToFile(File file, Iterable<V> iterable ,LineGenerator generator) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
        for (V next : iterable) {
            String line = generator.generateWritLine(next);
            if (line.equals("")) {
                continue;
            }
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static <V> void writeToFile(String path, Iterable<V> iterable ,LineGenerator generator) throws IOException {
        writeToFile(new File(path), iterable, generator);
    }

    public static <R> R getPrivateVariable(Object instance, String fieldName ,Class<R> clzz)  {
        Field field = null;
        R r;
        try {
            field = instance.getClass().getField(fieldName);
            field.setAccessible(true);
            r = (R) field.get(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(field!=null){
                field.setAccessible(false);
            }
        }
        return r;
    }

    public static <E,R> Map<R, E> listToMap(List<E> list,String filedName,Class<R> clazz) {
        Map<R, E> map = new HashMap<>();
        for (E e : list) {
            R o = getPrivateVariable(e, filedName,clazz);
            map.put(o, e);
        }
        return map;
    }

    public static String asXML(Node node) throws TransformerException {
        //transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text");
        DOMSource domSource = new DOMSource();
        domSource.setNode(node);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(domSource, result);
        return sw.toString();
    }

    /**
     * MD5加密类
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    public static String toMD5(String str){
        md.update(str.getBytes());
        byte[]byteDigest = md.digest();
        int i;
        StringBuilder buf = new StringBuilder("");
        for (byte aByteDigest : byteDigest) {
            i = aByteDigest;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        //32位加密
        return buf.toString();
        // 16位的加密
        //return buf.toString().substring(8, 24);
    }

    /**
     * 集合的差集
     */
    public static <V> Collection<V> collectionRemoveAll(Collection<V> c1,Collection<V> c2){
        Collection<V> temp = new ArrayList<>();
        temp.addAll(c1);
        temp.removeAll(c2);
        return temp;
    }

    /**
     * 集合的交集
     */
    public static <V> Collection<V> collectionRetainAll(Collection<V> c1,Collection<V> c2){
        Collection<V> temp = new ArrayList<>();
        temp.addAll(c1);
        temp.retainAll(c2);
        return temp;
    }

    /**
     * 集合的和集
     */
    public static <V> Collection<V> collectionAddAll(Collection<V> c1,Collection<V> c2){
        Collection<V> temp = new HashSet<>();
        temp.addAll(c1);
        temp.addAll(c2);
        return temp;
    }

    public static <C> String generateString(Collection<C> coll,LineGenerator lineGenerator) {
        StringBuilder builder = null;
        for (C c : coll) {
            String content = lineGenerator.generateWritLine(c);
            if (builder == null) {
                builder = new StringBuilder();
                builder.append(content);
            } else {
                builder.append(" ").append(content);
            }
        }
        return builder == null ? "" : builder.toString();
    }

    public static void testEveryLine(LineProcessor processor) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            boolean b;
            try{
                b = processor.processLine(line);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            if(!b){
                break;
            }
        }
    }

    public static Pattern chinesePattern = Pattern.compile("[\\u4E00-\\u9FA5]+");

    public static boolean isChinese(String line){
        return chinesePattern.matcher(line).find();
    }

    public static boolean isChineseByCharacter(String line) {
        for (char c : line.toCharArray()) {
            if (Character.isLetter(c) && !Character.isLowerCase(c) && !Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static Collection<String> preparationFile2Lib(InputStream in) throws IOException {
        final Collection<String> lib = new HashSet<>();
        readFile(in, new LineProcessor() {
            @Override
            public boolean processLine(String line) throws IOException {
                lib.add(line);
                return true;
            }
        });
        return lib;
    }

    public static <R,V> Map<R, Collection<V>> groupByParameter(Collection<V> coll, String paramter, Class<R> rClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<R, Collection<V>> map = new HashMap<>();
        for (V o : coll) {
            R group = getFiledByGetMethod(o, paramter, rClass);
            Collection<V> objects = map.get(group);
            if(objects == null) {
                objects = new HashSet<>();
                map.put(group, objects);
            }
            objects.add(o);
        }
        return map;
    }
    
    public static <R> R getFiledByGetMethod(Object instance, String fileName,Class<R> r) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (R)instance.getClass().getDeclaredMethod("get"+captureName(fileName), null).invoke(instance, null);
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static <K,G> Map<G, Collection<K>> groupBySelf(Collection<K> ks, GroupInterface<G,K> kf) {
        Map<G, Collection<K>> map = new HashMap<>();
        for (K k : ks) {
            G g = kf.generateGroup(k);
            Collection<K> list = map.get(g);
            if(list == null) {
                list = new ArrayList<>();
                map.put(g, list);
            }
            list.add(k);
        }
        return map;
    }

    public static <K,V> Map<K, Collection<V>> sortByValueSize(Map<K, Collection<V>> map, final boolean asc) {
        if (!(map instanceof LinkedHashMap)) {
            map = new LinkedHashMap<>(map);
        }
        List<Map.Entry<K, Collection<V>>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<K, Collection<V>>>() {
            @Override
            public int compare(Map.Entry<K, Collection<V>> o1, Map.Entry<K, Collection<V>> o2) {
                if(asc){
                    return o2.getValue().size() - o1.getValue().size();
                }else {
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

    static Gson gson = new Gson();

    public static <K> List<K> readFileLineAsGson(File file, final Class<K> clazz) throws IOException {
        final List<K> list = new ArrayList<>();
        readFile(file, new LineProcessor() {
            @Override
            public boolean processLine(String line) throws IOException {
                K k = gson.fromJson(line, clazz);
                list.add(k);
                return true;
            }
        });
        return list;
    }

    public static Logger getSimpleFileLogger(String clazz, String file) {
        Logger logger = Logger.getLogger(clazz);
        try {
            Handler handler = new FileHandler(file);
            Formatter formatter = new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\r\n";
                }
            };
            handler.setLevel(Level.ALL);
            handler.setFormatter(formatter);
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return logger;
    }

}
