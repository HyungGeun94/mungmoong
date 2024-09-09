package me.hyunggeun.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.domain.User;
import me.hyunggeun.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "not found"));


        return new CustomUserDetails(user);
    }

}
