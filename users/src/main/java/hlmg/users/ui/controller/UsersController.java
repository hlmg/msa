package hlmg.users.ui.controller;

import hlmg.users.service.UserService;
import hlmg.users.shared.UserDto;
import hlmg.users.shared.mapper.UserMapper;
import hlmg.users.ui.model.CreateUserRequest;
import hlmg.users.ui.model.CreateUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto userDetails = UserMapper.INSTANCE.creatRequestToDto(request);
        UserDto createdUser = userService.createUser(userDetails);
        CreateUserResponse response = UserMapper.INSTANCE.dtoToCreateResponse(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
