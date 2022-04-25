package com.company;

import com.company.MyException.OutOfTreeException;

public class AvlTree {

    class Node {
        Integer value;
        Node parent;
        Node left;
        Node right;
        int height;

        public Node() {
            value = null;
            parent = this;
            left = null;
            right = null;
            height = 0;
        }

        public Node(Node n) {
            value = n.value;
            parent = n.parent;
            left = n.left;
            right = n.right;
            height = n.height;
        }
    }

    private Node node;

    private Node root;

    boolean restored = true;

    public AvlTree() {
        node = new Node();
        root = node;
    }

    public Integer find(Integer obj) {

        restore();
        Integer result = null;
        if (node.value == null) {
            node = node.parent;
            return null;
        } else if (obj.compareTo(node.value) == 0) {
            result = node.value;
            node = node.parent;
            return result;
        } else if (obj.compareTo(node.value) < 0) {
            node = node.left;
            result = find(obj);
            node = node.parent;
        } else if (obj.compareTo(node.value) > 0) {
            node = node.right;
            result = find(obj);
            node = node.parent;
        }
        return result;
    }

    private void updateHeight() {
        while (true) {
            if (node.value == null) { // node was deleted
                node.height = 0;
            } else if (node.left.value == null && node.right.value == null) { // node is a leave
                node.height = 1;
            } else {
                int h = (node.left.height > node.right.height) ? node.left.height : node.right.height;
                node.height = h + 1;
            }
            if (node.parent.value == node.value) {
                break;
            }
            node = node.parent;
        }
    }

    private void balanceTree(Node currNode) {
        Node left = currNode.left;
        Node right = currNode.right;
        Node parent = currNode.parent;
        if (left.height <= 1 && right.height <= 1) {
            return;
        }
        if (left.height - right.height > 1) {
            System.out.format("%d is unbalanced(left)!%n", currNode.value);
            if (left.left.height > left.right.height) {
                // Rotate right.
                Node rotated = rotateRight(currNode);
                if (root.value == currNode.value) {
                    rotated.parent = rotated;
                    root = rotated;
                } else if (parent.left == currNode) {
                    parent.left = rotated;
                } else if (parent.right == currNode) {
                    parent.right = rotated;
                }
            } else {
                // Big rotate right.
                currNode.left = rotateLeft(left);
                Node rotated = rotateRight(currNode);
                if (root.value == currNode.value) {
                    rotated.parent = rotated;
                    root = rotated;
                } else if (parent.left == currNode) {
                    parent.left = rotated;
                } else if (parent.right == currNode) {
                    parent.right = rotated;
                }
            }
            updateHeight();
        } else if (right.height - left.height > 1) {
            System.out.format("%d is unbalanced(right)!%n", currNode.value);
            if (right.right.height > right.left.height) {
                // Rotate left.
                Node rotated = rotateLeft(currNode);
                if (root.value == currNode.value) {
                    rotated.parent = rotated;
                    root = rotated;
                } else if (parent.left == currNode) {
                    parent.left = rotated;
                } else if (parent.right == currNode) {
                    parent.right = rotated;
                }
            } else {
                // Big rotate left.
                currNode.right = rotateRight(right);
                Node rotated = rotateLeft(currNode);
                if (root.value == currNode.value) {
                    rotated.parent = rotated;
                    root = rotated;
                } else if (parent.left == currNode) {
                    parent.left = rotated;
                } else if (parent.right == currNode) {
                    parent.right = rotated;
                }
            }
            updateHeight();
        } else {
            balanceTree(left);
            balanceTree(right);
        }
    }

    private Node rotateRight(Node p) {
        Node q = p.left;
        Node B = q.right;
        p.left = B;
        q.right = p;
        q.parent = p.parent;
        p.parent = q;
        B.parent = p;
        p.height = 1 + (p.right.height > p.left.height ? p.right.height : p.left.height);
        q.height = 1 + (q.right.height > q.left.height ? q.right.height : q.left.height);
        return q;
    }

    private Node rotateLeft(Node q) {
        Node p = q.right;
        Node B = p.left;
        q.right = B;
        p.left = q;
        p.parent = q.parent;
        q.parent = p;
        B.parent = q;
        q.height = 1 + (q.right.height > q.left.height ? q.right.height : q.left.height);
        p.height = 1 + (p.right.height > p.left.height ? p.right.height : p.left.height);
        return p;
    }

    public void add(Integer obj) {

        restore();

        if (node.value == null) {
            node.value = obj;
            Node left = new Node();
            left.parent = node;
            node.left = left;
            Node right = new Node();
            right.parent = node;
            node.right = right;
            updateHeight();
            balanceTree(node);
            return;
        } else if (obj.compareTo(node.value) == 0) {
            node = node.parent;
            return;
        } else if (obj.compareTo(node.value) < 0) {
            node = node.left;
            add(obj);
            node = node.parent;
        } else if (obj.compareTo(node.value) > 0) {
            node = node.right;
            add(obj);
            node = node.parent;
        }
    }

    public void remove(Integer obj) {

        restore();

        if (node.value == null) {
            node = node.parent;
        } else if (obj.compareTo(node.value) == 0) {
            if (node.left.value == null && node.right.value == null) { // node is a leave.
                node.value = null;
                node.left = null;
                node.right = null;
            } else if (node.left.value != null && node.right.value == null) { // node has left child
                if (node.parent == node) { // if node is root
                    root = node.left;
                    root.parent = root;
                    node = root;
                } else if (node.parent.right == node) { // check if current node is a right child of its parent
                    node.parent.right = node.left;
                    node = node.parent;
                } else if (node.parent.left == node) { // check if current node is a left child of its parent
                    node.parent.left = node.left;
                    node = node.parent;
                }
            } else if (node.left.value == null && node.right.value != null) { // node has right child
                if (node.parent == node) { // if node is root
                    root = node.right;
                    root.parent = root;
                    node = root;
                } else if (node.parent.right == node) { // check if current node is a right child of its parent
                    node.parent.right = node.right;
                    node = node.parent;
                } else if (node.parent.left == node) { // check if current node is a left child of its parent
                    node.parent.left = node.right;
                    node = node.parent;
                }
            } else { // node has left and right children
                // Needed to find prescending node for removing node.
                // It's the rightest node in the left subtree.
                Node presNode = node.left;
                while (presNode.right.value != null) {
                    presNode = presNode.right;
                }
                node.value = presNode.value;
                if (presNode.left.value != null) { // if prescending node has left leave.
                    presNode.value = presNode.left.value;
                    presNode.left.value = null; // and remove left leave
                } else { // else if it's a leave itself.
                    presNode.value = null; // remove it.
                    presNode.height = 0;
                }
            }
            updateHeight();
            balanceTree(node);
        } else if (obj.compareTo(node.value) < 0) {
            node = node.left;
            remove(obj);
            node = node.parent;
        } else if (obj.compareTo(node.value) > 0) {
            node = node.right;
            remove(obj);
            node = node.parent;
        }
    }

    public Integer maxValue(){
        restore();
        while (node.right.value != null) {
            node = node.right;
        }
        Integer result = node.value;
        while (node.parent != node) {
            node = node.parent;
        }
        return result;
    }

    public Integer minValue(){
        restore();
        while (node.left.value != null) {
            node = node.left;
        }
        Integer result = node.value;
        while (node.parent != node) {
            node = node.parent;
        }
        return result;
    }

    public Integer next(Integer obj) {
        Integer result = null;
        restore();

        if (node.value == null) {
            node = node.parent;
            return null;
        } else if (obj.compareTo(node.value) == 0) {
            // TODO: Find next.
            result = findNext();
            while (node != node.parent) {
                node = node.parent;
            }
            return result;
        } else if (obj.compareTo(node.value) < 0) {
            node = node.left;
            result = next(obj);
            node = node.parent;
        } else if (obj.compareTo(node.value) > 0) {
            node = node.right;
            result = next(obj);
            node = node.parent;
        }

        return result;
    }

    private Integer findNext() {
        if (node.right.value != null) {
            node = node.right;
            while (node.left.value != null) {
                node = node.left;
            }
            return node.value;
        } else {
            while (node.parent.right == node) {
                node = node.parent;
            }
            if (node.parent.left == node) {
                return node.parent.value;
            }
            return null;
        }
    }

    public Integer current() {
        return node.value;
    }

    public Integer parent() {
        return node.parent.value;
    }

    public Integer right() {
        return node.right.value;
    }

    public Integer left() {
        return node.left.value;
    }

    public Integer height() {
        return node.height;
    }

    public void goUp() {
        node = node.parent;
        if (node == node.parent) restored = true;
    }

    public boolean goLeft() throws OutOfTreeException {
        if (node.left.value == null) {
            throw new com.company.MyException.OutOfTreeException("No left child!");
        }
        restored = false;
        node = node.left;
        return true;
    }

    public boolean goRight() throws OutOfTreeException {
        if (node.right.value == null) {
            throw new com.company.MyException.OutOfTreeException("No right child!");
        }
        restored = false;
        node = node.right;
        return true;
    }

    public void restore(){
        while (restored == false) {
            goUp();
        }
    }

}
