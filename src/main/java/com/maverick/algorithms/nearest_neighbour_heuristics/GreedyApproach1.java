package com.maverick.algorithms.nearest_neighbour_heuristics;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Om Narayan Singh
 * @version 1.0
 * @project algorithms
 * @package com.maverick.algorithms.nearest_neighbour_heuristics
 * @since 1/17/23
 **/
@Slf4j
public class GreedyApproach1 {
    public static void main(String[] args) {
        var inputDistanceMatrix = new int[][]{
                       // 0  1   2   3
                new int[]{0, 10, 15, 20}, // 0
                new int[]{5, 0, 25, 10},  // 1
                new int[]{15, 30, 0, 5},  // 2
                new int[]{16, 10, 20, 0}, // 3
        };
        /*
         * 0 - baneshwor
         * 1 - koteswor
         * 2 - pepsi cola re
         * 3 - maiti ghar
         *
         */
        var startingNode = 3;
        var result = calculateShortestRoute(inputDistanceMatrix, startingNode);
        log.info("Shortest route taken: {}", result);
    }

    static List<Integer> calculateShortestRoute(int[][] costMatrix, int startingNode) {
        var visitedRoutes = new ArrayList<Integer>();
        var relativeCost = new LinkedHashMap<Integer, Integer>();
        var visitedRoutesCost = new ArrayList<Integer>();
        var totalRoutes = costMatrix.length;
        var firstNode = startingNode;
        log.info("Starting and Ending node: {}", firstNode);
        log.info("Total routes to travel: {}", totalRoutes);
        visitedRoutes.add(startingNode);
        log.info("Total visited routes: {}", visitedRoutes);


        for (int i = 0; i < totalRoutes; i++) {
            var nodeSpecificCosts = costMatrix[startingNode];
            log.info("Costs selected: {}", nodeSpecificCosts);

            for (int j = 0; j < nodeSpecificCosts.length; j++) {
                log.info("Select row cost: {}", nodeSpecificCosts[j]);
                var cost = nodeSpecificCosts[j];
                if (cost != 0)
                    relativeCost.put(j, cost);
            }
            log.info("Relative costs: {}", relativeCost);

            /*
             * initialize a list with nodes to exclude while calculating minimum cost
             */
            List<Integer> nodesToExclude;

            nodesToExclude = relativeCost.keySet()
                    .stream()
                    .filter(key -> {
                        if (visitedRoutes.size() != totalRoutes) {
                            return visitedRoutes.contains(key);
                        } else {
                            return !Objects.equals(key, firstNode);
                        }
                    })
                    .collect(Collectors.toList());

            log.info("Nodes to exclude to cost comparison: {}", nodesToExclude);

            /*
             * removes the nodes in @var nodesToExclude from @var secondRelativeCost
             */
            nodesToExclude.forEach(relativeCost::remove);
            log.info("Updated relative costs: {}", relativeCost);

            Map.Entry<Integer, Integer> greedyRouteMap;

            greedyRouteMap = relativeCost.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), Collections.min(relativeCost.values())))
                    .findFirst()
                    .orElse(relativeCost.entrySet().stream().findFirst().orElseThrow());


            visitedRoutes.add(greedyRouteMap.getKey());
            visitedRoutesCost.add(greedyRouteMap.getValue());
            log.info("Greedy Route selected: {}", greedyRouteMap);
            startingNode = greedyRouteMap.getKey();

            log.info("Total visited routes: {}", visitedRoutes);

            relativeCost.clear();
        }

        return visitedRoutes;
    }
}
