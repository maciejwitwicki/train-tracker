package io.mwi.traintracker.api.login;

import io.mwi.traintracker.keycloak.AccessTokenResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static io.mwi.traintracker.TestUtils.randomInt;
import static io.mwi.traintracker.TestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

class AccessTokenMapperTest {

    private final AccessTokenMapper toTest = Mappers.getMapper(AccessTokenMapper.class);

    @Test
    void mapsCorrectly() {

        var token = randomString();
        var expiresIn = randomInt();

        var result = toTest.mapAccessTokenResponse(new AccessTokenResponse(token, expiresIn));

        assertThat(result).isEqualTo(new LoginResponse(token, expiresIn));
    }

}