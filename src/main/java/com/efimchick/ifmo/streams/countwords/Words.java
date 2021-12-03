package com.efimchick.ifmo.streams.countwords;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Words {
    private static final Pattern REGEX_PATTERN = Pattern.compile("[^\\p{Alpha}]", Pattern.UNICODE_CHARACTER_CLASS);
    private static final String TEXT_FILE_DELIMITER = "\\s";
    private static final int MIN_WORD_COUNT = 10;
    private static final int MIN_LENGTH_WORD = 4;
    private static final String SPACE = " ";
    private static final String DASH = " - ";
    private static final String NEW_LINE = "\n";

    public String countWords(List<String> lines) {
        Map<String, Integer> countWordsInMap = countWordsInMap(getWordsFromText(lines));
        Map<String, Integer> wordsMapForSort = filterWordsByMinFrequency(countWordsInMap);
        Set<Map.Entry<String, Integer>> sortedWordsByFrequencySet = sortHashMap(wordsMapForSort);
        return createResultString(sortedWordsByFrequencySet);
    }

    private List<String> getWordsFromText(List<String> text) {
        String lowerCaseText = text
            .stream()
            .map(String::toLowerCase).collect(Collectors.joining(SPACE));
        String[] textAsArray = REGEX_PATTERN.matcher(lowerCaseText).replaceAll(SPACE).split(TEXT_FILE_DELIMITER);
        return Arrays
            .stream(textAsArray)
            .collect(Collectors.toList());
    }

    private Map<String, Integer> countWordsInMap(List<String> wordsFromText) {
        return wordsFromText
            .stream()
            .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));
    }

    private Map<String, Integer> filterWordsByMinFrequency(Map<String, Integer> wordsMap) {
        return wordsMap
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().length() >= MIN_LENGTH_WORD)
            .filter(entry -> entry.getValue() >= MIN_WORD_COUNT)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Set<Map.Entry<String, Integer>> sortHashMap(Map<String, Integer> unsortedMap) {
        return unsortedMap
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String createResultString(Set<Map.Entry<String, Integer>> sortedWordsByFrequencySet) {
        return sortedWordsByFrequencySet
            .stream()
            .map(entry -> entry.getKey() + DASH + entry
            .getValue()).collect(Collectors.joining(NEW_LINE));
    }
}
