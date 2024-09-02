package com.hotel.lodgingCommander.service.impl;


import com.hotel.lodgingCommander.model.AddressModel;
import com.hotel.lodgingCommander.model.entity.Address;
import com.hotel.lodgingCommander.model.repository.AddressRepository;
import com.hotel.lodgingCommander.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Boolean save(AddressModel addressDTO) {
        var address = Address.builder()
                .address(addressDTO.getAddress())
                .addressDetail(addressDTO.getAddressDetail())
                .postCode(addressDTO.getPostCode())
                .latitude(addressDTO.getLatitude())
                .longitude(addressDTO.getLongitude())
                .build();
        return addressRepository.save(address) != null ? true : false;
    }
}
