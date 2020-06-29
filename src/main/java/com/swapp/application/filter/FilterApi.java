package com.swapp.application.filter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("filters")
@CrossOrigin
public interface FilterApi {

    @GetMapping(value = "/{filterId}")
    ResponseEntity<Filter> getFilterById(@PathVariable long filterId);

    @GetMapping
    ResponseEntity<List<Filter>> getFilters();

    @PostMapping
    ResponseEntity<?> addFilter(Filter filter);

    @PutMapping(value = "/{filterId}")
    ResponseEntity<?> updateFilter(@PathVariable long filterId, Filter filter);

    @DeleteMapping(value = "/{filterId}")
    ResponseEntity<?> deleteFilter(@PathVariable long filterId);
}
