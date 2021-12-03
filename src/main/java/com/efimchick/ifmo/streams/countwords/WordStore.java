package com.efimchick.ifmo.streams.countwords;

public class WordStore implements Comparable<WordStore> {
    private final String value;
    private Integer count;

    public WordStore(String value) {
        this.value = value;
        this.count = 1;
    }

    public String getValue() {
        return value;
    }

    public Integer getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public int compareTo(WordStore o) {
        int compareCount = o.getCount().compareTo(count);
        return compareCount == 0 ? value.compareTo(o.getValue()) : compareCount;
    }

    @Override
    public String toString() {
        return value + " - " + count;
    }

}
