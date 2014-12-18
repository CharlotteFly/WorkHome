package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hwyang on 2014/12/10.
 */
public class LineTextFile extends ArrayList<String> {
    public LineTextFile(String filePath) {
        super();
        try {
            init(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            add(line);
        }
        reader.close();
    }
}
