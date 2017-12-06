package com.dmitr.romashov;


import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(9);
        queue.add(7);
        queue.add(11);
        queue.poll();
        RedBlackTree redBlackTree = new RedBlackTree();
        // example from book with three cases
        redBlackTree.insert(11);
        redBlackTree.insert(2);
        redBlackTree.insert(14);
        redBlackTree.insert(15);
        redBlackTree.insert(7);
        redBlackTree.insert(1);
        redBlackTree.insert(8);
        redBlackTree.insert(5);
//        redBlackTree.printTree();
//        redBlackTree.insert(4);
//
//        redBlackTree.printTree();

//        // delete red node
//        redBlackTree.delete(15);
//        redBlackTree.printTree();
//
//        // delete black node 1
//        redBlackTree.delete(1);
//        redBlackTree.printTree();
//
        // delete black root 7
        redBlackTree.delete(11);
        redBlackTree.printTree();

    }
}
