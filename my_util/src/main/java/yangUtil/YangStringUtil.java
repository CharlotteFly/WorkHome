package yangUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 字符串工具类
 * Created by hwyang on 2015/1/6.
 */
public class YangStringUtil {

    public static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String toMD5(String str) {
        md.update(str.getBytes());
        byte[] byteDigest = md.digest();
        int i;
        StringBuilder buf = new StringBuilder("");
        for (byte aByteDigest : byteDigest) {
            i = aByteDigest;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        //32位加密
        return buf.toString();
        // 16位的加密
        //return buf.toString().substring(8, 24);
    }

    public static <E> String getCollectionString(Collection<E> collection) {
        StringBuilder builder = null;
        for (E e : collection) {
            if (builder == null) {
                builder = new StringBuilder();
            } else {
                builder.append("\r\n");
            }
            builder.append(e.toString());
        }
        return builder == null ? "" : builder.toString();
    }

    static final String symbolDic = ",.!?，。？！";

    /**
     * 分句
     *
     * @param sentence
     * @return
     */
    public static List<String> splitSentence(String sentence) {
        if (sentence == null || sentence.equals("")) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        char[] chars = sentence.toCharArray();
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (symbolDic.contains(String.valueOf(aChar)) && offset > 0) {
                int start = i - offset;
                result.add(sentence.substring(start, i));
                offset = 0;
            } else {
                offset++;
            }
        }
        if (offset > 0) {
            result.add(sentence.substring(sentence.length() - offset, sentence.length()));
        }
        return result;
    }

    public static List<String> sentenceCombination(String sentence, int max) {
        if (sentence == null || sentence.equals("") || max <= 0) {
            return Collections.EMPTY_LIST;
        }
        List<String> result = new ArrayList<>();
        char[] chars = sentence.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int end = i + max > chars.length ? chars.length : i + max;
            for (int j = i + 1; j <= end; j++) {
                result.add(sentence.substring(i, j));
            }
        }
        return result;
    }

    public static boolean containsWord(String content, String word) {
        return content.contains(" " + word + " ") || content.startsWith(word + " ") || content.endsWith(" " + word) || content.equals(word);
    }

}
