package io.mwi.traintracker.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono<UserDto> createUser(@Valid @RequestBody CreateUserRequest user) {
        return userService.createUser(
                user.username(),
                user.password(),
                user.email());
    }

    @GetMapping("/users")
    public Mono<UserDto> getUser(Principal principal) {
        return userService.getUserById(principal.getName());
    }

}
