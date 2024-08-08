package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.*;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.service.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/properties")
public class AddHotelController {

    // 임시 User 객체 생성
    private User getTemporaryUser() {
        User user = new User();
        user.setId(1L);

        return user;
    }


    private final HotelService hotelService;

    public AddHotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }



    @PostMapping("/address")
    public ResponseEntity<Map<String, Long>> saveAddress(@RequestBody AddressDTO addressDTO, HttpSession session) {
        Long addressId = hotelService.saveAddress(addressDTO);
        session.setAttribute("addressId", addressId);
        System.out.println("AddressDTO in controller: " + addressDTO);


        Map<String, Long> response = new HashMap<>();
        response.put("addressId", addressId);

        // 클라이언트로 위의 addressId를 포함한 응답을 보내는 코드
        // 따라서 세션에 저장한"addressId"는 클라이언트의 `response.data.addressId`값이 된다
        return ResponseEntity.ok(response);
    }


    @PostMapping("/category")
    public String saveCategory(@RequestBody CategoryDTO categoryDTO, HttpSession session) {
    Long addressId = (Long) session.getAttribute("addressId");
    if(addressId == null) {
        return "redirect:/error";
    }
    Long categoryId = hotelService.saveCategory(categoryDTO);
    session.setAttribute("categoryId", categoryId);
    return "redirect:/properties/hotel?categoryId=" + categoryId;
   }

    @GetMapping("/hotel")
    public String showHotelForm(HttpSession session, Model model) {
        Long addressId = (Long) session.getAttribute("addressId");
        Long categoryId = (Long) session.getAttribute("categoryId");

        if (addressId == null || categoryId == null) {
            return "redirect:/error";
        }
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setAddressId(addressId);
        hotelDTO.setCategoryId(categoryId);

        model.addAttribute("hotelDTO", hotelDTO);
        System.out.println(addressId);
        return "hotel-form";
    }


    @PostMapping("/hotel")
    public String saveHotel(@ModelAttribute HotelDTO hotelDTO, HttpSession session) {
        Long addressId = (Long) session.getAttribute("addressId");
        Long categoryId = (Long) session.getAttribute("categoryId");
        System.out.println(addressId);
        if (addressId == null || categoryId == null) {
            return "redirect:/error";
        }

        User user = getTemporaryUser(); // 임시로 User 객체 가져오기

        hotelDTO.setAddressId(addressId);
        hotelDTO.setCategoryId(categoryId);

        long hotelId = hotelService.saveHotel(hotelDTO, user);
        session.setAttribute("hotelId", hotelId);
        return "redirect:/properties/room?hotelId=" + hotelId;
    }

    @PostMapping("/facility")
    public ResponseEntity<Map<String, Long>> saveFacility(@RequestBody FacilityDTO facilityDTO, HttpSession session) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        if (hotelId == null) {
            return ResponseEntity.badRequest().build();
        }

        facilityDTO.setHotelId(hotelId);
        hotelService.saveFacility(facilityDTO);

        Map<String, Long> response = new HashMap<>();
        response.put("hotelId", hotelId);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/room")
    public String showRoomForm(HttpSession session, Model model) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setHotelId(hotelId);

        model.addAttribute("roomDTO", new RoomDTO());
        return "room-form";
    }

    @PostMapping("/room")
    public String saveRoom(@ModelAttribute RoomDTO roomDTO, HttpSession session) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        roomDTO.setHotelId(hotelId);

        hotelService.saveRoom(roomDTO);
        return "redirect:/properties/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
