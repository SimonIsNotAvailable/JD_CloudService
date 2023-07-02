package simon.jd_cloudservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import simon.jd_cloudservice.repository.UserRepository;
import simon.jd_cloudservice.security.FilterJwt;
import simon.jd_cloudservice.security.UtilsJwt;
import simon.jd_cloudservice.service.UserInfoService;

@Configuration
public class CloudConfiguration {

    @Bean
    public FilterJwt filterJwt(UserDetailsService userDetailsService) {
        return new FilterJwt(utilsJwt(userDetailsService));
    }

    @Bean
    public UtilsJwt utilsJwt(UserDetailsService userDetailsService) {
        return new UtilsJwt(userDetailsService);
    }

//    @Bean
//    public UserInfoService userInfoService(UserRepository userRepository) {
//        return new UserInfoService(userRepository);
//    }
}
