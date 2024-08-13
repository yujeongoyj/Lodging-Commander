package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.LikeListDTO;
import com.hotel.lodgingCommander.model.MapDTO;
import com.hotel.lodgingCommander.service.HotelService;
import com.hotel.lodgingCommander.service.LikeListService;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins ="http://localhost:3000")
@RequestMapping("/likelist")
public class LikeListController {

    private final HotelService hotelService;
    private LikeListService likeListService;


    public LikeListController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public Map<String, Object> likeList(@PathVariable Long id) {
        LikeListDTO likeListDTO;
        HotelService hotelService;

        // MapDTO를 HashMap으로 변환하여 반환합니다.
        Map<String, Object> resultMap = new HashMap<>();
        //resultMap.put("id", likeListDTO.getId());

        return resultMap;
    }

    @PostMapping("/add")
    public HashMap<String, Object> addLike(@RequestBody LikeListDTO likeListDTO) {
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            likeListService.addLike(likeListDTO);
            resultMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }


    @PostMapping("/remove")
    public HashMap<String, Object> removeLike(@RequestBody LikeListDTO likeListDTO) {
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            likeListService.removeLike(likeListDTO);
            resultMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @GetMapping("/user/{userId}")
    public List<LikeListDTO> getLikesByUserId(@PathVariable long userId) {
        return likeListService.getLikesByUserId(userId);
    }
}
