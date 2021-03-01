package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {

        boolean result;
        if (x == null || y == null) throw new IllegalArgumentException();
        List sortedX;                   //sorted list x
        List listZ = new ArrayList<>();
        int countForX = 0;

        for (int i = 0; i < x.size(); i++) {
            for (int j = 0; j < y.size(); j++) {


                if (x.get(i).equals(y.get(j))) {


                    try {
                        listZ.add(y.get(j));
                        countForX++;
                    } catch (IndexOutOfBoundsException exc) {
                        result = false;
                        break;
                    }


                }
            }
        }

            for (int i = 0; i < listZ.size(); i++) {            //check duplicate element
                for (int j = 1; j < i; j++) {
                    if (listZ.get(i).equals(listZ.get(j))) {
                        listZ.remove(j);
                    }
                }
            }
            sortedX = listZ;
            Collections.sort(sortedX);

            if (x.equals(listZ) && x.equals(sortedX)) { //check order of elements
                result = true;
            } else {
                result = false;
            }


            return result;
        }


}
