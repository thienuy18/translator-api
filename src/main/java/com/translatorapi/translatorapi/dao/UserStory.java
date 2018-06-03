package com.translatorapi.translatorapi.dao;

import com.translatorapi.translatorapi.model.Entry;

import java.util.*;

public class UserStory implements UserStoryService {

    private final Map<String, Map<Entry, Entry>> wordContainers;

    public UserStory() {
        wordContainers = new HashMap<>();
    }

    public boolean addTranslation(String fromLanguage, String fromWord, String toLanguage, String toWord) {
        if(!validStrings(fromLanguage, fromWord, toLanguage, toWord)) {
            throw new IllegalArgumentException("Wrong input.");
        }

        Entry fromEntry = new Entry(fromLanguage.toUpperCase(), fromWord);
        Entry toEntry = new Entry(toLanguage.toUpperCase(), toWord);

        return insertTranslation(fromEntry, toEntry) && insertTranslation(toEntry, fromEntry);
    }

    private boolean insertTranslation(Entry fromEntry, Entry toEntry) {
        Map<Entry, Entry> words = wordContainers.get(fromEntry.getLanguage());
        if (words != null) {
            Entry word = words.get(fromEntry);
            if (word != null) {
                word.addNewTranslation(toEntry);
            } else {
                words.put(fromEntry, fromEntry);
                fromEntry.addNewTranslation(toEntry);
            }
        } else {
            wordContainers.put(fromEntry.getLanguage(), new HashMap<>());
            addEntry(fromEntry);
            fromEntry.addNewTranslation(toEntry);
        }

        return true;
    }

    private void addEntry(Entry entry) {
        Map<Entry, Entry> words = wordContainers.get(entry.getLanguage());
        words.put(entry, entry);
    }

    public String getTranslation(String fromLanguage, String wordToTranslate, String toLanguage) {
        if(!validStrings(fromLanguage, wordToTranslate, toLanguage)) {
            throw new IllegalArgumentException("Wrong input.");
        }

        return searchTranslation(new Entry(fromLanguage.toUpperCase(), wordToTranslate), toLanguage.toUpperCase());
    }

    private String searchTranslation(Entry entry, String toLanguage) {
        Map<Entry, Entry> words = wordContainers.get(entry.getLanguage());
        String resultTranslation = null;

        if (words != null) {
            Entry lookUpWordRoot = words.get(entry);
            if (lookUpWordRoot != null) {
                resultTranslation = lookUpForTranslation(lookUpWordRoot, toLanguage);
            }
        }

        return resultTranslation;
    }

    // Breadth First Traversal of an Entry
    private String lookUpForTranslation(Entry entry, String language) {
        Set<Entry> visitedTranslations = new HashSet<>();
        Queue<Entry> leftTranslations = new LinkedList<>();
        String translatedWord = null;

        leftTranslations.add(entry);
        visitedTranslations.add(entry);

        while (!leftTranslations.isEmpty()) {
            entry = leftTranslations.remove();

            if (entry.getLanguage().equals(language)) {
                return entry.getWord();
            }

            for (Entry translationWord : entry.getTranslations()) {
                if (!visitedTranslations.contains(translationWord)) {
                    leftTranslations.add(translationWord);
                    visitedTranslations.add(translationWord);
                }
            }
        }

        return translatedWord;
    }

    private boolean validStrings(String... strings) {
        for (String s : strings) {
            if(s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Map<Entry, Entry>> getWordContainers() {
        return wordContainers;
    }
}
