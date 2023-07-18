package simon.jd_cloudservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import simon.jd_cloudservice.dto.LoginDto;
import simon.jd_cloudservice.exception.WrongDataException;
import simon.jd_cloudservice.security.UtilsJwt;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UtilsJwt jwtUtil;

    private final AuthenticationManager authManager;

    public Map<String, Object> login(LoginDto body){
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
