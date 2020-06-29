package com.swapp.application.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {

    public static final String EMPTY_CATEGORY_NAME = "categoryValidator.emptyCategoryName";
    public static final String PROVIDED_PARENT_CATEGORY_DOES_NOT_EXIST = "categoryValidator.providedParentCategoryDoesNotExist";
    public static final String OFFER_WITH_PROVIDED_NAME_ALREADY_EXISTS = "offerValidator.offerWithProvidedNameAlreadyExists";

    public static final String FILTER_ACCOUNT_ID_DOES_NOT_EXIST = "validator.accountIdDoesNotExist";
    public static final String FILTER_CATEGORY_ID_DOES_NOT_EXIST = "validator.categoryIdDoesNotExist";
    public static final String FILTER_PRICE_FROM_BIGGER_THEN_PRICE_TO = "filterValidator.priceFromBiggerThenPriceTo";
    public static final String FILTER_DATE_FROM_IS_AFTER_DATE_TO = "filterValidator.dateFromAfterDateTo";
    public static final String FILTER_EMPTY_NAME = "filterValidator.emptyName";

    public static final String USER_WITH_PROVIDED_USERNAME_ALREADY_EXIST = "userValidator.userWithProvidedUsernameAlreadyExists";
    public static final String EMPTY_USERNAME = "userValidator.emptyUsername";
    public static final String EMPTY_FIRST_NAME = "userValidator.emptyFirstname";
    public static final String EMPTY_LAST_NAME = "userValidator.emptyLastname";
    public static final String EMPTY_PASSWORD = "userValidator.emptyPassword";
    public static final String USERNAME_CONTAINS_WHITESPACE = "userValidator.usernameContainsWhitespaces";
    public static final String PASSWORD_CONTAINS_WHITESPACE = "userValidator.passwordContainsWhitespaces";
    public static final String USERNAME_OR_PASSWORD_IS_INCORRECT = "userController.usernameOrPassowrdIsIncorrect";
    public static final String TOO_LONG_USERNAME = "userValidator.tooLongUsername";
    public static final String TOO_LONG_PASSWORD = "userValidator.tooLongPassword";
    public static final String TOO_LONG_FIRST_NAME = "userValidator.tooLongFirstName";
    public static final String TOO_LONG_LAST_NAME = "userValidator.tooLongLastName";
    public static final String INVALID_REFRESH_TOKEN = "tokenService.invalidRefreshToken";
    public static final String INVALID_PHONE_NUMBER = "userValidator.invalidPhoneNumber";
    public static final String INVALID_EMAIL_ADDRESS = "userValidator.invalidEmailAddress";


    private static final ResourceBundle englishBundle = ResourceBundle.getBundle("messages", new Locale("en"));
    private static final ResourceBundle polishBundle = ResourceBundle.getBundle("messages", new Locale("pl"));

    private static final ThreadLocal<Language> languageThreadLocal = new ThreadLocal<>();

    public static String getMessage(String messageKey) {
        return getMessage(messageKey, languageThreadLocal.get());
    }

    public static String getMessage(String messageKey, Language language) {
        if (Language.POLISH.equals(language)) {
            return polishBundle.getString(messageKey);
        }

        return englishBundle.getString(messageKey);
    }

    public static void setLanguage(Language language) {
        languageThreadLocal.set(language);
    }

    public enum Language {
        POLISH,
        ENGLISH;

        public static Language getEnumByString(String language) {
            if (language.contains("pl")) {
                return POLISH;
            }

            if (language.contains("en")) {
                return ENGLISH;
            }

            return ENGLISH;
        }
    }
}
