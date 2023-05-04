package io.mwi.traintracker.api.login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final AccessTokenService accessTokenService;

    @PostMapping
    public Mono<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return accessTokenService.getAccessTokenByUserCredentials(loginRequest.username(), loginRequest.password());
    }
}
