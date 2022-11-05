package it.frascu.adventofcode;

import java.util.*;
import java.util.function.BiPredicate;

public class Day12 {

    public static void main(String[] args) {

        Map<String, Set<String>> edges = new HashMap<>();
        try (Scanner scanner = new Scanner(Day12.class.getClassLoader().getResourceAsStream("Day12.txt"))) {
            while (scanner.hasNextLine()) {
                String split[] = scanner.nextLine().trim().split("-");
                String node1 = split[0];
                String node2 = split[1];
                if (edges.containsKey(node1)) {
                    edges.get(node1).add(node2);
                } else {
                    edges.put(node1, new HashSet<>(List.of(node2)));
                }
                if (!"start".equals(node1)) {
                    if (edges.containsKey(node2)) {
                        edges.get(node2).add(node1);
                    } else {
                        edges.put(node2, new HashSet<>(List.of(node1)));
                    }
                }
            }
        }

        Set<String> solutions = new HashSet<>();
        solutions = new HashSet<>();
        System.out.println("Part 2: " +
                getNumberOfPaths("start", edges, new ArrayList<>(), false, solutions)//(p, n) -> p.stream().filter(np -> np.equals(n)).count() < 2)
        );
        System.out.println(solutions.size());
    }

    private static int getNumberOfPaths(String nextNode, Map<String, Set<String>> edges, List<String> path, //BiPredicate<List<String>, String> canBeAddedSmallCave,
                                        boolean added2times, Set<String> solutions) {
        if (nextNode.equals("end")) {
            path.add("end");
            solutions.add(String.join(",", path));
            return 1;
        }

        BiPredicate<List<String>, String> canBeAddedSmallCave;
        if (added2times) {
            canBeAddedSmallCave = (p, n) -> !p.contains(n);
        } else {
            canBeAddedSmallCave = (p, n) -> Collections.frequency(p, n) < 2;
        }
        if (canBeAdded(path, nextNode, canBeAddedSmallCave)) {
            path.add(nextNode);
            int sum = 0;
            for (String n : edges.get(nextNode)) {
                if (!added2times) {
                    if (Character.isUpperCase(nextNode.charAt(0))) {
                        sum += getNumberOfPaths(n, edges, new ArrayList<>(path), false, solutions);
                    } else {
                        int frequency = Collections.frequency(path, nextNode);
                        if (frequency == 2) {
                            sum += getNumberOfPaths(n, edges, new ArrayList<>(path), true, solutions);
                        } else if (frequency == 1) {
                            sum += getNumberOfPaths(n, edges, new ArrayList<>(path), false, solutions);
                            if (!nextNode.equals("start")) {
                                sum += getNumberOfPaths(n, edges, new ArrayList<>(path), true, solutions);
                            }
                        }
                    }
                } else {
                    sum += getNumberOfPaths(n, edges, new ArrayList<>(path), true, solutions);
                }
            }
            return sum;
        }
        return 0;
    }

    private static boolean canBeAdded(List<String> path, String node, BiPredicate<List<String>, String> canBeAddedSmallCave) {
        return Character.isUpperCase(node.charAt(0)) || (!Character.isUpperCase(node.charAt(0)) && canBeAddedSmallCave.test(path, node));
    }

}





