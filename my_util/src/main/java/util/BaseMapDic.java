package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * Created by hwyang on 2015/1/23.
 */
public abstract class BaseMapDic<K, V> extends HashMap<K, V> {

    private String split = "\\t";

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public BaseMapDic(Reader in) {
        super();
        try {
            init(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LineHandler handler;

    public BaseMapDic(Reader in, LineHandler handler) {
        super();
        this.handler = handler;
        try {
            init(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init(Reader in) throws IOException {
        BufferedReader reader = new BufferedReader(in);
        String line;
        while ((line = reader.readLine()) != null) {
            if (this.handler != null) {
                line = handler.doHandler(line);
            }
            String[] mavValue = line.split(split);
            put(setKey(mavValue[0]), setValue(mavValue[1]));
        }
    }

    public abstract K setKey(String key);

    public abstract V setValue(String value);
}