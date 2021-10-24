package com.efimchick.ifmo.streams.countwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 08 - Java Lambdas & Streams - Vladimir_Vasyukov
 * Class for working with words
 */

public class Words {
    private static final String TEXT_FILE_DELIMITER = "\\s";
    private static final String REGEX_DELIMITER =
        "([\\u0000-\\u0040\\u005B-\\u0060\\u007B-\\u00BF\\u02B0-\\u036F\\u00D7\\u00F7\\u2000-\\u2BFF])+";
    private static final String EMPTY_SYM = " ";
    private static final int MIN_WORD_LENGTH = 4;
    private static final int MIN_WORD_COUNT = 10;

    public String countWords(List<String> lines) {
        Map<String, WordStore> map = new HashMap<>();
        String[] strings = lines.toString().replaceAll(REGEX_DELIMITER, EMPTY_SYM).split(TEXT_FILE_DELIMITER);
        List<String> lowerCaseList = Arrays.stream(strings)
            .map(String::toLowerCase)
            .collect(Collectors.toList());
        lowerCaseList
            .forEach(s -> map.put(s, map.containsKey(s)
                ? map.get(s).incrementCount() : new WordStore(s)));
        List<WordStore> wordStoreList = new ArrayList<>(map.values());
        Collections.sort(wordStoreList);
        return applyFilters(wordStoreList);
    }

    public String applyFilters(List<WordStore> list) {
        return list.stream()
            .filter(o -> o.getCount() >= MIN_WORD_COUNT)
            .filter(o -> o.getValue().length() >= MIN_WORD_LENGTH)
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
    }
}
