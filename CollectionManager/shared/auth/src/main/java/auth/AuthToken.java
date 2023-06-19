package auth;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * Auth token.
 * Contains user ID, token and expiration date.
 *
 * @see auth.AuthService
 */
@Data
public class AuthToken implements Serializable {
    /**
     * Serial version UID
     */
    private final long serialVersionUID = 1L;

    /**
     * User ID
     */
    private final Integer userId;

    /**
     * User token
     */
    private final String token;

    /**
     * Token expiration date
     */
    private final LocalDateTime expiresAt;
}
