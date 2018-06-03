package com.translatorapi.translatorapi.dao;

public interface UserStoryService {
    boolean addTranslation(String fromLanguage, String fromWord, String toLanguage, String toWord);

    String getTranslation(String fromLanguage, String wordToTranslate, String toLanguage);
}
