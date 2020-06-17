package com.github.hclincode.repository;

import com.github.hclincode.io.entity.UrlHashEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlHashRepository extends CrudRepository<UrlHashEntity, Long> {
    @Query(value = "SELECT * FROM url_hash WHERE hash = :urlHash AND (last_update_time + INTERVAL :liveSeconds second >= CURRENT_TIMESTAMP)", nativeQuery = true)
    public UrlHashEntity findByUrlHashAndLastUpdateTimeGreaterThanEqual(String urlHash, int liveSeconds);
}