package com.translatorapi.translatorapi;

import com.translatorapi.translatorapi.controllers.UserStoryController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslatorApiApplicationTests {

    @Autowired
    private UserStoryController controller;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

}
