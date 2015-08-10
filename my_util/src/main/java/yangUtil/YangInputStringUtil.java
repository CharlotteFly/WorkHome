package yangUtil;

import com.google.gson.Gson;
import handler.BooleanHandler;
import util.MoreLineReader;

import java.io.*;
import java.util.*;

/**
 * Created by hwyang on 2015/1/6.
 */
public class YangInputStringUtil {
    static Gson gson = new Gson();

    public static <E> void readGsonFile(String filePath, final BooleanHandler<E> lp, final Class<E> clazz) throws IOException {
        readFile(filePath, new BooleanHandler<String>() {
            @Override
            public boolean doHandler(String s) {
                E e = gson.fromJson(s, clazz);
                return lp.doHandler(e);
            }
        });
    }

    public static void readFile(InputStream inputStream, BooleanHandler<String> lp, int ln) throws IOException {
        readFile(new InputStreamReader(inputStream, "utf-8"), lp, ln);
    }

    public static void readFile(InputStream inputStream, BooleanHandler<String> lp) throws IOException {
        readFile(inputStream, lp, -1);
    }

    public static void readFile(File file, BooleanHandler<String> lp, int ln) throws IOException {
        readFile(new InputStreamReader(new FileInputStream(file), "utf-8"), lp, ln);
    }

    public static void readFile(File file, BooleanHandler<String> lp) throws IOException {
        readFile(file, lp, -1);
    }

    public static void readFile(Reader inputStream, BooleanHandler<String> lp) throws IOException {
        readFile(inputStream, lp, -1);
    }

    public static void readFile(Reader inputStream, BooleanHandler<String> lp, int ln) throws IOException {
//        BufferedReader reader = new BufferedReader(inputStream);
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
            boolean b = lp.doHandler(line);
            if (!b) {
                break;
            }
        }
        reader.close();
    }

    public static void readFile(String path, BooleanHandler<String> lp, int ln) throws IOException {
        File file = new File(path);
        readFile(file, lp, ln);
    }

    public static void readFile(String path, BooleanHandler<String> lp) throws IOException {
        File file = new File(path);
        readFile(file, lp, -1);
    }

    public static void copyFile(File src, File target) throws IOException {
        final PrintWriter printWriter = new PrintWriter(new FileWriter(target));
        YangInputStringUtil.readFile(src, new BooleanHandler<String>() {
            @Override
            public boolean doHandler(String s) {
                printWriter.println(s);
                return true;
            }
        });
        printWriter.close();
    }

    /**
     * 分隔文件
     *
     * @param inputFile
     * @param outputDirPath
     * @param splitLineNum
     * @throws IOException
     */
    public static void splitFile(String inputFile, final String outputDirPath, final int splitLineNum) throws IOException {
        File outputDir = new File(outputDirPath);
        int index = 0;
        int offset = 0;
        PrintWriter writer = null;
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int num = 0;
        while ((line = reader.readLine()) != null) {
            num++;
            if (num % 10000 == 0) {
                System.out.println(num);
            }
            if (writer == null) {
                try {
                    writer = new PrintWriter(new FileWriter(new File(outputDir, String.valueOf(offset))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            writer.println(line);
            index++;
            if (index % splitLineNum == 0) {
                offset++;
                writer.close();
                writer = null;
            }
        }
        if (writer != null) {
            writer.close();
        }
    }


    /**
     * 分隔文件
     *
     * @param inputFile
     * @param outputDirPath
     * @throws IOException
     */
    public static void splitFileByNum(String inputFile, final String outputDirPath, int num) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        int total = 0;
        while (reader.readLine() != null) {
            total++;
        }
        reader.close();
        splitFile(inputFile, outputDirPath, total / num + 1);
    }

    /**
     * 合并分析结果文件
     *
     * @param inputDir
     * @param outputFilePath
     * @param lines
     * @throws IOException
     */
    public static void meargeFile(String inputDir, final String outputFilePath, String... lines) throws IOException {
        File dir = new File(inputDir);
        File[] files = dir.listFiles();
        assert files != null;
        List<File> filesList = Arrays.asList(files);
        Collections.sort(filesList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int o2Num = Integer.parseInt(YangFileUtil.getSuffix(o2.getName()));
                int o1Num = Integer.parseInt(YangFileUtil.getSuffix(o1.getName()));
                return o1Num - o2Num;
            }
        });
        PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath));
        int i = 0;
        for (File file : filesList) {
            MoreLineReader maps = new MoreLineReader(new FileReader(file), lines);
            for (Map<String, String> map : maps) {
                i++;
                if (i % 1000 == 0) {
                    System.out.println(i);
                }
                for (String line : lines) {
                    writer.println(map.get(line));
                }
            }
        }
        writer.close();
    }

    public static <K> List<K> readFileLineAsGson(File file, final Class<K> clazz) throws IOException {
        final List<K> list = new ArrayList<>();
        readFile(file, new BooleanHandler<String>() {
            @Override
            public boolean doHandler(String s) {
                K k = gson.fromJson(s, clazz);
                list.add(k);
                return true;
            }
        });
        return list;
    }

    public static List<String> readLines(Reader reader) throws IOException {
        final List<String> lines = new ArrayList<>();
        try {
            readFile(reader, new BooleanHandler<String>() {
                @Override
                public boolean doHandler(String s) {
                    lines.add(s);
                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public static List<String> readLines(File file) {
        final List<String> lines = new ArrayList<>();
        try {
            readFile(file, new BooleanHandler<String>() {
                @Override
                public boolean doHandler(String s) {
                    lines.add(s);
                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeLines(Collection<String> lines, PrintWriter printWriter) {
        for (String line : lines) {
            printWriter.println(line);
        }
        printWriter.close();
    }

    public static Iterable<String> readLinesByIterable(Reader reader) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    String line;

                    @Override
                    public boolean hasNext() {
                        try {
                            line = bufferedReader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (line == null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return line != null;
                    }

                    @Override
                    public String next() {
                        return line;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> readLinesByIterable(FileReader reader, final Class<T> clazz) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    T t;

                    @Override
                    public boolean hasNext() {
                        String line = null;
                        try {
                            line = bufferedReader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (line == null) {
                            t = null;
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            t = gson.fromJson(line, clazz);
                        }
                        return line != null;
                    }

                    @Override
                    public T next() {
                        return t;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
        };
    }

}
