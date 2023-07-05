package simon.jd_cloudservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import simon.jd_cloudservice.security.FilterJwt;
import simon.jd_cloudservice.security.UtilsJwt;

@Configuration
@ComponentScan(basePackages = {"simon.jd_cloudservice"})
public class CloudConfiguration {

    @Bean
    public FilterJwt filterJwt(UserDetailsService userDetailsService) {
        return new FilterJwt(utilsJwt(userDetailsService));
    }

    @Bean
    public UtilsJwt utilsJwt(UserDetailsService userDetailsService) {
        return new UtilsJwt(userDetailsService);
    }
}
