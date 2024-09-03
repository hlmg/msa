package hlmg.users.service;

import hlmg.users.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);

    UserDto getUserByEmail(String email);
}
