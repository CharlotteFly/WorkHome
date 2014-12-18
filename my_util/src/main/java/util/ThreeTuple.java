package util;

/**
 * Created by hwyang on 2014/12/4.
 */
public class ThreeTuple<F, S, T> extends TwoTuple<F, S> {
    public final T third;

    public ThreeTuple(F first, S second, T third) {
        super(first, second);
        this.third = third;
    }
}
