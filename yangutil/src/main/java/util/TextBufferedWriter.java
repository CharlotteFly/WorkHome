package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by hwyang on 2014/11/14.
 */
public class TextBufferedWriter extends BufferedWriter {
    private static final String noteSymbol = "#";

    public TextBufferedWriter(Writer out) {
        super(out);
    }

    public TextBufferedWriter(Writer out, String noteLine) throws IOException {
        super(out);
        write(noteSymbol + noteLine);
        newLine();
    }

    public TextBufferedWriter(Writer out, int sz) {
        super(out, sz);
    }

}
