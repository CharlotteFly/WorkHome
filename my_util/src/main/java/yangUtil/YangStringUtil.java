package yangUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    static final String symbolDic = ",.!?，。？！；;．。， 、！？；";

    /**
     * 分句
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
            if (symbolDic.contains(String.valueOf(aChar))) {
                if (offset > 0) {
                    int start = i - offset;
                    result.add(sentence.substring(start, i));
                }
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

    public static List<Map.Entry<Integer, Integer>> splitSentenceByEntry(String sentence) {
        if (sentence == null || sentence.equals("")) {
            return Collections.emptyList();
        }
        List<Map.Entry<Integer, Integer>> result = new ArrayList<>();
        char[] chars = sentence.toCharArray();
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (symbolDic.contains(String.valueOf(aChar))) {
                if (offset > 0) {
                    int start = i - offset;
                    AbstractMap.SimpleEntry<Integer, Integer> entry = new AbstractMap.SimpleEntry<Integer, Integer>(start, i);
                    result.add(entry);
                }
                offset = 0;
            } else {
                offset++;
            }
        }
        if (offset > 0) {
            AbstractMap.SimpleEntry<Integer, Integer> entry = new AbstractMap.SimpleEntry<Integer, Integer>(sentence.length() - offset, sentence.length());
            result.add(entry);
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

    public static <E> List<List<E>> sentenceCombination(List<E> sentence, int max) {
        if (sentence == null || sentence.isEmpty() || max <= 0) {
            return Collections.EMPTY_LIST;
        }
        List<List<E>> result = new ArrayList<>();
        for (int i = 0, length = sentence.size(); i < length; i++) {
            int end = i + max > length ? length : i + max;
            for (int j = i + 1; j <= end; j++) {
                result.add(sentence.subList(i, j));
            }
        }
        return result;
    }

    public static boolean containsWord(String content, String word) {
        return content.contains(" " + word + " ") || content.startsWith(word + " ") || content.endsWith(" " + word) || content.equals(word);
    }

    public static boolean equalse(String str1, String str2) {
        return str1 == null ? (str2 == null) : str1.equals(str2);
    }

    public static boolean isContains(Integer start, Integer end, Integer s, Integer e) {
        return !(start >= e || end <= s);
    }

    /**
     * 根据词典从字串中找到词，以词长排除有交差的词
     *
     * @return
     */
    public static List<String> findWordByPosition(String content, List<String> matchWords) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>();
        for (String matchWord : matchWords) {
            int index = Integer.MIN_VALUE;
            while ((index = content.indexOf(matchWord, index + 1)) >= 0) {
                if (index != 0 && content.charAt(index - 1) != ' ') {
                    continue;
                }
                if (index != content.length() - 1 && content.charAt(index + matchWord.length() + 1) != ' ') {
                    continue;
                }
                list.add(new AbstractMap.SimpleEntry<>(index, index + matchWord.length()));
            }
        }
        List<Map.Entry<Integer, Integer>> filter = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry1 : list) {
            for (Map.Entry<Integer, Integer> entry2 : list) {
                int start1 = entry1.getKey();
                int start2 = entry2.getKey();
                int end1 = entry1.getValue();
                int end2 = entry2.getValue();
                if (start1 == start2 && end1 == end2) {
                    continue;
                }
                if (isContains(start1, end1, start2, end2)) {
                    if ((end2 - start2) > (end1 - start1)) {
                        filter.add(entry1);
                    }
                }
            }
        }
        list.removeAll(filter);
        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            result.add(content.substring(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public static <E> List<Map.Entry<Integer, Integer>> sentenceCombinationByEntry(List<E> eList, int max) {
        if (eList == null || eList.isEmpty() || max <= 0) {
            return Collections.EMPTY_LIST;
        }
        List<Map.Entry<Integer, Integer>> result = new ArrayList<>();
        for (int i = 0, length = eList.size(); i < length; i++) {
            int end = i + max > length ? length : i + max;
            for (int j = i + 1; j <= end; j++) {
                result.add(new AbstractMap.SimpleEntry<>(i, j));
            }
        }
        return result;
    }

    public static List<Map.Entry<Integer, Integer>> sentenceCombinationByEntry(String str, int max) {
        char[] chars = str.toCharArray();
        List<Character> characters = new ArrayList<>();
        for (char aChar : chars) {
            characters.add(aChar);
        }
        return sentenceCombinationByEntry(characters, max);
    }

    public static String retainLetterNoEn(String str) {
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char aChar : chars) {
            if (Character.isLetter(aChar) && !Character.isUpperCase(aChar) && !Character.isLowerCase(aChar)) {
                builder.append(aChar);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        List<String> strings = sentenceCombination("这是一个测试，请", 5);
        System.out.println(strings);
        System.out.println(equalse("a", "a"));
        System.out.println(equalse(null, null));
        System.out.println(equalse(null, "a"));
        System.out.println(equalse("a", null));
        System.out.println(equalse("a", "b"));
    }


}
