package authservice.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Encrypt utility class.
 */
public class Encrypt {
    /**
     * Encrypt algorithm.
     */
    private final MessageDigest digest;

    /**
     * Create a new encrypt utility.
     *
     * @throws NoSuchAlgorithmException if the algorithm is not supported
     */
    public Encrypt() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }

    /**
     * Hash a password with a salt.
     *
     * @param password the password to hash
     * @param salt     the salt to use
     * @return the hashed password
     */
    public String hash(String password, String salt) {
        String saltedPassword = password + salt;
        byte[] hash = digest.digest(saltedPassword.getBytes());
        return new String(hash);
    }

    /**
     * Generate a random salt.
     *
     * @return the salt
     */
    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
