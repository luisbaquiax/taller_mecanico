package org.ayd.apimecahnicalworkshop.utils;

import lombok.Getter;

@Getter
public enum TypeMovment {
    //type_movement ENUM('entrada', 'salida', 'ajuste') NOT NULL,
    ENTRY("entrada"),
    INVENTORY_OULET("salida"),
    INVENTORY_ADJUSTEMENT ("ajuste");

    private final String typeMovment;

    TypeMovment(String typeMovment) {
        this.typeMovment = typeMovment;
    }
}
