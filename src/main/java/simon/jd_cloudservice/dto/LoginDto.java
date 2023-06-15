package simon.jd_cloudservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
    private String login;
    private String password;
}
