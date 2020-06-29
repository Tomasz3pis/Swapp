package com.swapp.application.filter;

import com.swapp.application.auth.UserProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
public class FilterController implements FilterApi {

    private FilterService filterService;
    private FilterValidator filterValidator;
    private UserProvider userProvider;

    @Override
    public ResponseEntity<Filter> getFilterById(@PathVariable long filterId) {
        long userId = userProvider.getCurrentUserId();

        log.info("Retrieving filter with id: {}", filterId);
        Optional<Filter> filter = filterService.getFilterByIdAndUserId(filterId, userId);

        if (!filter.isPresent()) {
            log.info("Filter with id {} was not found", filterId);
            return ResponseEntity.notFound().build();
        }

        log.info("Filter with id {} was successfully retrieved", filterId);
        return ResponseEntity.ok(filter.get());
    }

    @Override
    public ResponseEntity<List<Filter>> getFilters() {
        long userId = userProvider.getCurrentUserId();

        return ResponseEntity.ok(filterService.getAllFilters(userId));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addFilter(@RequestBody Filter filter) {
        long userId = userProvider.getCurrentUserId();

        log.info("Adding filter to the database");

        List<String> validationResult = filterValidator.validateFilterRequest(filter, userId);
        if (!validationResult.isEmpty()) {
            log.info("Filter is not valid {}", validationResult);
            return ResponseEntity.badRequest().body(validationResult);
        }

        Filter createdFilter = filterService.addFilter(userId, filter);
        log.info("Saving filter to the database was successful. Filter id is {}", createdFilter.getId());

        return ResponseEntity.ok(createdFilter.getId());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateFilter(@PathVariable long filterId, @RequestBody Filter filter) {
        long userId = userProvider.getCurrentUserId();

        Optional<Filter> filterByIdAndUserId = filterService.getFilterByIdAndUserId(filterId, userId);
        if (!filterByIdAndUserId.isPresent()) {
            log.info("No filter with id {} was found, not able to update", filterId);
            return ResponseEntity.notFound().build();
        }

        List<String> validationResult = filterValidator.validateFilterRequest(filter, userId);
        if (!validationResult.isEmpty()) {
            log.info("Filter is not valid {}", validationResult);
            return ResponseEntity.badRequest().body(validationResult);
        }

        filterService.updateFilter(filterId, userId, filter);
        log.info("Filter with id {} was successfully updated", filterId);

        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteFilter(@PathVariable long filterId) {
        long userId = userProvider.getCurrentUserId();

        Optional<Filter> filterByIdAndUserId = filterService.getFilterByIdAndUserId(filterId, userId);
        if (!filterByIdAndUserId.isPresent()) {
            log.info("No filter with id {} was found, not able to delete", filterId);
            return ResponseEntity.notFound().build();
        }

        filterService.deleteFilter(filterId);

        return ResponseEntity.ok().build();
    }
}
