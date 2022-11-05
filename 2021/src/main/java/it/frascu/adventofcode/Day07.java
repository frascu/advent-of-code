package it.frascu.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day07 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(Day07.class.getClassLoader().getResourceAsStream("Day07.txt"))) {
            List<Integer> positions = Arrays.stream(scanner.nextLine().split(",")).map(Integer::valueOf).toList();
            calculateMinFuel(positions);
        }
    }

    private static void calculateMinFuel(List<Integer> positions) {
        int max = positions.stream().max(Integer::compareTo).orElseThrow();
        int min = positions.stream().min(Integer::compareTo).orElseThrow();

        IntStream.range(min, max + 1)
                .map(finalPosition -> positions.stream().mapToInt(pos -> Math.abs(pos - finalPosition)).sum())
                .min()
                .ifPresent(minFuel -> System.out.println("Part 1: " + minFuel));

        IntStream.range(min, max + 1)
                .map(finalPosition -> positions.stream()
                        .mapToInt(pos -> IntStream.range(1, Math.abs(pos - finalPosition) + 1).sum())
                        .sum())
                .min()
                .ifPresent(minFuel -> System.out.println("Part 2: " + minFuel));
    }

}





