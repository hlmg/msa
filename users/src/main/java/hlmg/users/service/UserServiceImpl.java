package hlmg.users.service;

import hlmg.users.data.UserEntity;
import hlmg.users.data.UserRepository;
import hlmg.users.shared.UserDto;
import hlmg.users.shared.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return UserMapper.INSTANCE.entityToDto(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(username, userEntity.getEncryptedPassword(), true, true, true, true, Collections.emptyList());
    }
}
