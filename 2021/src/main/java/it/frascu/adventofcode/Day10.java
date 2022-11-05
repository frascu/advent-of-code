package it.frascu.adventofcode;

import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(Day10.class.getClassLoader().getResourceAsStream("Day10.txt"))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().trim());
            }
        }

        List<String> uncompletedLines = part1(lines);
        part2(uncompletedLines);
    }

    private static List<String> part1(List<String> lines) {
        Map<Character, Pair<Character, Integer>> closeBracketMap = new HashMap<>();
        closeBracketMap.put(')', new Pair<>('(', 3));
        closeBracketMap.put(']', new Pair<>('[', 57));
        closeBracketMap.put('}', new Pair<>('{', 1197));
        closeBracketMap.put('>', new Pair<>('<', 25137));

        List<String> uncompletedLines = new ArrayList<>();
        lines.stream()
                .map(line ->
                        findIrregularBracket(line, closeBracketMap)
                                .map(c -> closeBracketMap.get(c).getValue1())
                                .or(() -> {
                                    uncompletedLines.add(line);
                                    return Optional.empty();
                                })
                                .orElse(0)
                )
                .reduce(Integer::sum)
                .ifPresent(sum -> System.out.printf("Part 1: %s%n", sum));
        return uncompletedLines;
    }

    private static void part2(List<String> uncompletedLines) {
        List<Long> scores = uncompletedLines.stream().map(line -> {
            LinkedList<Character> stack = new LinkedList<>();
            IntStream.range(0, line.length()).forEach(i -> {
                char bracket = line.charAt(i);
                switch (bracket) {
                    case '{', '(', '[', '<' -> stack.addLast(bracket);
                    case '}', ')', ']', '>' -> stack.removeLast();
                }
            });

            AtomicLong atomicLong = new AtomicLong();
            IntStream.range(0, stack.size()).forEach(i -> {
                char openBracket = stack.removeLast();
                switch (openBracket) {
                    case '(' -> atomicLong.set(atomicLong.get() * 5 + 1);
                    case '[' -> atomicLong.set(atomicLong.get() * 5 + 2);
                    case '{' -> atomicLong.set(atomicLong.get() * 5 + 3);
                    case '<' -> atomicLong.set(atomicLong.get() * 5 + 4);
                }
            });

            return atomicLong.get();
        }).sorted(Long::compareTo).toList();
        System.out.printf("Part 2: %s%n", scores.get(scores.size() / 2));
    }

    private static Optional<Character> findIrregularBracket(String line, Map<Character, Pair<Character, Integer>> infoCloseBracket) {
        LinkedList<Character> stack = new LinkedList<>();
        return IntStream.range(0, line.length())
                .filter(i -> {
                    char bracket = line.charAt(i);
                    switch (bracket) {
                        case '{', '(', '[', '<' -> stack.addLast(bracket);
                        case '}', ')', ']', '>' -> {
                            char lastChar = stack.removeLast();
                            if (lastChar != infoCloseBracket.get(bracket).getValue0()) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .mapToObj(line::charAt)
                .findFirst();
    }


}






