package util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by hwyang on 2014/9/25.
 */
public class FileWriteHelper implements Closeable{
    private BufferedWriter writer;
    private int lineNum;

    public int getLineNum() {
        return this.lineNum;
    }

    public FileWriteHelper(String output,boolean append) throws IOException {
        writer = new BufferedWriter(new FileWriter(output, append));
    }

    public void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        lineNum++;
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
