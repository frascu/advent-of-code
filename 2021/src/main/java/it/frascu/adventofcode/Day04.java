package it.frascu.adventofcode;

import org.javatuples.Pair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 {

    public static void main(String[] args) {
        InputStream is = Day04.class.getClassLoader().getResourceAsStream("Day04.txt");

        Integer[] randomNumbers;
        List<Integer[][]> bingoBoards = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            randomNumbers = Arrays.stream(scanner.nextLine().split(",")).map(Integer::valueOf).toArray(Integer[]::new);
            scanner.nextLine();
            Integer[][] board = new Integer[5][];
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    bingoBoards.add(board);
                    row = 0;
                    board = new Integer[5][];
                } else {
                    board[row] = Arrays.stream(line.split(" +")).map(Integer::valueOf).toArray(Integer[]::new);
                    row++;
                }
            }
            bingoBoards.add(board);
        }
        partOne(randomNumbers, bingoBoards);
        partTwo(randomNumbers, bingoBoards);
    }

    private static void partOne(Integer[] randomNumbers, List<Integer[][]> bingoBoards) {
        List<int[][]> results = bingoBoards.stream().map(b -> new int[b.length][b.length]).toList();
        for (Integer randomNumber : randomNumbers) {
            List<Pair<Integer[][], int[][]>> winners = getWinners(bingoBoards, results, randomNumber);
            if (!winners.isEmpty()) {
                System.out.println("Part 1: " + sumUnmarkedValues(winners.get(0).getValue1(), winners.get(0).getValue0()) * randomNumber);
                return;
            }
        }
    }

    private static void partTwo(Integer[] randomNumbers, List<Integer[][]> bingoBoards) {
        List<int[][]> results = bingoBoards.stream().map(b -> new int[b.length][b.length]).collect(Collectors.toList());;
        for (Integer randomNumber : randomNumbers) {
            List<Pair<Integer[][], int[][]>> winners = getWinners(bingoBoards, results, randomNumber);
            if (!winners.isEmpty()) {
                winners.forEach(winner -> {
                    bingoBoards.remove(winner.getValue0());
                    results.remove(winner.getValue1());
                });
                if (bingoBoards.isEmpty()) {
                    int[][] lastResult = winners.get(winners.size() - 1).getValue1();
                    Integer[][] lastBingBoard = winners.get(winners.size() - 1).getValue0();
                    int lastRandomNumber = randomNumber;
                    System.out.println("Part 2: " + sumUnmarkedValues(lastResult, lastBingBoard) * lastRandomNumber);
                    break;
                }
            }
        }
    }

    private static List<Pair<Integer[][], int[][]>> getWinners(List<Integer[][]> bingoBoards, List<int[][]> results, Integer randomNumber) {
        List<Pair<Integer[][], int[][]>> winners = new ArrayList<>();
        for (int k = 0; k < bingoBoards.size(); k++) {
            int[][] result = results.get(k);
            int positionI = 0;
            int positionJ = 0;
            boolean found = false;
            Integer[][] bingoBoard = bingoBoards.get(k);
            for (int i = 0; i < bingoBoard.length && !found; i++) {
                for (int j = 0; j < bingoBoard.length && !found; j++) {
                    if (bingoBoard[i][j].equals(randomNumber)) {
                        result[i][j] = 1;
                        found = true;
                        positionI = i;
                        positionJ = j;
                    }
                }
            }

            //check if this board won
            if (found) {
                int finalPositionJ = positionJ;
                int finalPositionI = positionI;
                int sumRow = IntStream.range(0, result.length).map(i -> result[i][finalPositionJ]).sum();
                int sumColumn = IntStream.range(0, result.length).map(j -> result[finalPositionI][j]).sum();
                if (sumRow == result.length || sumColumn == result.length) {
                    winners.add(new Pair<>(bingoBoard, result));
                }
            }

        }
        return winners;
    }

    private static int sumUnmarkedValues(int[][] result, Integer[][] bingoBoard) {
        int lastResult = 0;
        for (int i = 0; i < bingoBoard.length; i++) {
            for (int j = 0; j < bingoBoard.length; j++) {
                if (result[i][j] == 0) {
                    lastResult+= bingoBoard[i][j];
                }
            }
        }
        return lastResult;
    }

}

