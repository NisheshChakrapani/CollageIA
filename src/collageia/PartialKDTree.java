package collageia;

import java.util.ArrayList;

/**
 * Created by nishu on 2/4/2017.
 */
public class PartialKDTree {
    private TreeNode parent;
    private ArrayList<Picture> left;
    private ArrayList<Picture> right;

    public PartialKDTree(TreeNode parent, ArrayList<Picture> left, ArrayList<Picture> right) {
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public TreeNode getParent() {
        return parent;
    }

    public ArrayList<Picture> getLeft() {
        return left;
    }

    public ArrayList<Picture> getRight() {
        return right;
    }
}
