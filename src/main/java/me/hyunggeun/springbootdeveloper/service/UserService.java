package me.hyunggeun.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.Exception.EmailAlreadyExistsException;
import me.hyunggeun.springbootdeveloper.domain.RoleType;
import me.hyunggeun.springbootdeveloper.domain.User;
import me.hyunggeun.springbootdeveloper.dto.AddUserRequest;
import me.hyunggeun.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    회원가입
    public Long save(AddUserRequest dto) {

//     중복체크
        boolean exist = userRepository.existsByEmail(dto.getEmail());

        if(exist) {
            throw new EmailAlreadyExistsException("이메일이 이미 존재합니다.");
        }


        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .role(RoleType.ADMIN)
                .build()).getId();
    }

}
