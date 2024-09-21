package hlmg.users.service;

import hlmg.users.data.AlbumServiceClient;
import hlmg.users.data.UserEntity;
import hlmg.users.data.UserRepository;
import hlmg.users.shared.UserDto;
import hlmg.users.shared.mapper.UserMapper;
import hlmg.users.ui.model.AlbumResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //    private final RestTemplate restTemplate;
    private final AlbumServiceClient albumServiceClient;

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
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDto userDto = UserMapper.INSTANCE.entityToDto(userEntity);
//        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
//        ResponseEntity<List<AlbumResponse>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponse>>() {
//        });
//        List<AlbumResponse> albums = albumsListResponse.getBody();
        log.debug("Before calling albums Microservice");
        List<AlbumResponse> albums = albumServiceClient.getAlbums(userId);
        log.debug("After calling albums Microservice");
        userDto.setAlbums(albums);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(username, userEntity.getEncryptedPassword(), true, true, true, true, Collections.emptyList());
    }
}
