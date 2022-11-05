package it.frascu.adventofcode;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class Day11 {

    public static void main(String[] args) {
        int length = 10;
        int[][] matrix = new int[length][];
        try (Scanner scanner = new Scanner(Day11.class.getClassLoader().getResourceAsStream("Day11.txt"))) {
            for (int i = 0; i < length; i++) {
                matrix[i] = scanner.nextLine().trim().chars().map(c -> c - '0').toArray();
            }
        }

        calculate(length, matrix);
    }

    private static void calculate(int length, int[][] matrix) {
        int numberOfFlashed = 0;
        int step = 0;
        while(!areAllZero(matrix)) {
            LinkedList<Pair<Integer, Integer>> toFlash = new LinkedList<>();
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    matrix[i][j]++;
                    if (matrix[i][j] > 9) {
                        toFlash.add(new Pair<>(i, j));
                    }
                }
            }

            while (!toFlash.isEmpty()) {
                Pair<Integer, Integer> octopusToFlash = toFlash.remove();
                int i = octopusToFlash.getValue0();
                int j = octopusToFlash.getValue1();

                if (matrix[i][j] > 9) {
                    List<Pair<Integer, Integer>> adjacentOctopuses = new ArrayList<>();

                    //me
                    matrix[i][j] = 0;
                    numberOfFlashed++;

                    //up
                    if (i > 0 && j < length - 1) adjacentOctopuses.add(new Pair<>(i - 1, j + 1));
                    if (i > 0) adjacentOctopuses.add(new Pair<>(i - 1, j));
                    if (i > 0 && j > 0) adjacentOctopuses.add(new Pair<>(i - 1, j - 1));

                    //center
                    if (j < length - 1) adjacentOctopuses.add(new Pair<>(i, j + 1));
                    if (j > 0) adjacentOctopuses.add(new Pair<>(i, j - 1));

                    //down
                    if (i < length - 1 && j > 0) adjacentOctopuses.add(new Pair<>(i + 1, j - 1));
                    if (i < length - 1) adjacentOctopuses.add(new Pair<>(i + 1, j));
                    if (i < length - 1 && j < length - 1) adjacentOctopuses.add(new Pair<>(i + 1, j + 1));

                    adjacentOctopuses.forEach(adjacent -> {
                        if (matrix[adjacent.getValue0()][adjacent.getValue1()] > 0) {
                            matrix[adjacent.getValue0()][adjacent.getValue1()]++;
                            if (matrix[adjacent.getValue0()][adjacent.getValue1()] > 9) {
                                toFlash.add(adjacent);
                            }
                        }
                    });
                }
            }

            if (step == 99) {
                System.out.println("Part 1: " + numberOfFlashed);
            }
            step++;
        }

        System.out.println("Part 2: " + step);
    }

    private static boolean areAllZero(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 0) {
                    return false;
                }
            }
        }
        return true;
    }


}






