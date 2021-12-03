package com.efimchick.ifmo.streams.countwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 08 - Java Lambdas & Streams - Vladimir_Vasyukov
 */

public class Words {
    private static final String TEXT_FILE_DELIMITER = "\\s";
    private static final String REGEX_DELIMITER =
        "([\\u0000-\\u0040\\u005B-\\u0060\\u007B-\\u00BF\\u02B0-\\u036F\\u00D7\\u00F7\\u2000-\\u2BFF])+";
    private static final String EMPTY_SYM = " ";
    private static final int MIN_WORD_LENGTH = 4;
    private static final int MIN_WORD_COUNT = 10;

    public String countWords(List<String> lines) {
        String[] strings = lines.toString().replaceAll(REGEX_DELIMITER, EMPTY_SYM).split(TEXT_FILE_DELIMITER);
        Map<String, WordStore> map = Arrays.stream(strings)
            .filter(s -> s.length() >= MIN_WORD_LENGTH)
            .map(String::toLowerCase)
            .collect(Collectors.toMap(
                k -> k,
                WordStore::new,
                (wordStore1, wordStore2) -> mergeWordCounts(wordStore1)));
        List<WordStore> wordStoreList = new ArrayList<>(map.values());
        return applyFilters(wordStoreList);
    }

    public WordStore mergeWordCounts(WordStore wordStore1) {
        wordStore1.incrementCount();
        return wordStore1;
    }

    public String applyFilters(Collection<WordStore> list) {
        return list.stream()
            .filter(o -> o.getCount() >= MIN_WORD_COUNT)
            .sorted()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
    }
}
