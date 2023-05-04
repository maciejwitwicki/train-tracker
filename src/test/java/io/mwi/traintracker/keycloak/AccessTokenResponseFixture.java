package io.mwi.traintracker.keycloak;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.mwi.traintracker.TestUtils.randomInt;
import static io.mwi.traintracker.TestUtils.randomString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenResponseFixture {

    public static AccessTokenResponse anAccessTokenResponse() {
        return new AccessTokenResponse(randomString(), randomInt());
    }

}