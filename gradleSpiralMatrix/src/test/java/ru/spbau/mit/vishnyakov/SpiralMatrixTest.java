package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpiralMatrixTest {

    /**
     * First case is trivial, second one is more complex,
     * but still pretty straightforward.
     * Finally the third one tests the case of equality of the first two elements.
     */

    @Test
    public void sortMatrixByColumns_1x1() throws Exception {

        SpiralMatrix m = new SpiralMatrix(1, new int[][]{{1}});
        m.sortMatrixByColumns();
        assertArrayEquals(new int[]{1}, m.getMatrix()[0]);

    }

    @Test
    public void sortMatrixByColumns_3x3() throws Exception {

        SpiralMatrix m = new SpiralMatrix(3, new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m.sortMatrixByColumns();
        assertArrayEquals(new int[]{3, 2, 1}, m.getMatrix()[0]);
        assertArrayEquals(new int[]{6, 5, 4}, m.getMatrix()[1]);
        assertArrayEquals(new int[]{9, 8, 7}, m.getMatrix()[2]);

    }

    @Test
    public void sortMatrixByColumns_3x3_startingElementsAreEqual() throws Exception {

        SpiralMatrix m = new SpiralMatrix(3, new int[][]{{6, 6, 9}, {5, 5, 0}, {3, 1, 0}});
        m.sortMatrixByColumns();
        assertArrayEquals(new int[]{9, 6, 6}, m.getMatrix()[0]);
        assertArrayEquals(new int[]{0, 5, 5}, m.getMatrix()[1]);
        assertArrayEquals(new int[]{0, 3, 1}, m.getMatrix()[2]);

    }

    /**
     * Tests the order of spiral output.
     */

    @Test
    public void spiralOutput_1x1() throws Exception {

        SpiralMatrix m = new SpiralMatrix(1, new int[][]{{1}});
        String res = m.spiralOutput();
        assertEquals("1 ", res);

    }

    @Test
    public void spiralOutput_3x3() throws Exception {

        SpiralMatrix m = new SpiralMatrix(3, new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        String res = m.spiralOutput();
        assertEquals("5 2 3 6 9 8 7 4 1 ", res);

    }

    @Test
    public void spiralOutput_5x5() throws Exception {

        SpiralMatrix m = new SpiralMatrix(5, new int[][]{ {1, 2, 3, 4, 5},
                                                {6, 7, 8, 9, 10},
                                                {11, 12, 13, 14, 15},
                                                {16, 17, 18, 19, 20},
                                                {21, 22, 23, 24, 25}});

        String res = m.spiralOutput();
        assertEquals("13 8 9 14 19 18 17 12 7 2 3 4 5 10 15 20 25 24 23 22 21 16 11 6 1 ", res);

    }

    /**
     * Tests the transpose function.
     */

    @Test
    public void transposeMatrix_1x1() throws Exception {

        SpiralMatrix m = new SpiralMatrix(1, new int[][]{{1}});
        m.transposeMatrix();
        assertArrayEquals(new int[]{1}, m.getMatrix()[0]);

    }

    @Test
    public void transposeMatrix_3x3() throws Exception {

        SpiralMatrix m = new SpiralMatrix(3, new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m.transposeMatrix();
        assertArrayEquals(new int[]{1, 4, 7}, m.getMatrix()[0]);
        assertArrayEquals(new int[]{2, 5, 8}, m.getMatrix()[1]);
        assertArrayEquals(new int[]{3, 6, 9}, m.getMatrix()[2]);
    }

    @Test
    public void transposeMatrix_5x5() throws Exception  {

        SpiralMatrix m = new SpiralMatrix(5, new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}});
        m.transposeMatrix();
        assertArrayEquals(new int[]{1, 6, 11, 16, 21}, m.getMatrix()[0]);
        assertArrayEquals(new int[]{2, 7, 12, 17, 22}, m.getMatrix()[1]);
        assertArrayEquals(new int[]{3, 8, 13, 18, 23}, m.getMatrix()[2]);
        assertArrayEquals(new int[]{4, 9, 14, 19, 24}, m.getMatrix()[3]);
        assertArrayEquals(new int[]{5, 10, 15, 20, 25}, m.getMatrix()[4]);

    }

    /**
     * Tests sorting by rows.
     */

    @Test
    public void sortingByRows_1x1() throws Exception {

        SpiralMatrix m = new SpiralMatrix(1, new int[][]{{1}});
        m.transposeMatrix();
        assertArrayEquals(new int[]{1}, m.getMatrix()[0]);

    }

    @Test
    public void sortingByRows_3x3() throws Exception {

        SpiralMatrix m = new SpiralMatrix(3, new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        m.sortMatrixByRows();
        assertArrayEquals(new int[]{7, 8, 9}, m.getMatrix()[0]);
        assertArrayEquals(new int[]{4, 5, 6}, m.getMatrix()[1]);
        assertArrayEquals(new int[]{1, 2, 3}, m.getMatrix()[2]);

    }

    @Test
    public void sortingByRows_3x3_startingElementsAreEqual() throws Exception {

        SpiralMatrix m = new SpiralMatrix(3, new int[][]{{5, 5, 6}, {5, 5, 5}, {7, 8, 0}});
        m.sortMatrixByRows();
        assertArrayEquals(new int[]{7, 8, 0}, m.getMatrix()[0]);
        assertArrayEquals(new int[]{5, 5, 6}, m.getMatrix()[1]);
        assertArrayEquals(new int[]{5, 5, 5}, m.getMatrix()[2]);

    }

}