package com.swapp.application.auth;

import static com.swapp.application.config.MessageProvider.EMPTY_FIRST_NAME;
import static com.swapp.application.config.MessageProvider.EMPTY_LAST_NAME;
import static com.swapp.application.config.MessageProvider.EMPTY_PASSWORD;
import static com.swapp.application.config.MessageProvider.EMPTY_USERNAME;
import static com.swapp.application.config.MessageProvider.INVALID_EMAIL_ADDRESS;
import static com.swapp.application.config.MessageProvider.INVALID_PHONE_NUMBER;
import static com.swapp.application.config.MessageProvider.PASSWORD_CONTAINS_WHITESPACE;
import static com.swapp.application.config.MessageProvider.TOO_LONG_FIRST_NAME;
import static com.swapp.application.config.MessageProvider.TOO_LONG_LAST_NAME;
import static com.swapp.application.config.MessageProvider.TOO_LONG_PASSWORD;
import static com.swapp.application.config.MessageProvider.TOO_LONG_USERNAME;
import static com.swapp.application.config.MessageProvider.USERNAME_CONTAINS_WHITESPACE;
import static com.swapp.application.config.MessageProvider.USER_WITH_PROVIDED_USERNAME_ALREADY_EXIST;
import static com.swapp.application.config.MessageProvider.getMessage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserValidator {

    static final int PASSWORD_MAX_LENGTH = 255;
    static final int FIRST_NAME_MAX_LENGTH = 255;
    static final int LAST_NAME_MAX_LENGTH = 255;
    static final int USERNAME_MAX_LENGTH = 255;
    static final String PHONE_NUMBER_PATTERN = "^(\\\\d{3}[- .]?){3}\\\\d{3}$";
    static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private UserService userService;

    List<String> validateUser(User user) {
        List<String> validationResults = new ArrayList<>();

        if (user.getUserName() == null) {
            validationResults.add(getMessage(EMPTY_USERNAME));
        }

        if (user.getUserName() != null && StringUtils.containsWhitespace(user.getUserName())) {
            validationResults.add(getMessage(USERNAME_CONTAINS_WHITESPACE));
        }

        if (user.getUserName() != null && userService.isUsernameAlreadyUsed(user.getUserName())) {
            validationResults.add(getMessage(USER_WITH_PROVIDED_USERNAME_ALREADY_EXIST));
        }

        if (user.getUserName() != null && user.getUserName().length() > USERNAME_MAX_LENGTH) {
            validationResults.add(String.format(getMessage(TOO_LONG_USERNAME), USERNAME_MAX_LENGTH));
        }

        if (user.getPassword() == null) {
            validationResults.add(getMessage(EMPTY_PASSWORD));
        }

        if (user.getPassword() != null && StringUtils.containsWhitespace(user.getPassword())) {
            validationResults.add(getMessage(PASSWORD_CONTAINS_WHITESPACE));
        }

        if (user.getPassword() != null && user.getPassword().length() > PASSWORD_MAX_LENGTH) {
            validationResults.add(String.format(getMessage(TOO_LONG_PASSWORD), PASSWORD_MAX_LENGTH));
        }

        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) {
            validationResults.add(getMessage(EMPTY_FIRST_NAME));
        }

        if (user.getFirstName() != null && user.getFirstName().length() > FIRST_NAME_MAX_LENGTH) {
            validationResults.add(String.format(getMessage(TOO_LONG_FIRST_NAME), FIRST_NAME_MAX_LENGTH));
        }

        if (user.getLastName() == null || user.getLastName().trim().equals("")) {
            validationResults.add(getMessage(EMPTY_LAST_NAME));
        }

        if (user.getFirstName() != null && user.getLastName().length() > LAST_NAME_MAX_LENGTH) {
            validationResults.add(String.format(getMessage(TOO_LONG_LAST_NAME), LAST_NAME_MAX_LENGTH));
        }

        if (!Pattern.compile(PHONE_NUMBER_PATTERN).matcher(user.getPhoneNumber()).matches()) {
            validationResults.add(getMessage(INVALID_PHONE_NUMBER));
        }

        if (!Pattern.compile(EMAIL_PATTERN).matcher(user.getUserMail()).matches()) {
            validationResults.add(getMessage(INVALID_EMAIL_ADDRESS));
        }

        return validationResults;
    }
}
