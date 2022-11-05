package it.frascu.adventofcode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day05 {

    public static void main(String[] args) {
        InputStream is = Day05.class.getClassLoader().getResourceAsStream("Day05.txt");

        int maxDim = Integer.MIN_VALUE;
        List<Integer[]> vents = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String[] points = scanner.nextLine().split("->");

                Integer[] x = Arrays.stream(points[0].trim().split(",")).map(Integer::valueOf).toArray(Integer[]::new);
                Integer[] y = Arrays.stream(points[1].trim().split(",")).map(Integer::valueOf).toArray(Integer[]::new);
                Integer[] vent = Stream.concat(Arrays.stream(x), Arrays.stream(y)).toArray(Integer[]::new);
                vents.add(vent);
                maxDim = Math.max(maxDim, Arrays.stream(vent).max(Integer::compareTo).orElse(Integer.MIN_VALUE));
            }
        }

        System.out.println("Part 1: " + start(maxDim + 1, vents, false));
        System.out.println("Part 2: " + start(maxDim + 1, vents, true));
    }

    private static int start(int dim, List<Integer[]> vents, boolean includeDiagonalVents) {
        int[][] matrix = new int[dim][dim];
        for (Integer[] vent : vents) {
            int x1 = vent[0];
            int x2 = vent[1];
            int horizontalSteps = x1 - vent[2];
            int verticalSteps = x2 - vent[3];
            if (!includeDiagonalVents && horizontalSteps != 0 && verticalSteps != 0) {
                continue;
            }
            while (horizontalSteps != 0 || verticalSteps != 0) {
                matrix[x2][x1] += 1;
                if (horizontalSteps < 0) {
                    x1++;
                    horizontalSteps++;
                } else if (horizontalSteps > 0) {
                    x1--;
                    horizontalSteps--;
                }
                if (verticalSteps < 0) {
                    x2++;
                    verticalSteps++;
                } else if (verticalSteps > 0) {
                    x2--;
                    verticalSteps--;
                }
            }
            matrix[x2][x1] += 1;
        }
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (matrix[i][j] > 1) {
                    count++;
                }
            }
        }
        return count;
    }


}

