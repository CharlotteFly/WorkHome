package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by hwyang on 2014/8/18.
 *
 * @author hwyang
 */
public class MyLogger {
    private BufferedWriter writer;

    public MyLogger(String writeFilePath) {
        try {
            writer = new BufferedWriter(new FileWriter(writeFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void writeLog(String log) {
        try {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
