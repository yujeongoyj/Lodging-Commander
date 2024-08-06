package com.hotel.lodgingCommander.dto;


import lombok.Data;

public class UserDTO {

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private String tel;
        private String grade;
        private String role;

//        public User toEntity() {
//            return User.builder()
//                    .username(username)
//                    .password(password)
//                    .email(email)
//                    .role(role);
//        }
    }
}
