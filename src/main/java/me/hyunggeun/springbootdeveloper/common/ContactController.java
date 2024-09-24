package me.hyunggeun.springbootdeveloper.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody ContactRequest request, BindingResult result) {


        emailService.sendContactEmail(request.getEmail());
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> checkVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        // 서비스에서 검증 처리
        String result = emailService.verifyCode(email, code);

        if (result.equals("Verification successful!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
