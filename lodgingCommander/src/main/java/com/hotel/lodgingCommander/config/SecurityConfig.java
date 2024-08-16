package com.hotel.lodgingCommander.config;

import com.hotel.lodgingCommander.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomUserDetailsService userDetailsService) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/user/**").permitAll()
//                                .requestMatchers("/logIn").permitAll()
                                .requestMatchers("/hotel/search").permitAll()
                                .requestMatchers("/rooms").permitAll()
                                .requestMatchers("/static/*").permitAll()
                                .requestMatchers("/log.png").permitAll()
                                .requestMatchers("/uploads/**").permitAll()
                                .requestMatchers("/hotel/details/*").permitAll()
                                .requestMatchers("/sample.jpg").permitAll()
                                .anyRequest().authenticated())
                .formLogin((form) ->
                        form
//                                .loginPage("/LogIn")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginProcessingUrl("/user/auth")
                                .successForwardUrl("/user/authSuccess")
                                .failureForwardUrl("/user/authFail"))
                .logout((logout) ->
                        logout
                                .logoutUrl("/user/logOut")
                                .logoutSuccessUrl("/user/logOutSuccess")
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID"))
                .userDetailsService(userDetailsService);

        httpSecurity.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }

}
