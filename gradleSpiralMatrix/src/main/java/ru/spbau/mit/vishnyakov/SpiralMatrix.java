package ru.spbau.mit.vishnyakov;

import java.util.Arrays;

/**
 * Implementation of SpiralMatrix with two methods: sorting by columns and spiral output.
 */

public class SpiralMatrix {

    /**
     * 2d array that represents matrix.
     */

    private int [][] matrix;

    /**
     * Constructor which receives size of matrix and its elements.
     * @param array - array of which matrix is being constructed.
     */

    public SpiralMatrix(int[][] array) {

        if (array == null || array.length % 2 == 0) {
            throw new IllegalArgumentException("It's not a correct input!");
        }

        for (int[] a : array) {
            if (a.length != array.length) {
                throw new IllegalArgumentException("It's not a correct input!");
            }
        }

        int n = array.length;
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(array[i], 0, matrix[i], 0, n);
        }
    }

    /**
     * Outputs matrix in spiral order.
     * Start in the center. Initially we have to go 1 move up, and then 1 right.
     * Note that number of moves that we went up uniquely determines the number of moves we have to go down,
     * when we are going in that direction and vice versa.
     * If we just have gone "n" moves top, then we will have to go "n + 1" moves down.
     * The same we can say for left and right directions.
     * So we just keep track of the current direction and number of moves for top/down and left/right directions.
     * Finally we have to stop, when number of the moves we have made is equal to n^2.
     * @return string that represents order of output.
     */

    public String spiralOutput() {

        int n = matrix.length;
        int curRow = n / 2;
        int curCol = n / 2;
        int counter = 0; //number of moves we've made

        int direction = 0; // directions: 0 - up, 1 - right, 2 - down, 3 - left.
        int top = 1, down = 0, right = 1, left = 0; //number of moves we have to go in each direction.

        StringBuilder result = new StringBuilder();

        while (counter < n * n) {
            if (direction == 0) {
                for (int i = 0; i < top; i++) {
                    result.append(matrix[curRow][curCol]).append(" ");
                    curRow--;
                    counter++;
                }
                direction = 1;
                down = top + 1;
            }
            else if (direction == 1) {
                for (int i = 0; i < right; i++) {
                    result.append(matrix[curRow][curCol]).append(" ");
                    curCol++;
                    counter++;
                }
                direction = 2;
                left = right + 1;
            }
            else if (direction == 2) {
                for (int i = 0; i < down; i++) {
                    result.append(matrix[curRow][curCol]).append(" ");
                    curRow++;
                    counter++;
                }
                direction = 3;
                top = down + 1;
            }
            else {
                for (int i = 0; i < left; i++) {
                    result.append(matrix[curRow][curCol]).append(" ");
                    curCol--;
                    counter++;
                }
                direction = 0;
                right = left + 1;
            }
        }
        return result.toString();
    }


    /**
     * Method that transposes matrix.
     * I wanted to make this method private, but in this case there is no option to test this method.
     */

    public void transposeMatrix() {

        int n = matrix.length;
        int [][] transposedMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }
        matrix = transposedMatrix;
    }

    /**
     * Method that sorts matrix by rows.
     * I wanted to make this method private, but in this case there is no option to test this method.
     */

    public void sortMatrixByRows() {

        Arrays.sort(matrix, (a, b) -> {

            int pos = 0;
            int size = a.length;
            while (pos < size && a[pos] == b[pos]) {
                pos++;
            }

            if (pos == size) {
                return 0;
            }

            if (a[pos] < b[pos]) {
                return 1;
            }

            return -1;
        });
    }


    /**
     * Method that sorts matrix by columns.
     */

    public void sortMatrixByColumns() {

        this.transposeMatrix();
        this.sortMatrixByRows();
        this.transposeMatrix();

    }


    /**
     * Method that outputs matrix in the default way.
     */

    public void simpleOutput() {

        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
