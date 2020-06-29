package com.swapp.application.filter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilterRepository extends CrudRepository<Filter, Long> {

    List<Filter> findByUserId(long userId);

    Optional<Filter> findByIdAndUserId(long id, long userId);

    boolean existsByIdAndUserId(long filterId, long userId);
}
