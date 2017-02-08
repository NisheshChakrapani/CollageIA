package collageia;

/**
 * Created by nishu on 2/4/2017.
 */
public class TreeNode {
    private Picture picture;
    private TreeNode left = null;
    private TreeNode right = null;

    public TreeNode(Picture picture) {
        this.picture = picture;
    }
    public TreeNode(Picture picture, TreeNode left, TreeNode right) {
        this.picture = picture;
        this.left = left;
        this.right = right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public Picture getPicture() {
        return picture;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }
}
