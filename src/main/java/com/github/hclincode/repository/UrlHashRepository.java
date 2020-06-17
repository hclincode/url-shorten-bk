package com.github.hclincode.repository;

import com.github.hclincode.io.entity.UrlHashEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UrlHashRepository extends CrudRepository<UrlHashEntity, Long> {
    @Query(value = "SELECT * FROM url_hash WHERE hash = :urlHash AND (last_update_time + INTERVAL :liveSeconds second >= CURRENT_TIMESTAMP)", nativeQuery = true)
    public UrlHashEntity findByUrlHashAndLastUpdateTimeGreaterThanEqual(String urlHash, int liveSeconds);

    @Query(value = "SELECT * FROM url_hash WHERE url = :url AND (last_update_time + INTERVAL :liveSeconds second >= CURRENT_TIMESTAMP)", nativeQuery = true)
    public UrlHashEntity findByUrlAndLastUpdateTimeGreaterThanEqual(String url, int liveSeconds);

    @Transactional
    @Modifying
    @Query(value = "UPDATE url_hash SET last_update_time = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    public int updateLastUpdateTime(Long id);
}