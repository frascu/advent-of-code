package it.frascu.adventofcode;

import java.util.*;
import java.util.stream.IntStream;

public class Day06 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(Day06.class.getClassLoader().getResourceAsStream("Day06.txt"))) {
            List<Integer> fishes = Arrays.stream(scanner.nextLine().split(",")).map(Integer::valueOf).toList();
            System.out.println("Part 1: " + getNumberOfFishesAfterDays(fishes, 80));
            System.out.println("Part 2: " + getNumberOfFishesAfterDays(fishes, 256));
        }
    }

    private static long getNumberOfFishesAfterDays(List<Integer> fishes, int days) {
        long[] counter = new long[9];
        fishes.forEach(fish -> counter[fish] += 1);
        IntStream.range(0, days).forEach(i -> {
            long becomeParents = counter[0];
            IntStream.range(1, counter.length).forEach(j -> counter[j - 1] = counter[j]);
            counter[8] = becomeParents;
            counter[6] += becomeParents;
        });
        return Arrays.stream(counter).sum();
    }

}





