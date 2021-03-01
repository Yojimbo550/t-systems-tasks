package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */



    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here

        boolean possibilityOfBuild;//var for check possibility of build a pyramid

        int[][]pyramid;
        int quantityOfInputNumbers = inputNumbers.size();

        //check possibility of build with quantity of input numbers
        int countOfTheoreticalPyramid = 0;
        int rows = 1;
        int columns = 1;

        while(countOfTheoreticalPyramid < quantityOfInputNumbers){

            countOfTheoreticalPyramid = countOfTheoreticalPyramid + rows;
            rows++;
            columns = columns + 2;
        }

        rows = rows - 1;//current number of rows
        columns = columns - 2;//current number of columns

        possibilityOfBuild = quantityOfInputNumbers == countOfTheoreticalPyramid && rows > 0 && columns > 0;//if we can build a pyramid
        for (Integer i: inputNumbers            //check nulls
        ) {
            if (i == null) {
                possibilityOfBuild = false;

                break;
            }

        }

        if(possibilityOfBuild) {

            List<Integer> sorted = inputNumbers.stream().sorted().collect(Collectors.toList());
            pyramid = new int[rows][columns];
            for (int[] row : pyramid) {
                Arrays.fill(row, 0);
                int center = (columns / 2);//central point of pyramid
                quantityOfInputNumbers = 1; // quantity of numbers in a row
                int arrIdx = 0; // array index

                for (int i = 0, offset = 0; i < rows; i++, offset++, quantityOfInputNumbers++) {
                    int start = center - offset;
                    for (int j = 0; j < quantityOfInputNumbers * 2; j += 2, arrIdx++) {
                        pyramid[i][start + j] = sorted.get(arrIdx);
                    }
                }


                for (int[] a : pyramid)//pyramid in console
                {
                    for (int b : a)
                        System.out.print(b + " ");
                    System.out.println();
                }
            }
        }
        else{
            throw new CannotBuildPyramidException();
        }

        return pyramid;
    }



}
