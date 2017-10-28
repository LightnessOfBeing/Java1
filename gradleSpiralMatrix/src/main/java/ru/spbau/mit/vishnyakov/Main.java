package ru.spbau.mit.vishnyakov;

/**
 * Main class of a program.
 * Illustrates some functional of the program.
 */

public class Main {

    public static void main(String[] args) {

        int[][] array = new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        SpiralMatrix m = new SpiralMatrix(array);
        m.simpleOutput();
        m.sortMatrixByColumns();
        m.simpleOutput();
    }
}
