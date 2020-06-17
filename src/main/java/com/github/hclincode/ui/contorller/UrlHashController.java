package com.github.hclincode.ui.contorller;

import java.util.Optional;

import com.github.hclincode.error.InvalidHashFromClient;
import com.github.hclincode.error.NoUrlFoundByHash;
import com.github.hclincode.service.UrlHashService;
import com.github.hclincode.shared.dto.UrlHashDto;
import com.github.hclincode.ui.model.request.HashUrlRequestModel;
import com.github.hclincode.ui.model.response.HashUrlResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/UrlHash")
@ResponseBody
public class UrlHashController {

    @Autowired
    UrlHashService urlHashService;

    @GetMapping(value = "/{hashValue}")
    public HashUrlResponse getUrlByHash(@PathVariable("hashValue") String hashValue) {
        HashUrlResponse returnValue = new HashUrlResponse();

        Optional<UrlHashDto> urlHashDto = urlHashService.getUrlByHash(hashValue);
        if (urlHashDto.isPresent()) {
            BeanUtils.copyProperties(urlHashDto.get(), returnValue);
        } else {
            throw new NoUrlFoundByHash();
        }

        return returnValue;
    }

    /**
     * By spec: Case 1: the same URL has the same auto generated hash string in
     * short time. Case 2: the same URL might have different hash string when user
     * defined
     */
    @PostMapping
    public HashUrlResponse getHashByUrl(@RequestBody HashUrlRequestModel hashUrlRequestModel) {
        HashUrlResponse returnValue = new HashUrlResponse();

        UrlHashDto urlHashDto = new UrlHashDto();
        BeanUtils.copyProperties(hashUrlRequestModel, urlHashDto);

        UrlHashDto generatedUrlHashDto = urlHashService.hashUrlEndToEnd(urlHashDto);
        BeanUtils.copyProperties(generatedUrlHashDto, returnValue);

        return returnValue;
    }
}