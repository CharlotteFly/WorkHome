package util;

import java.io.*;
import java.util.Iterator;

/**
 * 一个文件读取的工具类
 * Created by hwyang on 2014/11/11.
 */
public class TextBufferReader extends BufferedReader implements Iterator<String>,Iterable<String>{
    private String line;

    public TextBufferReader(Reader in, int sz) {
        super(in, sz);
    }

    public TextBufferReader(Reader in) {
        super(in);
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        try {
            line = super.readLine();
            if (line == null) {
                this.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line != null;
    }

    @Override
    public String next() {
        return line;
    }

    @Override
    public void remove() {
        System.out.println("此方法不可用......");
    }
}
