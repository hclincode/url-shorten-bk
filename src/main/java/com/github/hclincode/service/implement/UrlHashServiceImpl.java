package com.github.hclincode.service.implement;

import java.util.Optional;

import com.github.hclincode.io.entity.UrlHashEntity;
import com.github.hclincode.repository.UrlHashRepository;
import com.github.hclincode.service.UrlHashService;
import com.github.hclincode.shared.configure.ConfigureProps;
import com.github.hclincode.shared.dto.UrlHashDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlHashServiceImpl implements UrlHashService {

    @Autowired
    UrlHashRepository urlHashRepo;

    @Autowired
    ConfigureProps conf;

    @Override
    public UrlHashDto hashUrlEndToEnd(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * TODO add LRU cache https://trello.com/c/bUX3oacY
     */
    @Override
    public Optional<UrlHashDto> getUrlByHash(String hash) {
        UrlHashEntity urlHashEntity = urlHashRepo.findByUrlHashAndLastUpdateTimeGreaterThanEqual(hash,
                conf.getHashLiveSeconds());

        if (urlHashEntity != null) {
            UrlHashDto urlHashDto = new UrlHashDto();
            BeanUtils.copyProperties(urlHashEntity, urlHashDto);
            return Optional.of(urlHashDto);
        }

        return Optional.empty();
    }

}