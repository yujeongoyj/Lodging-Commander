package com.hotel.lodgingCommander.enums;

import com.hotel.lodgingCommander.model.entity.enums.UserGrade;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConverterGrade extends AbstractCodedEnumConverter<UserGrade,String> {
    public ConverterGrade() {
        super(UserGrade.class);
    }
}
