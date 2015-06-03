package util;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwyang on 2015/4/10.
 */
public class BufferedPrintWriter {
    private Integer limit = 500;
    private List<String> buffer = new ArrayList<>();
    private PrintWriter writer;

    public BufferedPrintWriter(Writer out) {
        writer = new PrintWriter(out);
    }

    public BufferedPrintWriter(Writer out, int limit) {
        this(out);
        this.limit = limit;
    }

    public void println(String x) {
        buffer.add(x);
        if (buffer.size() > limit) {
            for (String line : buffer) {
                writer.println(line);
            }
            buffer.clear();
        }
    }

    public void println() {
        this.println("");
    }

    public void close() {
        if (!buffer.isEmpty()) {
            for (String line : buffer) {
                writer.println(line);
            }
        }
        buffer = null;
        writer.close();
    }
}
