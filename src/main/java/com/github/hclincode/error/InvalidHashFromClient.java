package com.github.hclincode.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Hash is invalid or has existed.")
public class InvalidHashFromClient extends RuntimeException {
    public InvalidHashFromClient(String msg) {
        super(msg);
    }
}