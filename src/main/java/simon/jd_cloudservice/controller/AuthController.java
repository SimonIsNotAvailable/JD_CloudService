package simon.jd_cloudservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simon.jd_cloudservice.dto.LoginDto;
import simon.jd_cloudservice.service.AuthService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginDto body) {
        return authService.login(body);
    }
}
