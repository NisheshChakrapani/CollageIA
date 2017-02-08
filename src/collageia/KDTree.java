package collageia;
import sun.reflect.generics.tree.Tree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nishu on 2/4/2017.
 */
public class KDTree {
    private ArrayList<Picture> pictures = new ArrayList();
    private TreeNode root;
    private ArrayList<Comparator<Picture>> comparators = new ArrayList<>(3);

    public KDTree(ArrayList<Picture> pictures) {
        this.pictures = pictures;

        comparators.add(new RedComparator());
        comparators.add(new GreenComparator());
        comparators.add(new BlueComparator());
    }

    public void createTree() {
        int sortIndex = 0;
        Collections.sort(pictures, comparators.get(sortIndex));
        sortIndex++;
        PartialKDTree first = buildPartialKDTree(pictures);
        root = first.getParent();

        ArrayList<PartialKDTree> currentList = new ArrayList<>();
        ArrayList<PartialKDTree> nextList = new ArrayList<>();

        currentList.add(first);

        while(currentList.size() > 0) {
            for (PartialKDTree pkdt : currentList) {
                Collections.sort(pkdt.getLeft(), comparators.get(sortIndex));
                Collections.sort(pkdt.getRight(), comparators.get(sortIndex));

                PartialKDTree leftTree = buildPartialKDTree(pkdt.getLeft());
                PartialKDTree rightTree = buildPartialKDTree(pkdt.getRight());

                if (leftTree != null) {
                    pkdt.getParent().setLeft(leftTree.getParent());
                    nextList.add(leftTree);
                }
                if (rightTree != null) {
                    pkdt.getParent().setRight(rightTree.getParent());
                    nextList.add(rightTree);
                }
            }

            currentList = nextList;
            nextList = new ArrayList<>();
            sortIndex = (sortIndex+1)%3;
        }
    }

    private PartialKDTree buildPartialKDTree(ArrayList<Picture> pictures) {
        if (pictures.size() == 0) {
            return null;
        }

        int medianIndex = findMedianIndex(pictures);
        Picture medianPicture = pictures.get(medianIndex);
        TreeNode node = new TreeNode(medianPicture);
        ArrayList<Picture> leftList = new ArrayList<>();
        for (int i = 0; i < medianIndex; i++) {
            leftList.add(pictures.get(i));
        }

        ArrayList<Picture> rightList = new ArrayList<>();
        for (int i = medianIndex+1; i < pictures.size(); i++) {
            rightList.add(pictures.get(i));
        }

        PartialKDTree pkdt = new PartialKDTree(node, leftList, rightList);
        return pkdt;
    }

    private int findMedianIndex(ArrayList list) {
        return Math.floorDiv(list.size(), 2);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void printTree() {
        int sortIndex = 0;
        Collections.sort(pictures, comparators.get(sortIndex));
        sortIndex++;
        PartialKDTree first = buildPartialKDTree(pictures);

        ArrayList<PartialKDTree> currentList = new ArrayList<>();
        ArrayList<PartialKDTree> nextList = new ArrayList<>();

        currentList.add(first);

        System.out.println(first.getParent().getPicture());
        while(currentList.size() > 0) {
            System.out.println();
            for (PartialKDTree pkdt : currentList) {
                Collections.sort(pkdt.getLeft(), comparators.get(sortIndex));
                Collections.sort(pkdt.getRight(), comparators.get(sortIndex));

                PartialKDTree leftTree = buildPartialKDTree(pkdt.getLeft());
                PartialKDTree rightTree = buildPartialKDTree(pkdt.getRight());

                if (leftTree != null) {
                    pkdt.getParent().setLeft(leftTree.getParent());
                    nextList.add(leftTree);
                    System.out.println(leftTree.getParent().getPicture());
                }
                if (rightTree != null) {
                    pkdt.getParent().setRight(rightTree.getParent());
                    nextList.add(rightTree);
                    System.out.println(rightTree.getParent().getPicture());
                }
            }

            currentList = nextList;
            nextList = new ArrayList<>();
            sortIndex = (sortIndex+1)%3;
        }
    }

    public TreeNode nearestNeighbor(Color color) {
        RelativeTreeNode nearest = appproximateNearest(color);
        ArrayList<TreeNode> currList = new ArrayList<>();
        ArrayList<TreeNode> nextList = new ArrayList<>();
        currList.add(root);
        int compIndex = 0;
        double bestDist = nearest.getDistance();

        while (currList.size() != 0) {
            for (TreeNode treeNode : currList) {
                Color c = treeNode.getPicture().getAvgColor();
                double distance = distance(c, color);
                if (distance < bestDist) {
                    bestDist = distance(treeNode.getPicture().getAvgColor(), color);
                    nearest = new RelativeTreeNode(treeNode, bestDist);
                }
                updateCandidates(treeNode, nearest, compIndex, nextList);
            }
            currList = nextList;
            nextList = new ArrayList<TreeNode>();
            compIndex++;
        }

        return nearest.getTreeNode();
    }

    private RelativeTreeNode appproximateNearest(Color color) {
        RelativeTreeNode closestRelativeTreeNode = null;
        double bestDist = Double.MAX_VALUE;
        int compareIndex = 0;
        TreeNode currNode = root;
        while (currNode != null) {
            double distance = distance(currNode.getPicture().getAvgColor(), color);
            if (distance < bestDist) {
                bestDist = distance;
                closestRelativeTreeNode = new RelativeTreeNode(currNode, distance);
            }

            if (compare(currNode.getPicture().getAvgColor(), color, compareIndex) == 0) {
                currNode = currNode.getLeft();
            } else {
                currNode = currNode.getRight();
            }

            compareIndex++;
        }

        return closestRelativeTreeNode;
    }

    private int compare(Color compareTo, Color compare, int compareIndex) {
        if (compareIndex%3 == 0) {
            if (compare.getRed() < compareTo.getRed()) {
                return 0;
            } else {
                return 1;
            }
        } else if (compareIndex%3 == 1) {
            if (compare.getGreen() < compareTo.getGreen()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (compare.getBlue() < compareTo.getBlue()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private double distance(Color c1, Color c2) {
        int xDist = c1.getRed()-c2.getRed();
        int yDist = c1.getGreen()-c2.getGreen();
        int zDist = c1.getBlue()-c2.getBlue();

        return Math.sqrt((xDist*xDist) + (yDist*yDist) + (zDist*zDist));
    }

    private void updateCandidates(TreeNode treeNode, RelativeTreeNode nearest, int compareIndex, ArrayList<TreeNode> nextList) {
        int absDiff;
        if (compareIndex%3 == 0) {
            absDiff = Math.abs(treeNode.getPicture().getAvgColor().getRed() - nearest.getTreeNode().getPicture().getAvgColor().getRed());
        } else if (compareIndex%3 == 1) {
            absDiff = Math.abs(treeNode.getPicture().getAvgColor().getGreen() - nearest.getTreeNode().getPicture().getAvgColor().getGreen());
        } else {
            absDiff = Math.abs(treeNode.getPicture().getAvgColor().getBlue() - nearest.getTreeNode().getPicture().getAvgColor().getBlue());
        }

        if (absDiff < nearest.getDistance()) {
            if (treeNode.getLeft() != null) {
                nextList.add(treeNode.getLeft());
            }
            if (treeNode.getRight() != null) {
                nextList.add(treeNode.getRight());
            }
        } else {
            int compare = compare(treeNode.getPicture().getAvgColor(), nearest.getTreeNode().getPicture().getAvgColor(), compareIndex);
            if (compare == 0 && treeNode.getLeft() != null) {
                nextList.add(treeNode.getLeft());
            } else if (compare == 1 && treeNode.getRight() != null) {
                nextList.add(treeNode.getRight());
            }
        }
    }
}
