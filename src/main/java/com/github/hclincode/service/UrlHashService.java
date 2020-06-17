package com.github.hclincode.service;

import java.util.Optional;

import com.github.hclincode.shared.dto.UrlHashDto;

public interface UrlHashService {
    public UrlHashDto hashUrlEndToEnd(UrlHashDto urlHashDto);

    public Optional<UrlHashDto> getUrlByHash(String hash);
}