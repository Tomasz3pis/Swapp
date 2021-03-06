package com.swapp.application.filter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FilterService {

    private FilterRepository filterRepository;

    public Filter addFilter(long userId, Filter filter) {
        filter.setUserId(userId);
        return filterRepository.save(filter);
    }

    public Optional<Filter> getFilterByIdAndUserId(long id, long userId) {
        return filterRepository.findByIdAndUserId(id, userId);
    }

    public void deleteFilter(long id) {
        filterRepository.deleteById(id);
    }

    public List<Filter> getAllFilters(long userId) {
        return filterRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Filter::getId))
                .collect(Collectors.toList());
    }

    public void updateFilter(long id, long userId, Filter filter) {
        Filter filterToUpdate = getFilterFromDatabase(id, userId);

        filterToUpdate.setDateFrom(filter.getDateFrom());
        filterToUpdate.setDateTo(filter.getDateTo());
        filterToUpdate.setDescription(filter.getDescription());
        filterToUpdate.setName(filter.getName());

        filterRepository.save(filterToUpdate);
    }

    private Filter getFilterFromDatabase(long id, long userId) {
        Optional<Filter> filterFromDb = getFilterByIdAndUserId(id, userId);

        if (!filterFromDb.isPresent()) {
            throw new IllegalStateException("Filter with id: " + id + " does not exist in database");
        }

        return filterFromDb.get();
    }
}
