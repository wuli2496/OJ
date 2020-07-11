class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;


    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}

class Solution {
    public Node construct(int[][] grid) {
        return backtrack(grid, 0, 0, grid.length);
    }

    private Node backtrack(int[][] grid, int left, int top, int length) {
        boolean isLeaf = checkLeaf(grid, left, top, length);

       if (isLeaf) {
           return new Node(grid[left][top] == 1, true, null, null, null, null);
       }
       int halfLen = length / 2;
        return helper(backtrack(grid, left, top, halfLen), backtrack(grid, left, top + halfLen, halfLen)
        , backtrack(grid, left + halfLen, top , halfLen), backtrack(grid, left + halfLen, top + halfLen, halfLen));
    }

    private Node helper(Node... nodes) {
        boolean val = nodes[0].val;
        boolean isLeaf = true;

        for (Node node : nodes) {
            if (val != node.val || node.isLeaf == false) {
                isLeaf = false;
                break;
            }
        }

        if (isLeaf) {
            return new Node(val, true, null, null, null, null);
        } else {
            return new Node(val, false, nodes[0], nodes[1], nodes[2], nodes[3]);
        }
    }

    private boolean checkLeaf(int[][] grid, int left, int top, int length) {
        int val = grid[left][top];
        boolean isLeaf = true;
        for (int i = left, row = left + length; i < row; ++i) {
            for (int j = top, col = top + length; j < col; ++j) {
                if (grid[i][j] != val) {
                    return false;
                }
            }
        }

        return true;
    }
}
