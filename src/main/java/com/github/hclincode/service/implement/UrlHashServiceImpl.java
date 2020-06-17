package com.github.hclincode.service.implement;

import java.util.Date;
import java.util.Optional;

import com.github.hclincode.error.InvalidHashFromClient;
import com.github.hclincode.error.NoHashSpaceAvailable;
import com.github.hclincode.io.entity.UrlHashEntity;
import com.github.hclincode.repository.UrlHashRepository;
import com.github.hclincode.service.UrlHashService;
import com.github.hclincode.shared.HashUtil;
import com.github.hclincode.shared.configure.ConfigureProps;
import com.github.hclincode.shared.dto.UrlHashDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlHashServiceImpl implements UrlHashService {
    private static final int GEN_HASH_ATTEMPT_TIMES = 2;

    @Autowired
    UrlHashRepository urlHashRepo;

    @Autowired
    ConfigureProps conf;

    @Autowired
    HashUtil hashUtil;

    @Override
    public UrlHashDto hashUrlEndToEnd(UrlHashDto urlQuery) {
        if (urlQuery.getUrlHash().equals("")) {
            return randomHashUrlEndToEnd(urlQuery);
        }

        return userDefinedHashUrlEndToEnd(urlQuery);
    }

    private UrlHashDto userDefinedHashUrlEndToEnd(UrlHashDto urlQuery) {
        String attemptHash = urlQuery.getUrlHash();

        // check hash valid else throw 400
        if (hashUtil.isInValidUrlHash(attemptHash)) {
            throw new InvalidHashFromClient("The hash is not valid.");
        }

        Optional<UrlHashEntity> recordInDb = getAliveRecordByHash(attemptHash);

        if (recordInDb.isEmpty()) {
            return saveUrlHashWithHash(urlQuery, attemptHash);
        }

        UrlHashEntity recordedUrlHashEntity = recordInDb.get();
        if (recordedUrlHashEntity.getUrl().equals(urlQuery.getUrl())) {
            // update time
            urlHashRepo.updateLastUpdateTime(recordedUrlHashEntity.getId());
            UrlHashDto returnValue = new UrlHashDto();
            BeanUtils.copyProperties(recordedUrlHashEntity, returnValue);
            return returnValue;
        }

        // URL in DB with exist hash which is different from user given
        throw new InvalidHashFromClient("Existed hash by others URL.");
    }

    private UrlHashDto randomHashUrlEndToEnd(UrlHashDto urlQuery) {
        // TODO redis cache https://trello.com/c/qATkaZwz

        for (int i = 0; i < GEN_HASH_ATTEMPT_TIMES; i++) {
            String attemptHash = hashUtil.generateUrlHash(conf.getHashLength());
            if (getUrlByHash(attemptHash).isEmpty()) {
                return saveUrlHashWithHash(urlQuery, attemptHash);
            }
        }

        throw new NoHashSpaceAvailable();
    }

    private UrlHashDto saveUrlHashWithHash(UrlHashDto urlQuery, String attemptHash) {
        UrlHashEntity urlHashEntityToSave = new UrlHashEntity();
        BeanUtils.copyProperties(urlQuery, urlHashEntityToSave);
        urlHashEntityToSave.setUrlHash(attemptHash);
        return saveUrlHash(urlHashEntityToSave);
    }

    private UrlHashDto saveUrlHash(UrlHashEntity urlHashEntityToSave) {
        UrlHashEntity storedUrlHashEntity = urlHashRepo.save(urlHashEntityToSave);
        UrlHashDto returnValue = new UrlHashDto();
        BeanUtils.copyProperties(storedUrlHashEntity, returnValue);
        return returnValue;
    }

    @Override
    public Optional<UrlHashDto> getUrlByHash(String hash) {
        Optional<UrlHashEntity> urlHashEntity = getAliveRecordByHash(hash);

        if (urlHashEntity.isPresent()) {
            UrlHashDto urlHashDto = new UrlHashDto();
            BeanUtils.copyProperties(urlHashEntity.get(), urlHashDto);
            return Optional.of(urlHashDto);
        }

        return Optional.empty();
    }

    /**
     * TODO add LRU cache https://trello.com/c/bUX3oacY
     */
    private Optional<UrlHashEntity> getAliveRecordByHash(String hash) {
        Optional<UrlHashEntity> returnValue = Optional.empty();

        UrlHashEntity urlHashEntity = urlHashRepo.findByUrlHashAndLastUpdateTimeGreaterThanEqual(hash,
                conf.getHashLiveSeconds());
        if (urlHashEntity != null) {
            return Optional.of(urlHashEntity);
        }

        return returnValue;
    }
}