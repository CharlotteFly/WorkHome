package simple;

import handler.BooleanHandler;
import handler.VoidHandler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by hwyang on 2015/9/23.
 */
public class YangSimpleTree<V> {
    private YangSimpleTree<V> parent;//父节点
    private List<YangSimpleTree<V>> children;//子节点
    private V value;//内容
    int deep = 0;//深度

    public YangSimpleTree(V v){
        this.value = v;
    }

    public YangSimpleTree<V> addChild(V v) {
        YangSimpleTree<V> e = new YangSimpleTree<>(v);
        e.parent = this;
        e.deep = this.deep + 1;
        if (this.children == null) {
            this.children = new ArrayList<>();
         }
        this.children.add(e);
        return e;
    }

    public List<YangSimpleTree<V>> getChildren() {
        return this.children;
    }

    public V getValue() {
        return this.value;
    }

    public YangSimpleTree<V> getParent() {
        return this.parent;
    }

    public YangSimpleTree<V> getRoot() {
        YangSimpleTree<V> current = this;
        while (true) {
            YangSimpleTree<V> parent = current.getParent();
            if (parent == null) {
                break;
            } else {
                current = parent;
            }
        }
        return current;
    }

    public void doHandler(VoidHandler<YangSimpleTree> handler) throws Exception {
        List<YangSimpleTree<V>> next = this.children;
        if (next == null) {
            return;
        }
        handler.doHandler(this);
        for (YangSimpleTree<V> tree : next) {
            tree.doHandler(handler);
        }
    }

    private void print() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.deep; i++) {
            builder.append("\t");
        }
        builder.append(value.toString());
        System.out.println(builder.toString());
        if (this.children == null) {
            return;
        }
        for (YangSimpleTree<V> tree : children) {
            tree.print();
        }
    }

    public List<YangSimpleTree<V>> children(BooleanHandler<YangSimpleTree<V>> handler) {
        List<YangSimpleTree<V>> result = new ArrayList<>();
        if (children == null) {
            return result;
        }
        for (YangSimpleTree<V> child : children) {
            if (handler.doHandler(child)) {
                result.add(child);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        YangSimpleTree<String> tree = new YangSimpleTree<>("root");
        YangSimpleTree<String> child_1_1 = tree.addChild("child_1_1");
        YangSimpleTree<String> child_1_2 = tree.addChild("child_1_2");

        child_1_1.addChild("child_2_1");
        child_1_1.addChild("child_2_2");

        child_1_2.addChild("child_2_3");
        child_1_2.addChild("child_2_4");

        System.out.println(child_1_1.getChildren());
        tree.print();
        System.out.println();
    }
}
