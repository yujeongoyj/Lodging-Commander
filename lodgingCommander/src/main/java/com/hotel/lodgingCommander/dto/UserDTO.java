package com.hotel.lodgingCommander.dto;

import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.entity.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String tel;
    private String grade;
    private UserRole role;
    private String role2;


    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.tel = user.getTel();
        this.grade = user.getGrade();
        this.role = user.getRole();
    }

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .tel(this.tel)
                .grade(this.grade)
                .role(role.USER)
                .build();
    }
}
