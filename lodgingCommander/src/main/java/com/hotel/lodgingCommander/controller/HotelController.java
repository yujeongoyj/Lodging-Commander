package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/properties/")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public String listHotels(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        return "hotel-list";
    }


    @GetMapping("new")
    public String showHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDTO());
        return "hotel-form";
    }

    @PostMapping
    public String createHotel(@ModelAttribute HotelDTO hotelRequestDTO) {
        hotelService.saveHotel(hotelRequestDTO);
        return "redirect:/properties/new";
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
