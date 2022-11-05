package it.frascu.adventofcode;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class Day13 {

    public static void main(String[] args) {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        Set<Pair<Integer, Integer>> coordinates = new HashSet<>();
        List<Pair<String, Integer>> instructions = new ArrayList<>();
        try (Scanner scanner = new Scanner(Day13.class.getClassLoader().getResourceAsStream("Day13.txt"))) {

            boolean areCoordinates = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    areCoordinates = false;
                    continue;
                }

                if (areCoordinates) {
                    String[] split = line.split(",");
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
                    coordinates.add(new Pair<>(x, y));
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                } else {
                    String[] instruction = line.split(" +")[2].split("=");
                    instructions.add(new Pair<>(instruction[0], Integer.valueOf(instruction[1])));
                }
            }
        }

        int i = 0;
        for (Pair<String, Integer> instruction : instructions) {
            Set<Pair<Integer, Integer>> newCoordinates = new HashSet<>();
            for (Pair<Integer, Integer> coordinate : coordinates) {
                int x = coordinate.getValue0();
                int y = coordinate.getValue1();
                if ("y".equals(instruction.getValue0()) && y > instruction.getValue1()) {
                    maxY = instruction.getValue1();
                    y = calculateNewPos(maxY, instruction.getValue1(), y);
                } else if ("x".equals(instruction.getValue0()) && x > instruction.getValue1()) {
                    maxX = instruction.getValue1();
                    x = calculateNewPos(maxX, instruction.getValue1(), x);
                }
                newCoordinates.add(new Pair<>(x, y));
            }
            coordinates = newCoordinates;

            i++;
            if (i == 1) {
                System.out.println("Part 1: " + coordinates.size());
            }
        }

        System.out.println("Part 2: ");
        print(maxX, maxY, coordinates);
    }

    private static void print(int maxX, int maxY, Set<Pair<Integer, Integer>> coordinates) {
        char[][] matrix = new char[maxY + 1][maxX + 1];
        for (char[] chars : matrix) {
            Arrays.fill(chars, ' ');
        }
        for (Pair<Integer, Integer> coordinate : coordinates) {
            matrix[coordinate.getValue1()][coordinate.getValue0()] = 'X';
        }
        IntStream.range(0, maxY).forEach(i -> System.out.println(Arrays.toString(matrix[i])));
    }

    private static int calculateNewPos(int maxLength, int fold, int actualPos) {
        int differenceY = (maxLength - fold);
        int baseStart = fold - differenceY;
        int newStart = maxLength - actualPos;
        return baseStart + newStart;
    }


}







