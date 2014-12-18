package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * 一个文件读取的工具类
 * Created by hwyang on 2014/11/11.
 */
public class TextBufferReader extends BufferedReader implements Iterator<String>,Iterable<String>{
    private String line;
    private static final String noteSymbol = "#";
    private boolean filterNode = false; //过滤以#开关的注释信息

    public boolean isFilterNode() {
        return filterNode;
    }

    public void setFilterNode(boolean filterNode) {
        this.filterNode = filterNode;
    }

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
    public String readLine() throws IOException {
        String line = super.readLine();
        if (filterNode && line.startsWith(noteSymbol)) {
            return readLine();
        }
        return line;
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
