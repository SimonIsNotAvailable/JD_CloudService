package simon.jd_cloudservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users")
public class User {

        @Id
        @Column(name = "login")
        private String login;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(name = "password")
        private String password;

        @Column(name = "authority")
        private String authority;

        @Column(name = "enabled")
        private Boolean enabled;

}
