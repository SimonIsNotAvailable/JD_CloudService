package simon.jd_cloudservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import simon.jd_cloudservice.entity.User;
import simon.jd_cloudservice.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<User> userRepositoryByLogin = userRepository.findByLogin(login);

        if (userRepositoryByLogin.isEmpty()) {
            log.error("User with username: {} not found", userRepositoryByLogin);
            throw new UsernameNotFoundException("User with username: " + userRepositoryByLogin + " not found");
        }
        User user = userRepositoryByLogin.get();
        return new org.springframework.security.core.userdetails.User(
                login,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority())));
    }
}
