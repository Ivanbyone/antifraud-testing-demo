package io.ivanbyone.dictionary_service.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import io.ivanbyone.dictionary_service.entity.BlackDictionary;

public interface BlackDictionaryRepository extends ListCrudRepository<BlackDictionary, Long> {
    
    @Query("SELECT b_value FROM black_dictionary WHERE b_value IN (:vals)")
    Set<String> findDuplicates(@Param("vals") Collection<String> vals);
}
