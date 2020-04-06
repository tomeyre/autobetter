package com.tom.autobetter.dynamodb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AutobetterRepository extends CrudRepository<AutobetterEntity, String> {
    List<AutobetterEntity> findAllById(Integer name);
}