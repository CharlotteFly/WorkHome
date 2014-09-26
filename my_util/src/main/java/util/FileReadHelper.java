package util;

import java.io.*;
import java.util.Iterator;

/**
 * Created by hwyang on 2014/9/25.
 */
public class FileReadHelper implements Iterator<String>,Iterable<String>,Closeable{
    private BufferedReader reader;
    private int lineNum;
    private String line;

    public int getLineNum() {
        return this.lineNum;
    }

    public FileReadHelper(String filePath) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filePath));
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        try {
            this.line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            this.lineNum++;
        }
        return this.line != null;
    }

    @Override
    public String next() {
        return this.line;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
