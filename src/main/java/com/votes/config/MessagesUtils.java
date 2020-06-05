package com.votes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessagesUtils {
    private final MessageSource messageSource;

    @Autowired
    public MessagesUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, String... arguments) {
        return messageSource.getMessage(key, arguments, Locale.getDefault());
    }

    public String[] getCodeAndMessage(String key, String... arguments) {
        return messageSource.getMessage(key, arguments, Locale.getDefault()).split(";");
    }
}