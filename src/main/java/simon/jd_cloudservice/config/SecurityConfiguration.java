package simon.jd_cloudservice.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import simon.jd_cloudservice.security.FilterJwt;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final FilterJwt filter;
    private final DataSource dataSource;
//
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity.cors().configurationSource(corsConfigurationSource());
//        httpSecurity.csrf().disable()
//
//                .authorizeRequests().antMatchers("/login").permitAll().
//
//                anyRequest().authenticated().and().
//
//                exceptionHandling().authenticationEntryPoint((request, response, authException) ->
//                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized error")).
//                and().sessionManagement()
//
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        httpSecurity
//                .logout()
//                .logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
//                .deleteCookies("auth-token")
//                .deleteCookies("JSESSIONID");
//
//        httpSecurity
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//
//    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email, password, enabled from users where email = ?")
                .authoritiesByUsernameQuery("select email, authority from authorities where email = ?")
                .rolePrefix("ROLE_");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )

//
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) ->
//                        response
//                                .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized error"))
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().logout().logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
//                .deleteCookies("auth-token")
//                .deleteCookies("JSESSIONID")
//                .and()
//                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
