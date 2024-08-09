package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.*;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.service.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/properties")
public class AddHotelController {

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

        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    public ResponseEntity<Map<String, Long>> saveCategory(@RequestBody CategoryDTO categoryDTO, HttpSession session) {
        Long categoryId = hotelService.saveCategory(categoryDTO);
        Long addressId = (Long) session.getAttribute("addressId");
        System.out.println(addressId);
        session.setAttribute("categoryId", categoryId);

        Map<String, Long> response = new HashMap<>();
        response.put("categoryId", categoryId);
        response.put("addressId", addressId);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/hotel")
    public ResponseEntity<Map<String, Long>> saveHotel(@RequestBody HotelDTO hotelDTO, HttpSession session) {
        Long hotelId = hotelService.saveHotel(hotelDTO, getTemporaryUser());
        session.setAttribute("hotelId", hotelId); // 필요한 경우 세션에 저장

        Map<String, Long> response = new HashMap<>();
        response.put("hotelId", hotelId);
        return ResponseEntity.ok(response);
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




    @PostMapping("/room")
    public ResponseEntity<Map<String, Long>> saveRoom(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("detail") String detail,
            @RequestParam("maxPeople") int maxPeople,
            @RequestParam("hotelId") Long hotelId,
            @RequestParam(value = "image", required = false) MultipartFile image,
            RedirectAttributes redirectAttributes,
            HttpSession session) {


        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(name);
        roomDTO.setPrice(price);
        roomDTO.setDetail(detail);
        roomDTO.setMaxPeople(maxPeople);
        roomDTO.setHotelId(hotelId);
        roomDTO.setQuantity(1);  // quantity는 폼 개수로 처리되므로 기본값 1로 설정

        if (image != null && !image.isEmpty()) {
            try {
                Long imgId = hotelService.saveImage(image);
                roomDTO.setImgId(imgId); // imgId를 RoomDTO에 설정
            } catch (IOException e) {
                e.printStackTrace();

                redirectAttributes.addFlashAttribute("message", "이미지 업로드 중 오류가 발생했습니다.");

            }
        }

        hotelService.saveRoom(roomDTO);
        redirectAttributes.addFlashAttribute("message", "객실이 성공적으로 저장되었습니다.");
        Map<String, Long> response = new HashMap<>();
        response.put("roomId", roomDTO.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<Map<String, Long>> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            Long imgId = hotelService.saveImage(image);
            Map<String, Long> response = new HashMap<>();
            response.put("imgId", imgId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
