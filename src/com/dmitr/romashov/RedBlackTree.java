package com.dmitr.romashov;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree implements IRedBlackTree {

    public Node root;
    public Node nil;

    enum NodeColor {
        RED,
        BLACK
    }

    public class Node {

        public int key;
        public NodeColor color;
        public Node parent;
        public Node left;
        public Node right;

        private Node(int value, NodeColor color) {
            this.key = value;
            this.color = color;
            parent = nil;
            left = nil;
            right = nil;

        }

        private Node() {

        }

    };

    public RedBlackTree() {
        nil = new Node();
        nil.color = NodeColor.BLACK;
        root = nil;
        root.parent = nil;
        root.left = nil;
        root.right = nil;

    }


    @Override
    public Node findNode(int value) {
        Node current = root;

        while (current != nil){
            if (current.key == value) return current;
            if (value < current.key){
                current = current.left;
            }
            else {
                current = current.right;
            }
        }
        if (current == nil){
            System.out.println("Sorry, there is no node with such key = " + value);
        }
        return null;
    }



    /**
     * Left rotate tree T relatively node x
     *
     * @param T tree
     * @param x node
     */
    private void leftRotate(RedBlackTree T, Node x) {
        Node y = x.right; // y - right child of x, which become parent of x
        x.right = y.left;

        if (y.left != T.nil) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == T.nil) {
            T.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    /**
     * Right rotate tree T relatively node y
     *
     * @param T tree
     * @param y node
     */
    private void rightRotate(RedBlackTree T, Node y) {
        Node x = y.left;
        y.left = x.right;

        if (x.right != T.nil) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == T.nil) {
            T.root = x;
        } else if (y.parent.left == y) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        x.right = y;
        y.parent = x;
    }

    @Override
    public void insert(int value) {
        Node y = nil; // parent of z
        Node x = root;
        Node z = new Node(value, NodeColor.RED); // inserted node

        while (x != nil) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == nil) {
            root = z;

        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }

        fixInsert(z);

    }

    private void fixInsert(Node z) {
        while (z.parent.color == NodeColor.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right; // uncle
                if (y.color == NodeColor.RED) { // first case: red uncle
                    z.parent.color = NodeColor.BLACK;
                    y.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) { // second case: uncle of z black and z ia a right son
                        z = z.parent;
                        leftRotate(this, z);
                    }
                    z.parent.color = NodeColor.BLACK; // third case: uncle of z black and z ia a left son
                    z.parent.parent.color = NodeColor.RED;
                    rightRotate(this, z.parent.parent);
                }
            } else { // symmetric
                Node y = z.parent.parent.left; // uncle
                if (y.color == NodeColor.RED) {
                    z.parent.color = NodeColor.BLACK;
                    y.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(this, z);
                    }
                    z.parent.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    leftRotate(this, z.parent.parent);
                }
            }
        }
        this.root.color = NodeColor.BLACK;
    }

    public void printTree() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(root);
        printNodes(nodes);
    }

    private void printNodes(List<Node> nodes) {
        int childsCounter = 0;
        int nodesAmount = nodes.size();
        List<Node> childs = new ArrayList<>();

        for (int i = 0; i < nodesAmount; i++) {
            if (nodes.get(i) != nil) {
                System.out.print("(" + nodes.get(i).key + "," + nodes.get(i).color + ") ");
                if (nodes.get(i).left != nil) {
                    childs.add(nodes.get(i).left);
                    ++childsCounter;
                } else childs.add(nil);
                if (nodes.get(i).right != nil) {
                    childs.add(nodes.get(i).right);
                    ++childsCounter;
                } else childs.add(nil);

            } else {
                System.out.print("(nil)");
            }
        }
        System.out.println();
        if (childsCounter != 0) printNodes(childs);
    }

    /**
     * Put node v in place of u
     *
     * @param u
     * @param v
     */
    private void rbTransplant(Node u, Node v) {
        if (u.parent == nil) {
            root = v;
        }
        else if (u == u.parent.left) {

            u.parent.left = v;
        }
        else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    @Override
    public void delete(int value) {
        Node deletedNode = findNode(value);
        if (deletedNode != null){
            deleteNode(deletedNode);
        }
        else {
            System.out.println("Not such node");
        }
    }

    public void deleteNode(Node z){
        // init y and yOriginalColor
        // y deleted node or moved
        Node y = z;
        NodeColor yOriginalColor = y.color;
        Node x; // take place of y

        if (z.left == nil){
            x = z.right;
            rbTransplant(z, z.right);
        }
        else if (z.right == nil){
            x = z.left;
            rbTransplant(z, z.left);
        }
        else {
            y = findMin(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z){
                x.parent = y;
            }
            else {
                rbTransplant(y, x);
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color; // same color as z
        }
        // x superblack, +1 to black on each way
        if (yOriginalColor == NodeColor.BLACK) deleteFixup(x);

    }

    private void deleteFixup(Node x) {
        while (x != root && x.color == NodeColor.BLACK){
            if (x == x.parent.left){
                Node w = x.parent.right; // brother x
                if (w.color == NodeColor.RED){ // first case, brother of x w node red
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    leftRotate(this, x.parent);
                    w = x.parent.right;
                }
                // second case: w black and brothers w black
                if (w.left.color == NodeColor.BLACK && w.right.color == NodeColor.BLACK){
                    w.color = NodeColor.RED;
                    x = x.parent;
                }
                // third case: brother of x w black, left child w - red, right - black
                else  {
                    if(w.right.color == NodeColor.BLACK){
                        w.left.color = NodeColor.BLACK;
                        w.color = NodeColor.RED;
                        rightRotate(this, w);
                        w = x.parent.right;
                    }
                    // w - black, right child w red
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.right.color = NodeColor.BLACK;
                    leftRotate(this, x.parent);
                    x = root;
                }
            }
            else { // symmetric
                Node w = x.parent.left;
                if (w.color == NodeColor.RED){
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rightRotate(this, x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == NodeColor.BLACK && w.left.color == NodeColor.BLACK){
                    w.color = NodeColor.RED;
                    x = x.parent;
                }
                else {
                    if(w.left.color == NodeColor.BLACK){
                        w.right.color = NodeColor.BLACK;
                        w.color = NodeColor.RED;
                        leftRotate(this, w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.left.color = NodeColor.BLACK;
                    rightRotate(this, x.parent);
                    x = root;
                }

            }
        }
        x.color = NodeColor.BLACK;

    }

    private Node findMin(Node q){
        Node y = q;
        while (y.left != nil){
            y = y.left;
        }
        return y;
    }
}
