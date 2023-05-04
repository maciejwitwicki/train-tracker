package io.mwi.traintracker.api.login;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.mwi.traintracker.TestUtils.randomInt;
import static io.mwi.traintracker.TestUtils.randomString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseFixture {

    public static LoginResponse aLoginResponse() {
        return new LoginResponse(randomString(), randomInt());
    }

}