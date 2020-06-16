package com.github.hclincode.ui.contorller;

import com.github.hclincode.shared.dto.UrlHashDto;
import com.github.hclincode.ui.model.request.HashUrlRequestModel;
import com.github.hclincode.ui.model.response.HashUrlResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/UrlHash")
public class UrlHashController {

    @GetMapping(value = "/{hashValue}")
    public String getUrlByHash(@PathVariable("hashValue") String hashValue) {
        // TODO
        return hashValue;
    }

    @PostMapping
    public HashUrlResponse getHashByUrl(@RequestBody HashUrlRequestModel hashUrlRequestModel) {
        HashUrlResponse returnValue = new HashUrlResponse();

        UrlHashDto urlHashDto = new UrlHashDto();
        BeanUtils.copyProperties(hashUrlRequestModel, urlHashDto);

        // TODO service call

        return returnValue;
    }

}