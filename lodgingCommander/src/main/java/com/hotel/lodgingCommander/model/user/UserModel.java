package com.hotel.lodgingCommander.model.user;

import com.hotel.lodgingCommander.model.entity.User;
import com.hotel.lodgingCommander.model.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String tel;
    private String grade;
    private UserRole role;

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

    public UserModel toModel(User user){
        return UserModel.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .tel(user.getTel())
                .grade(user.getGrade())
                .role(user.getRole())
                .build();
    }
}
