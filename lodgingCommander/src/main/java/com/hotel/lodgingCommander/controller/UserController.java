    package com.hotel.lodgingCommander.controller;
    
    import com.hotel.lodgingCommander.dto.user.UserDTO;
    import com.hotel.lodgingCommander.entity.User;
    import com.hotel.lodgingCommander.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Optional;
    
    @RestController
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @RequestMapping("/user/")
    public class UserController {
    
        private final UserService USER_SERVICE;
    
        @Autowired
        public UserController(UserService userService) {
            USER_SERVICE = userService;
        }
    
        @PostMapping("register")
        public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
            USER_SERVICE.registerUser(userDTO.toEntity());

            return ResponseEntity.ok().build();
        }

        @PutMapping("update")
        public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            try {
                USER_SERVICE.update(email, userDTO.toEntity());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("업데이트 중 오류가 발생했습니다.");
            }
        }


        @DeleteMapping("delete")
        public ResponseEntity<?> delete() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            USER_SERVICE.delete(email);
            SecurityContextHolder.clearContext();
    
            return ResponseEntity.ok().build();
        }
    
        @GetMapping("checkUser")
        public ResponseEntity<Optional<User>> getUserPage() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Optional<User> user = USER_SERVICE.getUserInfo(email);
    
            return ResponseEntity.ok(user);
        }
    
        @RequestMapping("authSuccess")
        public ResponseEntity<Map<String, Object>> authSuccess() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            String authorities = authentication.getAuthorities().toString();
            System.out.println("Auth has Success");
    
            Map<String, Object> userInfo = new HashMap<>();
            UserDTO userDTO = USER_SERVICE.getUserDTO(email);
    
            userInfo.put("result", "success");
            userInfo.put("userDTO", userDTO);
            userInfo.put("id", userDTO.getId());
            userInfo.put("email", userDTO.getEmail());
            userInfo.put("password", userDTO.getPassword());
            userInfo.put("nickname", userDTO.getNickname());
            userInfo.put("tel", userDTO.getTel());
            userInfo.put("grade", userDTO.getGrade());
            userInfo.put("role", userDTO.getRole());
    
            return ResponseEntity.ok(userInfo);
        }
    
        @RequestMapping("authFail")
        public ResponseEntity<Map<String, Object>> authFail() {
            System.out.println("Auth has failed");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", "fail");
    
            return ResponseEntity.ok(resultMap);
        }
    
        @RequestMapping("logOutSuccess")
        public ResponseEntity<Void> logOutSuccess(Authentication authentication) {
            System.out.println("log out success");
            System.out.println("로그아웃 후 사용자 정보 테스트 : " + authentication);
            SecurityContextHolder.clearContext();
    
            return ResponseEntity.ok().build();
        }
    
        @PostMapping("checkEmail")
        public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> emailRequest) {
            String email = emailRequest.get("email");
            boolean exists = USER_SERVICE.emailExists(email);
            Map<String, Boolean> response = new HashMap<>();
            response.put("exists", exists);
            return ResponseEntity.ok(response);
        }
    
    }