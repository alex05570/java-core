package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 4, 6, 78, 1, 1, 2, 3, 4};

        System.out.println(heatMap(array));

        String[] words = {"a", "b", "a", "c", "b", "a"};
        System.out.println(heatMap(words));
    }

    private static <T> Map<T, Long> heatMap(T[] array) {
        return Arrays.stream(array)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }
}