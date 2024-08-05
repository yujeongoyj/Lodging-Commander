package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository USER_REPOSITORY;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        USER_REPOSITORY = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 임시 메서드
        User user = USER_REPOSITORY.findUserBy(username);

        // 실제 로그인 구현 메서드 (유저 엔티티)
        // User user = USER_REPOSITORY.findUserBy(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " is not a valid username");
        } else {
            // User Entity에 UserDetails를 export 해야한다.
            // return user;

            return user;
        }
    }
}