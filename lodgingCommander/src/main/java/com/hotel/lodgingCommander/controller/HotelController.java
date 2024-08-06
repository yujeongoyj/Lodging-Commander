package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/properties")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public String showHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDTO());
        return "hotel-form";
    }

    @PostMapping
    public HotelDTO createHotel(@RequestBody HotelDTO hotelRequestDTO) {
        return hotelService.saveHotel(hotelRequestDTO);
    }

    @PutMapping("/{propertyId}")
    public HotelDTO updateHotel(@PathVariable Long propertyId, @RequestBody HotelDTO hotelRequestDTO) {
        return hotelService.updateHotel(propertyId, hotelRequestDTO);
    }

    @DeleteMapping("/{propertyId}")
    public void deleteHotel(@PathVariable Long propertyId) {
        hotelService.deleteHotel(propertyId);
    }
}
