package com.kafkaProcessing.utils;

import java.util.Arrays;
import java.util.Optional;

public class ArrayUtil {
    public static Optional<String> findFirst(String[] values) {
        return Arrays.asList(values)
                .stream()
                .reduce((first, last) -> first);
    }

    public static Optional<String> findLast(String[] values) {
        return Arrays.asList(values)
                .stream()
                .reduce((first, last) -> last);
    }
}
