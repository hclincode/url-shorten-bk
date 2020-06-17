package com.github.hclincode.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUrlFoundByHash extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -1910336985362088012L;

}