package com.translatorapi.translatorapi;

import com.translatorapi.translatorapi.dao.UserStory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TranslatorApiApplication {

    @Bean
    protected UserStory userStoryDao() {
        return new UserStory();
    }

	public static void main(String[] args) {
		SpringApplication.run(TranslatorApiApplication.class, args);
	}
}
