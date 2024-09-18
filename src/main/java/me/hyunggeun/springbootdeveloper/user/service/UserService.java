package me.hyunggeun.springbootdeveloper.user.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.user.dto.AddUserRequest;
import me.hyunggeun.springbootdeveloper.user.entity.RoleType;
import me.hyunggeun.springbootdeveloper.user.entity.User;
import me.hyunggeun.springbootdeveloper.user.exception.EmailAlreadyExistsException;
import me.hyunggeun.springbootdeveloper.user.repository.UserRepository;
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
                .role(RoleType.USER)
                .build()).getId();
    }

}
