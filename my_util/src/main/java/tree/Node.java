package tree;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　 ┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * User: hwyang
 * DateTime: 2016/3/24 15:55
 */
public class Node<T> {
    private T value;
    private Node<T> parent;
    private Node<T> leftMostChild;//最左侧子结点
    private Node<T> rightSibling;//相邻右结点

    public Node(T value, Node<T> parent, Node<T> leftmostChild, Node<T> rightSibling) {
        this.value = value;
        this.parent = parent;
        this.leftMostChild = leftmostChild;
        this.rightSibling = rightSibling;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeftMostChild() {
        return leftMostChild;
    }

    public void setLeftMostChild(Node<T> leftMostChild) {
        this.leftMostChild = leftMostChild;
    }

    public Node<T> getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(Node<T> rightSibling) {
        this.rightSibling = rightSibling;
    }
}
