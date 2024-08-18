        package lodgingCommander.controller;

        import com.hotel.lodgingCommander.model.LikeListDTO;
        import com.hotel.lodgingCommander.service.LikeListService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        @RestController
        @CrossOrigin(origins = "http://localhost:3000")
        @RequestMapping("/likelist")
        @RequiredArgsConstructor
        public class LikeListController {

            private final LikeListService likeListService;

            @PostMapping("/add")
            public Map<String, Object> addLike(@RequestBody LikeListDTO likeListDTO) {
                Map<String, Object> resultMap = new HashMap<>();
                try {
                    likeListService.addLike(likeListDTO);
                    resultMap.put("userId", likeListDTO.getUserId());
                    resultMap.put("hotelId", likeListDTO.getHotelId());
                    resultMap.put("result", "success");
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.put("result", "fail");
                }
                return resultMap;
            }

            @PostMapping("/remove")
            public Map<String, Object> removeLike(@RequestBody LikeListDTO likeListDTO) {
                Map<String, Object> resultMap = new HashMap<>();
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
            // 좋아요 상태 조회
            @GetMapping("/{userId}/{hotelId}")
            public Map<String, Object> getLikeStatus(@PathVariable Long userId, @PathVariable Long hotelId) {
                Map<String, Object> resultMap = new HashMap<>();
                try {
                    boolean isLiked = likeListService.findByUserIdAndHotelId(userId, hotelId) != null;
                    resultMap.put("isLiked", isLiked);
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.put("error", "Failed to fetch like status");
                }
                return resultMap;
            }

            // 찜목록에서 좋아요 삭제
            @DeleteMapping("/{id}")
            public Map<String, Object> removeLikeById(@PathVariable Long id) {
                Map<String, Object> resultMap = new HashMap<>();
                try {
                    likeListService.removeLikeById(id);
                    resultMap.put("result", "success");
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.put("result", "fail");
                }
                return resultMap;
            }
        }