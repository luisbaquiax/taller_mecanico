package org.ayd.apimecahnicalworkshop.utils;

import lombok.Getter;

@Getter
public class ErrorApi extends Error{
    private final int status;

    public ErrorApi(int status, String message) {
        super(message);
        this.status = status;
    }
}
