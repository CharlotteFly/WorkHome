package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

/**
 * Created by hwyang on 2014/12/1.
 */
public class WordDic extends HashSet<String> {
    public WordDic(Reader in) {
        super();
        try {
            init(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LineHandler handler;

    public WordDic(Reader in, LineHandler handler) {
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
            add(line);
        }
    }

}
