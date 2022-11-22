package com.github.happi.explorer.junit.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for a hashing text.
 */
final class HashMac {

    /**
     * Internal constructor.
     * Instance does not allow.
     */
    private HashMac() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * Hash a text input with a secret key.
     *
     * @param algorithm Algorithm for hashing
     * @param key       Secret key
     * @param input     Input text
     * @return The input hashed with the secret key
     */
    static String execute(final String algorithm, final String key, final String input) {

        var secret = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        var hexCode = new StringBuilder();
        try {
            var mac = Mac.getInstance(algorithm);
            mac.init(secret);
            var bytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
            for (var b : bytes) {
                var hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexCode.append('0');
                }
                hexCode.append(hex);
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new IllegalArgumentException(ex);
        }
        return hexCode.toString();
    }
}
