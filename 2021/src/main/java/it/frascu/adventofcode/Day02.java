package it.frascu.adventofcode;

import java.io.InputStream;
import java.util.Scanner;

public class Day02 {

    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    private static void partOne() {
        InputStream is = Day02.class.getClassLoader().getResourceAsStream("Day02.txt");
        try (Scanner scanner = new Scanner(is)) {
            int horizontal = 0;
            int depth = 0;
            while (scanner.hasNext()) {
                String command = scanner.next();
                int units = scanner.nextInt();
                switch (command) {
                    case "forward" -> horizontal += units;
                    case "down" -> depth += units;
                    case "up" -> depth -= units;
                }
            }
            System.out.println("Part 1: " + (horizontal * depth));
        }
    }

    private static void partTwo() {
        InputStream is = Day02.class.getClassLoader().getResourceAsStream("Day02.txt");
        try (Scanner scanner = new Scanner(is)) {
            int horizontal = 0;
            int depth = 0;
            int aim = 0;
            while (scanner.hasNext()) {
                String command = scanner.next();
                int units = scanner.nextInt();
                switch (command) {
                    case "forward" -> {
                        horizontal += units;
                        depth += aim * units;
                    }
                    case "down" -> aim += units;
                    case "up" -> aim -= units;
                }
            }
            System.out.println("Part 2: " + (horizontal * depth));
        }
    }
}

