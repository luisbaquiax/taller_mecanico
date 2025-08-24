package org.ayd.apimecahnicalworkshop.utils;

public enum Roles {
    ADMIN(1L),
    EMPLOYEE(2L),
    ESPECIALIST(3L),
    CLIENT(4L);
    private final Long id;

    Roles(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
