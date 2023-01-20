package com.maverick.algorithms.nearest_neighbour_heuristics;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import lombok.extern.slf4j.Slf4j;
import java.util.LinkedHashMap;

/**
 * @author user1
 * @version 1.0
 * @project algorithms
 * @package com.maverick.algorithms.nearest_neighbour_heuristics
 * @since 1/16/23
 **/
@Slf4j
public class LinkedHashMapDemo {
    public static void main(String[] args) {
        var linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put("Hello", "world");
        linkedHashMap.put("wait", "apple");
        linkedHashMap.put("what", "banana");
        linkedHashMap.put("k re", "peaple");
        log.info("hero:  {}", linkedHashMap);

        Multimap<Integer, Integer> multimap = TreeMultimap.create();
        multimap.put(1, 2);
        multimap.put(1, 3);
        multimap.put(2, 4);
        multimap.put(4, 6);
        log.info("arko hero:  {}", multimap);

    }
}
