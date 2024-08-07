package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.AddressDTO;
import com.hotel.lodgingCommander.dto.CategoryDTO;
import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.dto.RoomDTO;
import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.service.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/properties")
public class HotelController {

    // 임시 User 객체 생성
    private User getTemporaryUser() {
        User user = new User();
        user.setId(1L); // 하드코딩된 ID
        // 필요한 다른 속성들도 설정 가능
        return user;
    }


    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/address")
    public String showAddressForm(Model model) {
        model.addAttribute("addressDTO", new AddressDTO());
        return "address-form";
    }

    /*@PostMapping("/address")
    public String saveAddress(@ModelAttribute AddressDTO addressDTO, Model model, HttpSession session) {
        Long addressId = hotelService.saveAddress(addressDTO);
        model.addAttribute("addressId", addressId);
        return "redirect:/properties/category?addressId=" + addressId;
    }*/

    @PostMapping("/address")
    public String saveAddress(@ModelAttribute AddressDTO addressDTO, HttpSession session) {
        Long addressId = hotelService.saveAddress(addressDTO);
        session.setAttribute("addressId", addressId);
        return "redirect:/properties/category?addressId=" + addressId;
    }

    @GetMapping("/category")
    public String showCategoryForm( Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "category-form";
    }

    @PostMapping("/category")
    public String saveCategory(@ModelAttribute CategoryDTO categoryDTO, HttpSession session) {
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
