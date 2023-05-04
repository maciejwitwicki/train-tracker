package io.mwi.traintracker;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    private final static SecureRandom secureRandom = new SecureRandom();

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt() {
        return secureRandom.nextInt();
    }

}
