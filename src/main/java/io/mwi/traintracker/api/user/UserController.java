package io.mwi.traintracker.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(originPatterns = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(@Valid CreateUserRequest user) {
        return userService.createUser(user.name());
    }

    @GetMapping
    public UserDto getUser(Principal principal) {
        return userService.getUserById(principal.getName());
    }

}
