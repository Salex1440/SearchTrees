package com.company;

public class Main {

    public static void showNode(AvlTree avlTree) {
        System.out.println("**********************************");
        System.out.format("current: %d; parent: %d; left: %d; right: %d; height= %d;%n",
                avlTree.current(), avlTree.parent(), avlTree.left(), avlTree.right(), avlTree.height());
        System.out.println("**********************************");
    }

    public static void main(String[] args) {

        AvlTree avlTree = new AvlTree();


        for (int i = 0; i < 30; i++) {
            avlTree.add(i);
        }
        showNode(avlTree);
        avlTree.goLeft();
        avlTree.goLeft();
        showNode(avlTree);


        /*avlTree.add(5);
        showNode(avlTree);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(6);
        avlTree.add(8);
        avlTree.add(3);
        showNode(avlTree);

        System.out.println("Remove 1.");
        avlTree.remove(1);
        showNode(avlTree);

        avlTree.goLeft();
        showNode(avlTree);*/


        /*for (int i = 1; i < 9; i++) {
            System.out.println(avlTree.next(i));
        }*/



    }
}
