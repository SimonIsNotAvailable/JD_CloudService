package simon.jd_cloudservice.security;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class FilterJwt extends GenericFilterBean {
    private final UtilsJwt utils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

            HttpServletResponse response = (HttpServletResponse) servletResponse;

            String token = utils.resolveToken((HttpServletRequest) servletRequest);
            try {
                if (token != null && utils.validateToken(token)) {
                    Authentication auth = utils.getAuthentication(token);

                    if (auth != null) {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (ExpiredJwtException e) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
                log.error(e.getMessage());
            }
    }
}
