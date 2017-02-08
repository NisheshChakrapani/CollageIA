package collageia;

/**
 * Created by nishu on 2/7/2017.
 */
public class RelativeTreeNode {
    private TreeNode treeNode;
    private double distance;

    public RelativeTreeNode(TreeNode treeNode, double distance) {
        this.treeNode = treeNode;
        this.distance = distance;
    }

    public RelativeTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public RelativeTreeNode(double distance) {
        this.distance = distance;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public TreeNode getTreeNode() {
        return this.treeNode;
    }

    public double getDistance() {
        return this.distance;
    }
}
