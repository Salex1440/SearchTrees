package com.company.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.company.AvlTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.company.MyException.OutOfTreeException;

class AvlTreeTest {

    private static AvlTree avlTree;

    @BeforeAll
    static void initTree() {
        avlTree = new AvlTree();
        for (int i = 0; i < 10; i++) {
            avlTree.add(i);
        }
    }

    @Test
    void notNull() {
        assertNotNull(avlTree);
    }

    @Test
    void find() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, avlTree.find(i));
        }
    }

    @Test
    void maxValue() {
        assertEquals(9, avlTree.maxValue());
    }

    @Test
    void minValue() {
        assertEquals(0, avlTree.minValue());
    }

    @Test
    void next() {
        for (int i = 0; i < 9; i++) {
            assertEquals(i + 1, avlTree.next(i));
        }
        assertEquals(null, avlTree.next(9));
    }

    @Test
    void exceptionTest() {
        avlTree.goRight();
        avlTree.goRight();
        avlTree.goRight();
        assertThrows(OutOfTreeException.class, () -> avlTree.goRight());
    }

}