package io.mwi.traintracker.api.user;

import io.mwi.traintracker.keycloak.KeycloakAdminClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class UserService {

    private final KeycloakAdminClient keycloakAdminClient;
    private final UserMapper userMapper;

    Mono<UserDto> createUser(CreateUserRequest user) {
        return keycloakAdminClient.createUser(
                        user.firstname(),
                        user.lastname(),
                        user.username(),
                        user.email(),
                        user.password())
                .flatMap(this::getUserById);
    }

    Mono<UserDto> getUserById(String userId) {
        return keycloakAdminClient.getUserById(userId)
                .map(userMapper::mapUserRepresentation);
    }
}
