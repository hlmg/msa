package hlmg.users.service;

import hlmg.users.data.UserEntity;
import hlmg.users.data.UserRepository;
import hlmg.users.shared.UserDto;
import hlmg.users.shared.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        UserEntity userEntity = UserMapper.INSTANCE.dtoToEntity(userDetails);

        UserEntity savedUser = userRepository.save(userEntity);

        return UserMapper.INSTANCE.entityToDto(savedUser);
    }
}
