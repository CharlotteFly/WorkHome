package yangUtil;

import util.YangUtil;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 文件处理工具类
 * Created by hwyang on 2014/12/17.
 */
public class YangFileUtil {
    /**
     * 根据时间戳找到最新文件
     * @param filePath 文件名，可以路径
     * @return 最新的文件
     */
    public static String getLastFile(String filePath) {
        File file = new File(filePath);
        final String fileName = file.getName();
        File baseFile = file.getParentFile();
        assert baseFile.isDirectory();
        String[] list = baseFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return fileName.equals(removeTimestamp(name));
            }
        });
        assert list.length > 0;
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String fileNameStr : list) {
            map.put(fileNameStr, Integer.parseInt(getTimestamp(fileNameStr)));
        }
        YangUtil.sortMapByValue(map, true);
        return file.getParentFile().getPath() + "\\" + new ArrayList<>(map.entrySet()).get(0).getKey();
    }

    /**
     * 得到这个文件的时间戳
     * @param fileName 文件名
     * @return 文件名中的时间戳
     */
    public static String getTimestamp(String fileName) {
        fileName = removeSuffix(fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 去掉时间戳
     * @param fileName 文件名
     * @return 去掉时间戳的文件名
     */
    public static String removeTimestamp(String fileName) {
        if (fileName.contains("." + getTimestamp(fileName))) {
            return fileName.replace("." + getTimestamp(fileName), "");
        }
        return fileName;
    }

    /**
     * 得到文件的title
     * @param fileName 文件名
     * @return 文件的title
     */
    public static String getFileTitle(String fileName) {
        return fileName.substring(0, fileName.indexOf("."));
    }

    /**
     * 去除文件的后缀
     * @param fileName 文件名
     * @return 去后缀的文件名
     */
    public static String removeSuffix(String fileName) {
        int lastDian = fileName.lastIndexOf(".");
        if (lastDian > 0) {
            return fileName.substring(0, lastDian);
        }
        return fileName;
    }

    /**
     * 得到文件的后缀
     * @param fileName 文件名
     * @return 后缀
     */
    public static String getSuffix(String fileName) {
        int lastDian = fileName.lastIndexOf(".");
        return fileName.substring(lastDian + 1);
    }

    /**
     * 给文件一个时间戳
     * @param filePath 文件名，可以路径
     * @return 加入时间戳的文件名
     */
    public static String setTimeToFilePath(String filePath) {
        return String.format("%s.%s.%s", removeSuffix(filePath), getCurrentTimestamp(), getSuffix(filePath));
    }

    //时间缀格式
    static DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");

    /**
     * 当前时间的时间缀
     * @return 时间缀
     */
    public static String getCurrentTimestamp() {
        return dateFormat.format(new Date());
    }

    /**
     * 得到这个文件的log文件名
     */
    public static String getFileLogFileName(String filePath) {
        filePath = removeSuffix(filePath);
        return filePath + ".log";
    }

    /**
     * 给文件在后缀前添加名
     */
    public static String addFileNameAtLast(String filePath, String name) {
        String s = removeSuffix(filePath);
        String suffix = getSuffix(filePath);
        return s + "." + name + "." + suffix;
    }

    public static String getFileName(String baseDir, String fileName) {
        File file = new File(baseDir);
        return file.getAbsolutePath() + "\\" + fileName;
    }

    /**
     * 根据classpath得到Reader默认编码为utf-8
     *
     * @param path classpath相对路径
     * @return Reader
     */
    public static Reader getReaderByClassPath(String path) {
        try {
            return new InputStreamReader(YangFileUtil.class.getClassLoader().getResourceAsStream(path), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String lastFile = getFileName("F:\\test\\", "test.txt");
        System.out.println(lastFile);
    }

}
