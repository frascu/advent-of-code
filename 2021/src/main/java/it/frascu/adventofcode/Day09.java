package it.frascu.adventofcode;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class Day09 {

    public static void main(String[] args) {
        List<List<Integer>> rows = new ArrayList<>();
        try (Scanner scanner = new Scanner(Day09.class.getClassLoader().getResourceAsStream("Day09.txt"))) {
            while (scanner.hasNextLine()) {
                rows.add(scanner.nextLine().trim().chars().map(n -> n - '0').boxed().toList());
            }
        }

        // part 1
        int lengthColumns = rows.get(0).size();
        List<Integer> numbers = new ArrayList<>();
        Set<Pair<Integer, Integer>> positions = new HashSet<>();
        IntStream.range(0, rows.size()).forEach(i ->
                //columns
                IntStream.range(0, lengthColumns).forEach(j -> {
                    List<Integer> neighbours = new ArrayList<>();
                    if (i > 0) neighbours.add(rows.get(i - 1).get(j)); // up
                    if (j > 0) neighbours.add(rows.get(i).get(j - 1)); // left
                    if (j < lengthColumns - 1) neighbours.add(rows.get(i).get(j + 1)); // right
                    if (i < rows.size() - 1) neighbours.add(rows.get(i + 1).get(j)); // down

                    int center = rows.get(i).get(j);
                    if (center < Collections.min(neighbours)) {
                        numbers.add(center + 1);
                        positions.add(new Pair<>(i, j));
                    }
                })
        );
        numbers.stream().reduce(Integer::sum).ifPresent(sum -> System.out.printf("Part 1: %s%n", sum));


        // part 2
        List<Integer> sizeBasins = new ArrayList<>();
        positions.forEach(p -> {
            LinkedList<Pair<Integer, Integer>> basinScanner = new LinkedList<>();
            Set<Pair<Integer, Integer>> basin = new HashSet<>();
            basinScanner.add(p);

            while (!basinScanner.isEmpty()) {
                Pair<Integer, Integer> posToScan = basinScanner.pop();
                basin.add(posToScan);

                // select neighbours
                int i = posToScan.getValue0();
                int j = posToScan.getValue1();
                Set<Pair<Integer, Integer>> neighbours = new HashSet<>();
                if (i > 0) neighbours.add(new Pair<>(i - 1, j)); // up
                if (j > 0) neighbours.add(new Pair<>(i, j - 1)); // left
                if (j < lengthColumns - 1) neighbours.add(new Pair<>(i, j + 1)); // right
                if (i < rows.size() - 1) neighbours.add(new Pair<>(i + 1, j)); //down
                neighbours.removeIf(n -> rows.get(n.getValue0()).get(n.getValue1()) == 9 || basinScanner.contains(n) || basin.contains(n));

                basinScanner.addAll(neighbours);
            }
            sizeBasins.add(basin.size());
        });
        sizeBasins.sort((i1, i2) -> Integer.compare(i2, i1));
        System.out.printf("Part 2: %s%n", sizeBasins.get(0) * sizeBasins.get(1) * sizeBasins.get(2));
    }


}






