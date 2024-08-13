package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.*;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.service.AddHotelService;
import com.hotel.lodgingCommander.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/properties")
public class AddHotelController {

 /*   private User getTemporaryUser() {
        User user = new User();
        user.setId(1L);

        return user;
    }*/


    private final AddHotelService addHotelService;
    private final UserService userService;

    public AddHotelController(AddHotelService addHotelService, UserService userService) {
        this.addHotelService = addHotelService;
        this.userService = userService;
    }


    @PostMapping("/address")
    public ResponseEntity<Map<String, Long>> saveAddress(@RequestBody AddressDTO addressDTO) {
        Long addressId = addHotelService.saveAddress(addressDTO);


        Map<String, Long> response = new HashMap<>();
        response.put("addressId", addressId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    public ResponseEntity<Map<String, Long>> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        Long categoryId = addHotelService.saveCategory(categoryDTO);

        Map<String, Long> response = new HashMap<>();
        response.put("categoryId", categoryId);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/hotel")
    public ResponseEntity<Map<String, Long>> saveHotel(@RequestBody HotelDTO hotelDTO) {
        User user = userService.getUserById(hotelDTO.getUserId());
        Long hotelId =  addHotelService.saveHotel(hotelDTO, user);
        Map<String, Long> response = new HashMap<>();
        response.put("hotelId", hotelId);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/facility")
    public ResponseEntity<Map<String, Long>> saveFacility(@RequestParam("hotelId") Long hotelId, @RequestBody FacilityDTO facilityDTO) {
        if (hotelId == null) {
            return ResponseEntity.badRequest().build();
        }

        facilityDTO.setHotelId(hotelId);
        addHotelService.saveFacility(facilityDTO);

        Map<String, Long> response = new HashMap<>();
        response.put("hotelId", hotelId);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/room")
    public ResponseEntity<Map<String, Long>> saveRoom(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("detail") String detail,
            @RequestParam("maxPeople") int maxPeople,
            @RequestParam("hotelId") Long hotelId,
            @RequestParam(value = "imgId", required = false) Long imgId) {

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(name);
        roomDTO.setPrice(price);
        roomDTO.setDetail(detail);
        roomDTO.setMaxPeople(maxPeople);
        roomDTO.setHotelId(hotelId);
        roomDTO.setQuantity(1);

        if (imgId != null) {
            roomDTO.setImgId(imgId);
        }


        addHotelService.saveRoom(roomDTO);
        Map<String, Long> response = new HashMap<>();
        response.put("roomId", roomDTO.getId());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<Map<String, Long>> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            Long imgId = addHotelService.saveImage(image);
            Map<String, Long> response = new HashMap<>();
            response.put("imgId", imgId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
