package hlmg.users.service;

import hlmg.users.shared.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDetails);
}
