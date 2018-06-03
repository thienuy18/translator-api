package com.translatorapi.translatorapi.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Entry {
    private String language;
    private String word;
    private Set<Entry> translations;

    public Entry () {
        // empty to allow bean access
    }

    public Entry (String language, String word) {
        this.language = language;
        this.word = word;
        this.translations = new HashSet<>();
    }

    public void addNewTranslation(Entry entry) {
        this.translations.add(entry);
    }

    public String getLanguage() {
        return language;
    }

    public String getWord() {
        return word;
    }

    public Set<Entry> getTranslations() {
        return translations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(word, entry.word);
    }

    @Override
    public int hashCode() {

        return Objects.hash(word);
    }
}
