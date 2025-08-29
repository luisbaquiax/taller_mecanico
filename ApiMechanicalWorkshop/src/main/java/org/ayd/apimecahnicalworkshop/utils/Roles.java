package org.ayd.apimecahnicalworkshop.utils;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN(1L),
    EMPLOYEE(2L),
    ESPECIALIST(3L),
    CLIENT(4L),
    SUPPLIER(5L);

    private final Long id;

    Roles(Long id) {
        this.id = id;
    }
}
