package io.mwi.traintracker.api.user;

import io.mwi.traintracker.keycloak.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {

    private final KeycloakClient keycloakClient;
    private final UserMapper userMapper;

    String createUser(String userName) {
        var user = keycloakClient.createUser();
        return user.getCredentials().stream()
                .findFirst()
                .map(CredentialRepresentation::getValue)
                .orElseThrow(() -> new RuntimeException("Change me to some business exception"));
    }

    public UserDto getUserById(String userId) {
        var user = keycloakClient.getUserById(userId);
        return userMapper.mapUserRepresentation(user);
    }
}
