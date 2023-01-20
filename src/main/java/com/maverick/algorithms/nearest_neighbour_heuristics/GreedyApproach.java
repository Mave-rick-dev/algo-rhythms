package com.maverick.algorithms.nearest_neighbour_heuristics;

import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Om Narayan Singh
 * @version 1.0
 * @project algorithms
 * @package com.maverick.algorithms.nearest_neighbour_heuristics
 * @since 1/16/23
 **/

@Slf4j
public class GreedyApproach {
    public static void main(String[] args) {
        var costMatrix = new int[][]{
                      //  0   1   2   3
                new int[]{0, 10, 15, 20},  // 0
                new int[]{5, 0, 25, 10},   // 1
                new int[]{15, 30, 0, 5},   // 2
                new int[]{16, 10, 20, 0},  // 3
        };

        var startingNode = 0;
        var firstNode = startingNode;
        var visitedRouteNode = new ArrayList<Integer>();
        var visitedRoutesCost = new ArrayList<Integer>();

        if (startingNode == 0) {
            // FIRST ITERATION
            var selectedRow = costMatrix[startingNode];

            var firstRelativeCost = new HashMap<Integer, Integer>();
            /*
             * - Stores visited route map: nodeID and cost
             * - Insertion order of the visited route is required as sequence can differ the cost of travel
             */
            var visitedRoute = new LinkedHashMap<Integer, Integer>();

            // starting node's cost is set as 0, as only the key is required i.e. only route identifier is needed
            visitedRouteNode.add(startingNode);
            visitedRoutesCost.add(0);
//            printVisitedRouteAndCost(visitedRouteNode, visitedRoutesCost);

            visitedRoute.put(startingNode, 0);
            for (int i = 0; i < selectedRow.length; i++) {
                log.info("Select row cost: {}", selectedRow[i]);
                var cost = selectedRow[i];
                if (cost != 0)
                    firstRelativeCost.put(i, cost);
            }
            log.info("FIRST relative costs: {}", firstRelativeCost);

            // get greedy cost and route
            var greedyRouteMap = firstRelativeCost.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), Collections.min(firstRelativeCost.values())))
                    .findFirst()
                    .orElseThrow();

            log.info("Greedy route {} and cost {}", greedyRouteMap.getKey(), greedyRouteMap.getValue());

            visitedRouteNode.add(greedyRouteMap.getKey());
            visitedRoutesCost.add(greedyRouteMap.getValue());
//            printVisitedRouteAndCost(visitedRouteNode, visitedRoutesCost);
            visitedRoute.put(greedyRouteMap.getKey(), greedyRouteMap.getValue());
            log.info("Visited route(s) till FIRST: {}", visitedRoute);

            // SECOND ITERATION
            var secondRelativeCost = new HashMap<Integer, Integer>();
            startingNode = greedyRouteMap.getKey();
            selectedRow = costMatrix[startingNode];

            for (int i = 0; i < selectedRow.length; i++) {
                log.info("Second Select row cost: {}", selectedRow[i]);
                var cost = selectedRow[i];
                if (cost != 0)
                    secondRelativeCost.put(i, cost);
            }
            log.info("SECOND Relative costs: {}", secondRelativeCost);

            /*
             * initialize a list with nodes to exclude while calculating minimum cost
             */
            var nodesToExclude = secondRelativeCost.keySet()
                    .stream()
                    .filter(visitedRouteNode::contains)
                    .collect(Collectors.toList());
            log.info("Nodes to exclude to cost comparison: {}", nodesToExclude);

            /*
             * removes the nodes in @var nodesToExclude from @var secondRelativeCost
             */
            nodesToExclude.forEach(secondRelativeCost::remove);
            log.info("Updated SECOND relative costs: {}", secondRelativeCost);

            /*
             * Calculates the greedy route
             * TODO: calculate greedy route for multiple routes with same min value
             *  - for now it takes the very first route with min value
             */
            var secondGreedyRoute = secondRelativeCost.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), Collections.min(secondRelativeCost.values())))
                    .findFirst()
                    .orElseThrow();

            log.info("Minimum cost: {}", Collections.min(secondRelativeCost.values()));
            log.info("Second route: {} cost: {}", secondGreedyRoute.getKey(), secondGreedyRoute.getValue());

            visitedRouteNode.add(secondGreedyRoute.getKey());
            visitedRoutesCost.add(secondGreedyRoute.getValue());
//            printVisitedRouteAndCost(visitedRouteNode, visitedRoutesCost);
            visitedRoute.put(secondGreedyRoute.getKey(), secondGreedyRoute.getValue());
            log.info("Second visited route: {}", secondGreedyRoute);
            log.info("Visited route(s) till SECOND: {}", visitedRoute);

            // THIRD ITERATION
            startingNode = secondGreedyRoute.getKey();
            var thirdRelativeCost = new HashMap<Integer, Integer>();
            selectedRow = costMatrix[startingNode];
            log.info("hero : {}",selectedRow);

            for (int i = 0; i < selectedRow.length; i++) {
                log.info("Third Select row cost: {}", selectedRow[i]);
                var cost = selectedRow[i];
                if (cost != 0)
                    thirdRelativeCost.put(i, cost);
            }
            log.info("THIRD Relative costs: {}", thirdRelativeCost);


            nodesToExclude = thirdRelativeCost.keySet()
                    .stream()
                    .filter(visitedRouteNode::contains)
                    .collect(Collectors.toList());
            log.info("Nodes to exclude to cost comparison: {}", nodesToExclude);



            /*
             * Calculates the greedy route
             * TODO: calculate greedy route for multiple routes with same min value
             *  - for now it takes the very first route with min value
             */
            Map.Entry<Integer, Integer> thirdGreedyRouteMap;
            if (thirdRelativeCost.size() == 1) {
                thirdGreedyRouteMap = thirdRelativeCost.entrySet()
                        .stream()
                        .findFirst()
                        .orElseThrow();
            } else {
                thirdGreedyRouteMap = thirdRelativeCost.entrySet()
                        .stream()
                        .filter(entry -> Objects.equals(entry.getValue(), Collections.min(secondRelativeCost.values())))
                        .findFirst()
                        .orElseThrow();
            }
            log.info("Third visited route: {}", thirdGreedyRouteMap);

            visitedRouteNode.add(secondGreedyRoute.getKey());
            visitedRoutesCost.add(secondGreedyRoute.getValue());
//            printVisitedRouteAndCost(visitedRouteNode, visitedRoutesCost);

            visitedRoute.put(thirdGreedyRouteMap.getKey(), thirdGreedyRouteMap.getValue());
            log.info("Visited route(s) till THIRD: {}", visitedRoute);

            // THIRD ITERATION
            startingNode = thirdGreedyRouteMap.getKey();
            var finalRelativeCost = new HashMap<Integer, Integer>();
            selectedRow = costMatrix[startingNode];

            var cost = selectedRow[firstNode];
            finalRelativeCost.put(firstNode, cost);

            visitedRouteNode.add(firstNode);
            visitedRoutesCost.add(cost);
//            printVisitedRouteAndCost(visitedRouteNode, visitedRoutesCost);

            visitedRoute.put(firstNode, cost);
            log.info("FINAL Relative costs: {}", finalRelativeCost);
            log.info("Visited route(s) till FINAL: {}", visitedRouteNode);
            log.info("Visited cost(s) till FINAL:  {}", visitedRoutesCost);
            log.info("Visited route(s) till FINAL: {}", visitedRoute);

            /*
                TODO: explored results:
                    1. visited routes till final: {0=15, 1=10, 3=10, 2=20}: LinkedHashMap
                        - updates the key:value for same route i.e starting node repeats at final travel
                    2. visited routes till final: {0=[15, 10], 1=10, 3=10, 2=20}: MultiValueMap(google)

                    But expected: {0=15, 1=10, 3=10, 2=20, 0=16}: ??
             */
//            log.info("Visited route(s) till FINAL: {}", visitedRoute);
        }
    }
}
