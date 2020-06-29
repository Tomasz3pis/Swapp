package com.swapp.application.filter;

import static com.swapp.application.config.MessageProvider.FILTER_DATE_FROM_IS_AFTER_DATE_TO;
import static com.swapp.application.config.MessageProvider.FILTER_EMPTY_NAME;
import static com.swapp.application.config.MessageProvider.getMessage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class FilterValidator {

    public List<String> validateFilterRequest(Filter filter, long userId) {
        List<String> validationResults = new ArrayList<>();

        if (filter.getName() == null || filter.getName().trim().equals("")) {
            validationResults.add(getMessage(FILTER_EMPTY_NAME));
        }

        if (filter.getDateFrom() != null && filter.getDateTo() != null
                && filter.getDateFrom().isAfter(filter.getDateTo())) {
            validationResults.add(getMessage(FILTER_DATE_FROM_IS_AFTER_DATE_TO));
        }

        return validationResults;
    }
}
