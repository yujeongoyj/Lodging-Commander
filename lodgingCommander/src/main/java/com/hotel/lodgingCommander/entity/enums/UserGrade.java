package com.hotel.lodgingCommander.entity.enums;

import com.hotel.lodgingCommander.enums.CodeEnum;

public enum UserGrade implements CodeEnum<String> {
    VIP("VIP"),
    GOLD("Gold"),
    SILVER("Silver");

    private final String code;

    UserGrade(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
