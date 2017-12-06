package com.dmitr.romashov;

public interface IRedBlackTree {
    void insert(int newElement);
    void delete(int element);
    RedBlackTree.Node findNode(int key);
}
