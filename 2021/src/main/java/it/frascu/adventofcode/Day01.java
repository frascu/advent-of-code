package it.frascu.adventofcode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day01 {

    public static void main(String[] args) {
        InputStream is = Day01.class.getClassLoader().getResourceAsStream("Day01.txt");
        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
        }

        partOne(list);
        partTwo(list);
    }

    private static void partOne(List<Integer> list) {
        int count = 0;
        int previous = Integer.MAX_VALUE;
        for (int currentValue : list) {
            if (currentValue > previous) {
                count++;
            }
            previous = currentValue;
        }
        System.out.println("Part 1: " + count);
    }

    private static void partTwo(List<Integer> list) {
        int count = 0;
        int previous = Integer.MAX_VALUE;
        for (int i = 2; i < list.size(); i++) {
            // calc previous 3 elements
            int currentValue = 0;
            for (int j = 0; j < 3; j++) {
                currentValue += list.get(i - j);
            }
            if (currentValue > previous) {
                count++;
            }
            previous = currentValue;
        }
        System.out.println("Part 2: " + count);
    }
}

