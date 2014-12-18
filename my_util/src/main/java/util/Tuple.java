package util;

/**
 * Created by hwyang on 2014/12/4.
 */
public class Tuple {
    public static <F, S> TwoTuple<F, S> getTwoTuple(F first, S second) {
        return new TwoTuple<F, S>(first, second);
    }

    public static <F, S, T> ThreeTuple<F, S, T> getThreeTuple(F first, S second, T third) {
        return new ThreeTuple<F, S, T>(first, second, third);
    }

}
