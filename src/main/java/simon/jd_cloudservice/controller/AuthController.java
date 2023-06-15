package simon.jd_cloudservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simon.jd_cloudservice.dto.LoginDto;
import simon.jd_cloudservice.exception.WrongDataException;
import simon.jd_cloudservice.security.UtilsJwt;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UtilsJwt jwtUtil;

    private final AuthenticationManager authManager;

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginDto body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPassword());

            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getLogin());
            return Collections.singletonMap("auth-token", token);

        } catch (AuthenticationException exception) {
            throw new WrongDataException("Bad credentials " + exception);
        }
    }
}
