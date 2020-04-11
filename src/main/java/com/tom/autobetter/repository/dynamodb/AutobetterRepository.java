package com.tom.autobetter.repository.dynamodb;

import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AutobetterRepository extends CrudRepository<RaceDayEntity, Integer> {
}