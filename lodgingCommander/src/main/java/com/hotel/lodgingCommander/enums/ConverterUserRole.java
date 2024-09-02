package com.hotel.lodgingCommander.enums;

import com.hotel.lodgingCommander.entity.enums.UserRole;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConverterUserRole extends AbstractCodedEnumConverter<UserRole,String> {
    public ConverterUserRole() {
        super(UserRole.class);
    }
}
