package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hwyang on 2014/12/1.
 */
public class MoreLineReader extends BufferedReader implements Iterator<Map<String, String>>, Iterable<Map<String, String>> {
    private String[] lines;
    private Map<String, String> resultUnit;

    private MoreLineReader(Reader in, int sz) {
        super(in, sz);
    }

    private MoreLineReader(Reader in) {
        super(in);
    }

    public MoreLineReader(Reader in, int sz, String... lines) {
        this(in, sz);
        this.lines = lines;
    }

    public MoreLineReader(Reader in, String... lines) {
        this(in);
        this.lines = lines;
    }


    @Override
    public boolean hasNext() {
        this.resultUnit = new HashMap<>();
        try {
            String content;
            for (String line : lines) {
                content = readLine();
                if (content == null) {
                    this.close();
                    return false;
                }
                resultUnit.put(line, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultUnit != null;
    }

    @Override
    public Map<String, String> next() {
        return this.resultUnit;
    }

    @Override
    public void remove() {
        //null
    }

    @Override
    public Iterator<Map<String, String>> iterator() {
        return this;
    }
}
