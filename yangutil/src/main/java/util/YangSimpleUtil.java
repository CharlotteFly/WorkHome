package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hwyang on 2015/2/25.
 */
public class YangSimpleUtil {
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
     * List转数组
     */
    public static <K> K[] listToArray(Collection<K> kList) {
        if (kList.isEmpty()) {
            return null;
        }
        ArrayList<K> ks = new ArrayList<K>(kList);
        K k = ks.get(0);
        Class<?> aClass = k.getClass();
        Object o = Array.newInstance(aClass, ks.size());
        K[] temp = (K[]) o;
        return ks.toArray(temp);
    }

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("B");
//        String[] strings = listToArray(list);
//        System.out.println(Arrays.toString(strings));

        double round = round(0.234234, 3);
        System.out.println(round);
    }
}
