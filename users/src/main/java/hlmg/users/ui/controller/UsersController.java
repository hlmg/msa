package hlmg.users.ui.controller;

import hlmg.users.service.UserService;
import hlmg.users.shared.UserDto;
import hlmg.users.shared.mapper.UserMapper;
import hlmg.users.ui.model.CreateUserRequest;
import hlmg.users.ui.model.CreateUserResponse;
import hlmg.users.ui.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port") + ", with token = " + env.getProperty("token.secret");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto userDetails = UserMapper.INSTANCE.creatRequestToDto(request);
        UserDto createdUser = userService.createUser(userDetails);
        CreateUserResponse response = UserMapper.INSTANCE.dtoToCreateResponse(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        UserResponse response = UserMapper.INSTANCE.dtoToUserResponse(userDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
