package yangUtil;

import com.google.common.base.Splitter;

import java.util.*;

/**
 *
 * Created by hwyang on 2015/4/15.
 */
public class YangSimpleUtil {

    public static boolean isEmptyString(String str) {
        return str == null || str.equals("");
    }

    public static boolean isEmptyCollection(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static List<Integer> range(int r) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<Integer> range(int star, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = star; i < end; i++) {
            list.add(i);
        }
        return list;
    }

    public static boolean isContains(Integer start1, Integer end1, Integer start2, Integer end2) {
        return !(start1 >= end2 || end1 <= start2);
    }

    /**
     * 四舍五入
     */
    public static double round(double score) {
        return ((int) (score * 100 + 0.5)) / 100.0; //四舍五入
    }

    /**
     * 四舍五入到后几位
     */
    public static double round(double score, int n) {
        double t = Math.pow(10, n);
        return ((int) (score * t + 0.5)) / (t * 1.0); //四舍五入
    }

    /**
     * 组合
     * 例 ： 3 -》 1 2; 2 1; 1 1 1;
     * @param num
     * @return
     */
    public static Collection<String> combination(int num) {
        if (num == 2) {
            return Arrays.asList("1 1");
        }
        Set<String> result = new HashSet<>();
        for (int left = 1; left < num; left++) {
            int right = num - left;
            result.add(left + " " + right);
            for (String s : combination(left)) {
                result.add(s + " " + right);
            }
            for (String s : combination(right)) {
                result.add(left + " " + s);
            }
        }
        return result;
    }

    public static Collection<Collection<String>> combination(String content) {
        int length = content.length();
        Collection<String> combination = combination(length);
        Collection<Collection<String>> result = new ArrayList<>();
        for (String unit : combination) {
            List<String> temp = new ArrayList<>();
            List<String> list = Splitter.on(" ").splitToList(unit);
            int index = 0;
            for (String s : list) {
                int len = Integer.parseInt(s);
                temp.add(content.substring(index, index + len));
                index += len;
            }
            result.add(temp);
        }
        return result;
    }

    /**
     * @return
     */
    public static Collection<String> combination(String content,int max) {
        char[] chars = content.toCharArray();
        Collection<String> result = new ArrayList<>();
        for (int start = 0; start < chars.length; start++) {
            for (int end =  start + max > chars.length ? chars.length : start + max; start < end; end--) {
                String temp = content.substring(start, end);
                result.add(temp);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Collection<Collection<String>> s = combination("我杨洪伟");
        System.out.println(s);
    }
}
