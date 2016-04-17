package tree;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by hwyang on 2016/1/7.
 */
public class Tree<T>{
    /**
     * Initiate（t）初始化一棵空树t。
     * （2）Root（x）求结点x 所在树的根结点。
     * （3）Parent（t，x）求树t 中结点x 的双亲结点。
     * （4）Child（t，x，i）求树t 中结点x 的第i 个孩子结点。
     * （5）RightSibling（t，x）求树t 中结点x 的第一个右边兄弟结点。
     * （6）Insert（t，x，i，s）把以s 为根结点的树插入到树t 中作为结点x 的第i 棵子树。
     * （7）Delete（t，x，i）在树t 中删除结点x 的第i 棵子树。
     * （8）Tranverse（t）是树的遍历操作，即按某种方式访问树t 中的每个结点，且使每个结点只被访问一次。
     */
    private Node<T> root;

    public Tree(T root){
        this.root = new Node<>(root, null, null, null);
    }

    public Node<T> getRoot(Node<T> node){
        return root;
    }

    public Node<T> getParent(Node<T> node) {
        return node.getParent();
    }

    public List<Node<T>> getChilds(Node<T> node) {
        List<Node<T>> result = new ArrayList<>();
        Node<T> leftMostChild = node.getLeftMostChild();
        if (leftMostChild == null) {
            return Collections.emptyList();
        }
        do {
            result.add(leftMostChild);
        } while ((leftMostChild = leftMostChild.getRightSibling()) != null);
        return result;
    }

    public Node<T> getBrother(Node<T> node, int offset) {
        Node<T> parent = getParent(node);
        if (parent == null) {
            return null;
        }
        List<Node<T>> childs = getChilds(parent);
        return childs.get(offset);
    }

    public boolean insert(Node<T> node, Node<T> newNode) {
        if (node == null || newNode == null) {
            return false;
        }
        List<Node<T>> childs = getChilds(node);
        if (childs.isEmpty()) {
            node.setLeftMostChild(newNode);
        } else {
            childs.get(childs.size() - 1).setRightSibling(newNode);
        }
        return true;
    }

    public Node<T> insert(Node<T> node, T instance) {
        Node<T> leftMostChild = node.getLeftMostChild();
        Node<T> insertNode = new Node<>(instance, node, null, leftMostChild);
        node.setLeftMostChild(insertNode);
        return insertNode;
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public Collection<Node<T>> getDescendant(Node<T> node) {
        List<Node<T>> childs = getChilds(node);
        if (childs == null || childs.isEmpty()) {
            return Collections.emptyList();
        }
        List<Node<T>> nodes = new ArrayList<>();
        for (Node<T> child : childs) {
            nodes.add(child);
            nodes.addAll(getDescendant(child));
        }
        return nodes;
    }

    public Collection<Node<T>> getAncestor(Node<T> node) {
        List<Node<T>> nodes = new ArrayList<>();
        while ((node = node.getParent()) != null) {
            nodes.add(node);
        }
        return nodes;
    }

    public Collection<Node<T>> getRelateNodes(Node<T> node) {
        ArrayList<Node<T>> arrayList = new ArrayList<>();
        arrayList.add(node);
        arrayList.addAll(getAncestor(node));
        arrayList.addAll(getDescendant(node));
        return arrayList;
    }


}
