package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

/**
 * Created by hwyang on 2015/4/10.
 */
public class BufferedBufferedReader extends BufferedReader {
    private LinkedList<String> buffer = new LinkedList<>();
    private Integer limit = 500;

    public BufferedBufferedReader(Reader in) {
        super(in);
    }

    public BufferedBufferedReader(Reader in, int limit) {
        super(in);
        this.limit = limit;
    }

    @Override
    public String readLine() throws IOException {
        if (buffer.isEmpty()) {
            for (int i = 0; i < limit; i++) {
                String line = super.readLine();
                if (line == null) {
                    break;
                }
                buffer.add(line);
            }
        }
        return buffer.pollFirst();
    }


}
