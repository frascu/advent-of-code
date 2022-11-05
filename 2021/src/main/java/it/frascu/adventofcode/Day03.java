package it.frascu.adventofcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiPredicate;

public class Day03 {

    public static void main(String[] args) throws IOException {
        InputStream is = Day03.class.getClassLoader().getResourceAsStream("Day03.txt");
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        partOne(lines);
        partTwo(lines);
    }

    private static void partOne(List<String> lines) {
        int length = lines.get(0).length();
        int[] result = new int[length];
        for (String line : lines) {
            char[] binaryChars = line.toCharArray();
            for (int i = 0; i < length; i++) {
                result[i] += binaryChars[i] == '0' ? -1 : 1;
            }
        }
        int gammaRate = 0;
        for (int i = 0; i < length; i++) {
            gammaRate += result[i] > 0 ? (Math.pow(2, length - i - 1)) : 0;
        }
        System.out.println("Part 1: " + (int) (gammaRate * (Math.pow(2, length) - 1 - gammaRate)));
    }

    private static void partTwo(List<String> lines) {
        int oxygenGeneratorRating = calculateValue(lines, (one, zero) -> one >= zero);
        int co2ScrubberRating = calculateValue(lines, (one, zero) -> one < zero);
        System.out.println("Part 2: " + oxygenGeneratorRating * co2ScrubberRating);
    }

    private static Integer calculateValue(List<String> lines, BiPredicate<Integer, Integer> predicateTakeOneBit) {
        int length = lines.get(0).length();
        LinkedList<String> linkedList = new LinkedList<>(lines);
        List<String> setZero = new ArrayList<>();
        List<String> setOne = new ArrayList<>();

        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            while (!linkedList.isEmpty()) {
                String line = linkedList.pop();
                char[] binaryChars = line.toCharArray();
                if (binaryChars[i] == '0') {
                    setZero.add(line);
                } else {
                    setOne.add(line);
                }
            }
            if (predicateTakeOneBit.test(setOne.size(), setZero.size())) {
                result[i] = '1';
                linkedList.addAll(setOne);
            } else {
                result[i] = '0';
                linkedList.addAll(setZero);
            }
            if (linkedList.size() == 1) {
                result = linkedList.get(0).toCharArray();
                break;
            }
            setOne = new ArrayList<>();
            setZero = new ArrayList<>();
        }
        return Integer.valueOf(String.valueOf(result), 2);
    }
}

