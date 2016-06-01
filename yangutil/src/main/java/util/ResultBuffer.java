package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwyang on 2015/1/19.
 */
public class ResultBuffer<K, V> {
    private File file;
    private Map<K, V> map;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    public ResultBuffer(File file) throws IOException, ClassNotFoundException {
        this.file = file;
        this.map = new HashMap<>();
        if (file.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            map = (Map<K, V>) ois.readObject();
            ois.close();
        }
    }

    public void put(K k, V v) {
        map.put(k, v);
    }

    public V get(K k) {
        return map.get(k);
    }

    public void flush() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(map);
        oos.close();
    }
}
