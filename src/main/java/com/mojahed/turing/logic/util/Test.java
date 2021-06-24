package com.mojahed.turing.logic.util;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

public class Test {
    public static void main(String[] args) {
        DoublyLikedList<String> doublyLikedList = new DoublyLikedList<>();

        doublyLikedList.addFirst("1");
        doublyLikedList.addLast("2");
        doublyLikedList.addLast("3");
        doublyLikedList.addFirst("4");

        System.out.println(doublyLikedList.toString());
        System.out.println(doublyLikedList.hasNext());
        System.out.println(doublyLikedList.hasPrevious());

        doublyLikedList.goNext();
        doublyLikedList.goNext();
        System.out.println(doublyLikedList.toString());
        System.out.println(doublyLikedList.hasNext());
        System.out.println(doublyLikedList.hasPrevious());

        doublyLikedList.addAfterCurrent("after1");
        doublyLikedList.addBeforeCurrent("before1");
        doublyLikedList.deleteCurrent();
        System.out.println(doublyLikedList.toString());

        doublyLikedList.deleteLast();
        System.out.println(doublyLikedList.toString());

        doublyLikedList.goPrevious();
        System.out.println(doublyLikedList.toString());
        System.out.println(doublyLikedList.hasNext());
        System.out.println(doublyLikedList.hasPrevious());




    }
}
