package com.translatorapi.translatorapi.test;

import com.translatorapi.translatorapi.dao.UserStory;
import com.translatorapi.translatorapi.model.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;


public class UserStoryTest {

    private UserStory userStory;
    private String languageWord1, valueWord1;
    private String languageWord2, valueWord2;
    private String languageWord3, valueWord3;
    private String languageWord4, valueWord4;
    private Entry entryWord1, entryWord2, entryWord3, entryWord4;

    @Before
    public void setUp () {
        languageWord1 = "DE";
        valueWord1 = "Hund";
        languageWord2 = "EN";
        valueWord2 = "dog";
        languageWord3 = "ES";
        valueWord3 = "gato";
        languageWord4 = "FR";
        valueWord4 = "chat";
        userStory = new UserStory();
        entryWord1 = new Entry(languageWord1, valueWord1); // DE
        entryWord2 = new Entry(languageWord2, valueWord2); // EN
        entryWord3 = new Entry(languageWord3, valueWord3); // ES
        entryWord4 = new Entry(languageWord4, valueWord4); // FR
    }

    @Test
    public void testAddOneValidTranslation() {
        boolean storedSuccessfully = false;
        assertFalse(containerContainsEntry(entryWord1));
        assertFalse(containerContainsEntry(entryWord2));

        try {
            storedSuccessfully = userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);
        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }

        assertTrue(storedSuccessfully);
        assertTrue(containerContainsEntry(entryWord1));
        assertTrue(containerContainsEntry(entryWord2));
    }

    @Test
    public void testAddOneValidTranslationExistsTranslationRelation() {
        boolean storedSuccessfully = false;

        assertFalse(containerContainsEntry(entryWord1));
        assertFalse(containerContainsEntry(entryWord2));

        try {
            storedSuccessfully = userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);
        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }

        assertTrue(storedSuccessfully);
        assertTrue(containerContainsEntry(entryWord1));
        assertTrue(containerContainsEntry(entryWord2));

        assertTrue(entryHaveTranslation(entryWord1, entryWord2));
        assertTrue(entryHaveTranslation(entryWord2, entryWord1));
    }

    @Test
    public void testAddOneInvalidEmptyStringTranslation() {
        try {
            userStory.addTranslation("", "", languageWord3, valueWord2);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testAddOneInvalidNullTranslation() {
        try {
            userStory.addTranslation(languageWord1, valueWord1, null, valueWord2);
            fail("Exception expected as one argument is null");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testRetrieveTranslation() {
        boolean storedSuccessfully;
        String resultWord;

        assertFalse(containerContainsEntry(entryWord1));
        assertFalse(containerContainsEntry(entryWord2));

        try {
            storedSuccessfully = userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);

            assertTrue(storedSuccessfully);
            assertTrue(containerContainsEntry(entryWord1));
            assertTrue(containerContainsEntry(entryWord2));

            resultWord = userStory.getTranslation(languageWord1, valueWord1, languageWord2);

            assertEquals(resultWord, valueWord2);
        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void testRetrieveTranslationReversed() {
        boolean storedSuccessfully;
        String resultWord;

        assertFalse(containerContainsEntry(entryWord1));
        assertFalse(containerContainsEntry(entryWord2));

        try {
            storedSuccessfully = userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);

            assertTrue(storedSuccessfully);
            assertTrue(containerContainsEntry(entryWord1));
            assertTrue(containerContainsEntry(entryWord2));

            resultWord = userStory.getTranslation(languageWord2, valueWord2, languageWord1);

            assertEquals(resultWord, valueWord1);
        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void testRetrieveInterchangeableTranslation() {
        String resultWord1;
        String resultWord2;

        assertFalse(containerContainsEntry(entryWord1));
        assertFalse(containerContainsEntry(entryWord2));
        assertFalse(containerContainsEntry(entryWord3));
        assertFalse(containerContainsEntry(entryWord4));

        try {
            userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);
            userStory.addTranslation(languageWord2, valueWord2, languageWord3, valueWord3);
            userStory.addTranslation(languageWord3, valueWord3, languageWord4, valueWord4);

            assertTrue(containerContainsEntry(entryWord1));
            assertTrue(containerContainsEntry(entryWord2));
            assertTrue(containerContainsEntry(entryWord3));
            assertTrue(containerContainsEntry(entryWord4));

            resultWord1 = userStory.getTranslation(languageWord1, valueWord1, languageWord3);
            resultWord2 = userStory.getTranslation(languageWord1, valueWord1, languageWord4);

            assertEquals(resultWord1, valueWord3);
            assertEquals(resultWord2, valueWord4);
        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void testTranslationNotExist() {
        String resultWord;
        assertFalse(containerContainsEntry(entryWord1));
        assertFalse(containerContainsEntry(entryWord2));

        try {
            userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);

            assertTrue(containerContainsEntry(entryWord1));
            assertTrue(containerContainsEntry(entryWord2));

            resultWord = userStory.getTranslation(languageWord1, valueWord1, languageWord3);

            assertNull(resultWord);

        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    public void testTranslationInvalidInput() {
        try {
            userStory.addTranslation(languageWord1, valueWord1, languageWord2, valueWord2);

            assertTrue(containerContainsEntry(entryWord1));
            assertTrue(containerContainsEntry(entryWord2));

            String resultWord = userStory.getTranslation(languageWord1, null, languageWord3);
            fail("Exception expected as one argument is null");
        } catch (IllegalArgumentException e) {

        }
    }

    private boolean containerContainsEntry(Entry entry) {
        Map<Entry, Entry> words = userStory.getWordContainers().get(entry.getLanguage());
        boolean containsEntry = false;

        if (words != null) {
            Entry lookUpEntry = words.get(entry);
            if (lookUpEntry != null) {
                containsEntry = true;
            }
        }

        return containsEntry;
    }

    private boolean entryHaveTranslation(Entry fromEntry, Entry toEntry) {
        Map<Entry, Entry> words = userStory.getWordContainers().get(fromEntry.getLanguage());
        boolean containsEntry = false;

        if (words != null) {
            Entry lookUpEntry = words.get(fromEntry);
            if (lookUpEntry != null && lookUpEntry.getTranslations().contains(toEntry)) {
                containsEntry = true;
            }
        }

        return containsEntry;
    }
}
