package it.frascu.adventofcode;

import java.util.*;
import java.util.stream.Collectors;

public class Day08 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(Day08.class.getClassLoader().getResourceAsStream("Day08.txt"))) {


            long totalNumber = 0;
            long totalCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split("\\|");
                List<String> uniqueSignals = Arrays.asList(splitLine[0].trim().split(" +"));
                List<String> digitsOutput = Arrays.asList(splitLine[1].trim().split(" +"));

                // part 1
                List<Integer> lengthValue = Arrays.asList(2, 3, 4, 7);
                List<String> uniqueSignalsFiltered = uniqueSignals.stream().filter(s -> lengthValue.contains(s.length())).toList();
                long count = digitsOutput.stream().filter(d ->
                        uniqueSignalsFiltered.stream().anyMatch(l ->
                                l.length() == d.length() && d.chars().noneMatch(c -> l.indexOf(c) == -1)
                        )
                ).count();
                totalCount += count;

                // part 2
                Set<String> signalsSet = new HashSet<>(uniqueSignals);
                Map<Integer, String> firstDigits = new HashMap<>();
                signalsSet.forEach(s -> {
                    switch (s.length()) {
                        case 7 -> firstDigits.put(8, s);
                        case 2 -> firstDigits.put(1, s);
                        case 4 -> firstDigits.put(4, s);
                        case 3 -> firstDigits.put(7, s);
                    }
                });
                signalsSet.removeAll(firstDigits.values());

                Map<String, Integer> map = signalsSet.stream().collect(Collectors.toMap(a -> a,
                        signal -> switch (signal.length()) {
                            case 6 -> {
                                // 0, 6, 9
                                if (firstDigits.get(4).chars().allMatch(c -> signal.indexOf(c) != -1)) {
                                    yield 9;
                                } else if (firstDigits.get(1).chars().allMatch(c -> signal.indexOf(c) != -1)) {
                                    yield 0;
                                } else {
                                    yield 6;
                                }
                            }
                            case 5 -> {
                                // 5, 2 , 3
                                if (firstDigits.get(7).chars().allMatch(c -> signal.indexOf(c) != -1)) {
                                    yield 3;
                                } else if (firstDigits.get(4).chars().filter(c -> signal.indexOf(c) != -1).count() == 3) {
                                    yield 5;
                                } else {
                                    yield 2;
                                }
                            }
                            default -> -1;
                        }
                ));
                firstDigits.forEach((k, v) -> map.put(v, k));

                List<String> numbers = digitsOutput.stream().map(d ->
                        uniqueSignals.stream()
                                .filter(l -> l.length() == d.length() && d.chars().noneMatch(c -> l.indexOf(c) == -1))
                                .findFirst()
                                .map(l -> map.get(l).toString())
                                .orElseThrow()
                ).toList();
                totalNumber += Integer.parseInt(String.join("", numbers));
            }
            System.out.println("Part 1: " + totalCount);
            System.out.println("Part 2: " + totalNumber);

        }
    }


}





