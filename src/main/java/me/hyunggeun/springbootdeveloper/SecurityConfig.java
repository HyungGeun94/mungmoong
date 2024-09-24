package me.hyunggeun.springbootdeveloper;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


                 http
                .authorizeHttpRequests((auth -> auth
                        .requestMatchers("/login", "/signup", "/user", "/","/articles/**","/verify/**","/sendEmail").permitAll()  // 변경
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()));

                http
                .formLogin((formLogin -> formLogin
                        .loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/articles")
                        .permitAll()
                ));


                http
                .logout((logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                ));


                http
                .csrf((auth) -> auth.disable());

             return http.build();
    }

}
