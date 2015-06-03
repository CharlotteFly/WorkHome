package handler;

/**
 * Created by hwyang on 2015/1/28.
 */
public interface VoidHandler<E> {
    public void doHandler(E e) throws Exception;
}
