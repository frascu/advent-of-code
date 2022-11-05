package it.frascu.adaventcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/1
 *
 * --- Day 1: Report Repair ---
 * After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a tropical island.
 * Surely, Christmas will go on without you.
 *
 * The tropical island has its own currency and is entirely cash-only.
 * The gold coins used there have a little picture of a starfish; the locals just call them stars.
 * one of the currency exchanges seem to have heard of them,
 * but somehow, you'll need to find fifty of these coins by the time you arrive so you can pay the deposit on your room.
 *
 * To save your vacation, you need to get all fifty stars by December 25th.
 *
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar;
 * the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 */
public class Day01 {

    private static final String INPUT_FILE_PATH = "input/day01.txt";

    public static void main(String[] args) {
        System.out.println("------ day 01 ------");
        partOne();
        partTwo();
    }

    /**
     * --- Part One ---
     *
     * Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input);
     * apparently, something isn't quite adding up.
     *
     * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
     *
     * For example, suppose your expense report contained the following:
     *
     * 1721
     * 979
     * 366
     * 299
     * 675
     * 1456
     *
     * In this list, the two entries that sum to 2020 are 1721 and 299. M
     * multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.
     *
     * Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you multiply them together?
     */
    private static void partOne() {
        System.out.println("----- part one -----");
        try (Scanner scanner = new Scanner(new File(INPUT_FILE_PATH))) {
            Set<Integer> entries = new HashSet<>();
            while (scanner.hasNext()) {
                int number = scanner.nextInt();
                if (entries.contains(number)) {
                    System.out.println((2020 - number) * number);
                    break;
                } else {
                    entries.add(2020 - number);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * --- Part Two ---
     * The Elves in accounting are thankful for your help;
     * one of them even offers you a starfish coin they had left over from a past vacation.
     * They offer you a second one if you can find three numbers in your expense report that meet the same criteria.
     *
     * Using the above example again, the three entries that sum to 2020 are 979, 366, and 675.
     * Multiplying them together produces the answer, 241861950.
     *
     * In your expense report, what is the product of the three entries that sum to 2020?
     */
    private static void partTwo() {
        System.out.println("----- part two -----");
        try (Scanner scanner = new Scanner(new File(INPUT_FILE_PATH))) {
            List<Integer> entries = new ArrayList<>();
            while (scanner.hasNext()) {
                entries.add(scanner.nextInt());
            }
            for (int i = 0; i < entries.size(); i++) {
                for (int j = 0; j < entries.size(); j++) {
                    for (int k = 0; k < entries.size(); k++) {
                        if (i != j && i != k && j != k &&
                                entries.get(i) + entries.get(j) + entries.get(k) == 2020) {
                            System.out.println(entries.get(i) * entries.get(j) * entries.get(k));
                            return;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
