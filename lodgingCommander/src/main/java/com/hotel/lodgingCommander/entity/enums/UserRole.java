package com.hotel.lodgingCommander.entity.enums;

import com.hotel.lodgingCommander.enums.CodeEnum;

public enum UserRole implements CodeEnum<String> {
    ADMIN("ADMIN")
    ,USER("USER");

    private final String code;

    UserRole(String code) {
       this.code=code;
    }

    public String getCode() {
        return code;
    }

}
