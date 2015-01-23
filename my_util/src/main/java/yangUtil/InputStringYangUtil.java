package yangUtil;

import util.LineProcessor;

import java.io.*;

/**
 * Created by hwyang on 2015/1/6.
 */
public class InputStringYangUtil {
    public static void readFile(InputStream inputStream, LineProcessor lp, int ln) throws IOException {
        readFile(new InputStreamReader(inputStream, "utf-8"), lp, ln);
    }

    public static void readFile(InputStream inputStream, LineProcessor lp) throws IOException {
        readFile(inputStream, lp, -1);
    }

    public static void readFile(File file, LineProcessor lp, int ln) throws IOException {
        readFile(new InputStreamReader(new FileInputStream(file), "utf-8"), lp, ln);
    }

    public static void readFile(File file, LineProcessor lp) throws IOException {
        readFile(file, lp, -1);
    }

    public static void readFile(InputStreamReader inputStream, LineProcessor lp) throws IOException {
        readFile(inputStream, lp, -1);
    }

    public static void readFile(InputStreamReader inputStream, LineProcessor lp, int ln) throws IOException {
        BufferedReader reader = new BufferedReader(inputStream);
        String line;
        int lineNum = 0;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            if (ln > 0 && ln < lineNum) {
                break;
            }
            if (lineNum % 10000 == 0) {
                System.out.println("read file on line :" + lineNum);
            }
            boolean b = lp.processLine(line);
            if (!b) {
                break;
            }
        }
        reader.close();
    }

    public static void readFile(String path, LineProcessor lp, int ln) throws IOException {
        File file = new File(path);
        readFile(file, lp, ln);
    }

    public static void readFile(String path, LineProcessor lp) throws IOException {
        File file = new File(path);
        readFile(file, lp, -1);
    }
}
