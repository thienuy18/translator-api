package com.translatorapi.translatorapi.controllers;

import com.translatorapi.translatorapi.dao.UserStory;
import com.translatorapi.translatorapi.dao.UserStoryService;
import com.translatorapi.translatorapi.exceptions.InvalidArguments;
import com.translatorapi.translatorapi.exceptions.NotFoundException;
import com.translatorapi.translatorapi.model.FormHolder;
import com.translatorapi.translatorapi.model.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/translator")
public class UserStoryController {

    private final UserStoryService userStoryDao;

    @Autowired
    public UserStoryController (UserStory userStory) {
        this.userStoryDao = userStory;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Boolean> storeTranslation(@RequestBody FormHolder formHolder) throws InvalidArguments {
        boolean isInserted;

        try {
            isInserted = userStoryDao.addTranslation(formHolder.fromLanguage, formHolder.fromWord, formHolder.toLanguage, formHolder.toWord);
        } catch (RuntimeException e) {
            throw new InvalidArguments();
        }

        return Collections.singletonMap("success", isInserted);
    }


    @RequestMapping(path = "/search/{fromLanguage}/{wordToTranslate}/{toLanguage}", method = RequestMethod.GET)
    public StringResponse getTranslation(@PathVariable("fromLanguage") String fromLanguage,
                                         @PathVariable("wordToTranslate") String wordToTranslate,
                                         @PathVariable("toLanguage") String toLanguage) throws NotFoundException {

        StringResponse translationResult = new StringResponse();

        try {
            translationResult.response = userStoryDao.getTranslation(fromLanguage, wordToTranslate, toLanguage);

            if (translationResult.response == null) {
                throw new NotFoundException();
            }

        } catch (RuntimeException e) {
            throw new NotFoundException();
        }

        return translationResult;
    }


    // Used for forms
    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getInput(@RequestBody MultiValueMap<String, String> formHolder) {
        for (Map.Entry<String, List<String>> entry : formHolder.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }


    // Multiple Request Parameters
    // http://localhost:8080/translator/store?fromLang=EN&fromWord=dog&toLang=DE&toWord=Hund
    // http://localhost:8080/translator/store?fromLang=DE&fromWord=Hund&toLang=ES&toWord=gato
    @RequestMapping(path = "/store", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Boolean> storeTranslation(@RequestParam("fromLang") String fromLanguage,
                                                 @RequestParam("fromWord") String fromWord,
                                                 @RequestParam("toLang") String toLanguage,
                                                 @RequestParam("toWord") String toWord) throws InvalidArguments {
        boolean isInserted;

        try {
            isInserted = userStoryDao.addTranslation(fromLanguage, fromWord, toLanguage, toWord);
        } catch (RuntimeException e) {
            throw new InvalidArguments();
        }

        return Collections.singletonMap("success", isInserted);
    }

    // Multiple request parameter
    // http://localhost:8080/translator/lookup?fromLang=EN&fromWord=dog&toLang=DE
    // http://localhost:8080/translator/lookup?fromLang=ES&fromWord=gato&toLang=EN
    @RequestMapping(path = "/lookup", method = RequestMethod.GET)
    public StringResponse lookUpTranslation(@RequestParam("fromLang") String fromLanguage,
                                            @RequestParam("fromWord") String wordToTranslate,
                                            @RequestParam("toLang") String toLanguage) throws NotFoundException {

        StringResponse translationResult = new StringResponse();

        try {
            translationResult.response = userStoryDao.getTranslation(fromLanguage, wordToTranslate, toLanguage);

            if (translationResult.response == null) {
                throw new NotFoundException();
            }

        } catch (RuntimeException e) {
            throw new NotFoundException();
        }

        return translationResult;
    }
}
